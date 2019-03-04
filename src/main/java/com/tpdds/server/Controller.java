package com.tpdds.server;

import com.tpdds.repositorios.RepositorioClientes;
import com.tpdds.usuarios.Cliente;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {


    public static ModelAndView hogaresByAdmin(Request req, Response res) {

        List<Cliente> clientes = RepositorioClientes.getInstance().todo();

        for(Cliente c: clientes) {
            System.out.println(c.getAllDispositivos());
        }

        Map<String, Object> viewModel = new HashMap<>();
        viewModel.put("mainSection", "Hogares y sus consumos");

        return new ModelAndView(viewModel, "index.hbs");
    }


    public static ModelAndView reporteSegunId(Request req, Response res) {

        Map<String, Object> viewModel = new HashMap<>();
        viewModel.put("mainSection", "Reporte " + req.params("idReporte") + " (tabla)");

        return new ModelAndView(viewModel, "index.hbs");
    }


    public static ModelAndView altaDispositivo(Request req, Response res) {

        Map<String, Object> viewModel = new HashMap<>();
        //viewModel.put("mainSection", "");

        return new ModelAndView(viewModel, "altaDispositivo.hbs");
    }

    
    //************************ ABM DISPOSITIVOS ***************************//
    
    
    public static ModelAndView darDeAltaDispositivoEstandar(Request req, Response res) {

        String nombre = req.queryParams("nombre");
        Float horasMinimas = Float.parseFloat(req.queryParams("horasMinimas"));
        Float horasMaximas = Float.parseFloat(req.queryParams("horasMaximas"));
        Float horasUso = Float.parseFloat(req.queryParams("horasUso"));
        Float kwhora = Float.parseFloat(req.queryParams("kwhora"));

        Map<String, Object> viewModel = new HashMap<>();

        if(nombre == null || horasMinimas == null || horasMaximas == null || horasUso == null || kwhora == null) {
            viewModel.put("mainSection", "Error");
        } else {
            viewModel.put("nombre", nombre);
            viewModel.put("horasMinimas", horasMinimas);
            viewModel.put("horasMaximas", horasMaximas);
            viewModel.put("horasUso", horasUso);
            viewModel.put("kwhora", kwhora);
        }



        return new ModelAndView(viewModel, "dispositivo.hbs");
    }
    
    public static ModelAndView darDeAltaDispositivoInteligente(Request req, Response res) {
    	
    	String nombre = req.queryParams("nombre");
    	//Fabricante fabricante = req.queryParams("fabricante");		// No se como obtener esto.
    	
    	Map<String, Object> viewModel = new HashMap<>();
    	
    	if(nombre == null) {
    		viewModel.put("mainSection", "Error");
    	} else {
    		viewModel.put("nombre", nombre);
    	}
    	
    	
    	return new ModelAndView(viewModel, "dispositivo.hbs");   	
    	
    }

}
