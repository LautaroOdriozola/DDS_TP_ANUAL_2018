package com.tpdds.server.controller;

import com.tpdds.dispositivos.Dispositivo;
import com.tpdds.dispositivos.DispositivoEstandar;
import com.tpdds.dispositivos.OptimizacionDispositivo;
import com.tpdds.repositorios.RepositorioClientes;
import com.tpdds.repositorios.RepositorioCorridaSimplex;
import com.tpdds.repositorios.RepositorioDispositivos;
import com.tpdds.usuarios.CalculadoraConsumo;
import com.tpdds.usuarios.Cliente;
import org.apache.commons.math3.optim.linear.NoFeasibleSolutionException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClienteController {

	public static ModelAndView hogar(Request req, Response res) {

		Session session = req.session();
		Cliente cliente = RepositorioClientes.getInstance().getClientByUser(session.attribute("username"));

		HashMap<String, Object> viewModel = new HashMap<>();

		Map<YearMonth, Double> periodos = cliente.getConsumosPorPeriodo();

		if (periodos.size() > 0) {
		    Map<YearMonth, Long> periodosRedondeados =
					periodos.entrySet().stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey,
								e -> e.getValue().longValue()
						));

			viewModel.put("periodos", periodosRedondeados);
		} else {
			// TODO: manejar el caso de un cliente sin consumos
			viewModel.put("noHayPeriodos", true);
		}

		List<Dispositivo> dispositivos = cliente.getAllDispositivos();
		viewModel.put("dispositivo", dispositivos);

		return new ModelAndView(viewModel, "hogar.hbs");
	}

	public static ModelAndView ingresarConsulta(Request req, Response res) {
		return new ModelAndView(null, "ingresarConsulta.hbs");
	}

	public static ModelAndView simplex(Request req, Response res) {

		Session session = req.session();
		Cliente cliente = RepositorioClientes.getInstance().getClientByUser(session.attribute("username"));

		List<Dispositivo> dispositivos = cliente.getAllDispositivos();

		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("dispositivo", dispositivos);

		try {
			CalculadoraConsumo calculadoraSimplex = new CalculadoraConsumo();
			calculadoraSimplex.generarCorridaSimplex(cliente);

			List<OptimizacionDispositivo> dispositivosOptimizados =
					RepositorioCorridaSimplex.getInstance().todo();

			viewModel.put("dispositivosOptimizados", dispositivosOptimizados);
		} catch (NoFeasibleSolutionException ex) {
			viewModel.put("noHuboSolucion", true);
		}

		return new ModelAndView(viewModel, "simplex.hbs");
	}

	public static ModelAndView consumoPorPeriodo(Request req, Response res) {
	    Session session = req.session();
		Cliente cliente = RepositorioClientes.getInstance().getClientByUser(session.attribute("username"));

		Map<String, Object> viewModel = new HashMap<>();

		String input = req.queryParams("periodo"); // espera formato YYYY-MM

		int year = Integer.parseInt(input.substring(0, 4));
		int month = Integer.parseInt(input.substring(5));

		final YearMonth yearMonth = YearMonth.of(year, month);

		Double consumo = cliente.getConsumosPorPeriodo().get(yearMonth);

		// no se me ocurre una mejor forma de manejar el caso de que no haya consumo
		if (consumo != null) {
			viewModel.put("consumoTotal", consumo);

			Map<String, Double> dispositivosConConsumos = cliente.getAllDispositivos()
					.stream()
                    .collect(Collectors.toMap(
                            Dispositivo::getNombre,
                            d -> d.getConsumoEnPeriodo(yearMonth)
					));

			viewModel.put("dispositivosConConsumos", dispositivosConConsumos);


			return new ModelAndView(viewModel, "consumoPeriodo.hbs");
		} else {
			// TODO: manejar mejor caso de error? por ahora te devuelve al hogar
			return ClienteController.hogar(req, res);
		}

	}

	public static ModelAndView vistaAltaDispositivo(Request request, Response response) {
		return new ModelAndView(null, "altaDispositivo.hbs");
	}

	public static ModelAndView altaDispositivo(Request request, Response response) {
	    Session session = request.session();
		Cliente cliente = RepositorioClientes.getInstance().getClientByUser(session.attribute("username"));

		String nombre = request.queryParams("nombre");

		Map<String, Object> model = new HashMap<>();

		BigDecimal horasMaximas;
		BigDecimal horasMinimas;
		BigDecimal kWhora;

		// pense en hacer algun tipo de validacion mas formal, pero sinceramente me parece que no vale la pena
		try {
			horasMaximas = new BigDecimal(request.queryParams("horasmaximas"));
			horasMinimas = new BigDecimal(request.queryParams("horasminimas"));
			kWhora = new BigDecimal(request.queryParams("kwhora"));

			if (horasMaximas.compareTo(horasMinimas) < 1)
				throw new NumberFormatException("La cantidad de horas minimas no puede ser mayor que la cantidad de horas maximas.");

		} catch (NumberFormatException ex) {
			model.put("numberError", true);
			model.put("errorMessage", ex.getMessage());
			return new ModelAndView(model, "altaDispositivo.hbs");
		}

		if (nombre.length() < 1) {
			model.put("nameError", true);
			return new ModelAndView(model, "altaDispositivo.hbs");
		}

		DispositivoEstandar d = new DispositivoEstandar(nombre, kWhora, new BigDecimal(0), horasMinimas, horasMaximas);
		cliente.agregarDispositivo(d);

		RepositorioDispositivos.getInstance().persistDispositivoEstandar(d);
		RepositorioClientes.getInstance().update(cliente);

		model.put("confirmed", "true");
		model.put("nombre", nombre);

		return new ModelAndView(model, "altaDispositivo.hbs");
	}
}
