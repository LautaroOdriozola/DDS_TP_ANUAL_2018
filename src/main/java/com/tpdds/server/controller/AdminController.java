package com.tpdds.server.controller;


import com.tpdds.repositorios.RepositorioClientes;
import com.tpdds.repositorios.RepositorioTransformadores;
import com.tpdds.transformadores.Transformador;
import com.tpdds.usuarios.Cliente;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAccumulator;

public class AdminController {
    public static ModelAndView hogares(Request request, Response response) {
        List<Cliente> clientes =
                RepositorioClientes.getInstance().todo();

        return new ModelAndView(clientes, "hogares.hbs");
    }

    public static ModelAndView reporteTransformadores(Request request, Response response) {
        List<Transformador> transformadores =
               RepositorioTransformadores.getInstance().todo();

        Map<String, Object> model = new HashMap<>();

        model.put("transformadores", transformadores);
        model.put("maxTransformador", transformadores.get(transformadores.size() - 1).getId());

        return new ModelAndView(model, "reporteTransformadores.hbs");
    }

    public static ModelAndView reporteTransformadorPeriodo(Request req, Response res) {
        String idString = req.queryParams("id");

        String periodo = req.queryParams("periodo"); // espera formato YYYY-MM

        int year = Integer.parseInt(periodo.substring(0, 4));
        int month = Integer.parseInt(periodo.substring(5));

        final YearMonth yearMonth = YearMonth.of(year, month);

        Transformador transformador = RepositorioTransformadores.getInstance().buscar(Integer.parseInt(idString));
        List<Cliente> clientes = transformador.getClientes();

        double consumo = clientes.stream()
                .map(Cliente::getConsumosPorPeriodo)
                .map(p -> p.getOrDefault(yearMonth, 0.0))
                .reduce(0.0, Double::sum);

        Map<String, Object> model = new HashMap<>();

        model.put("consumo", consumo);
        model.put("transformador", transformador);
        model.put("periodo", yearMonth);
        model.put("cantidadClientes", clientes.size());

        return new ModelAndView(model, "reporteTransformador.hbs");
    }

    public static ModelAndView reportes(Request req, Response res) {

        Map<String, Object> viewModel = new HashMap<>();
        viewModel.put("mainSection", "Reportes");

        return new ModelAndView(viewModel, "index.hbs");
    }
}
