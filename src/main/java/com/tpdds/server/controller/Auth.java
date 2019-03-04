package com.tpdds.server.controller;

import com.tpdds.repositorios.RepositorioClientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.Map;

public class Auth {
    public static ModelAndView loginView(Request req, Response res) {
        String username = req.session().attribute("username");

        if (username == null) {
            return new ModelAndView(null, "login.hbs");
        } else {
            boolean esAdmin =
                    RepositorioClientes.getInstance().esAdmin(username);
            return homeRedirect(res, esAdmin);
        }
    }

    public static ModelAndView login(Request req, Response res) {
        String username = req.queryParams("username");
        String password = req.queryParams("password");

        Session userSession = req.session(true);

        if (!RepositorioClientes.getInstance().accesoConcedido(username, password)) {
            Map<String, Object> viewModel = new HashMap<>();
            viewModel.put("loginFallido", "true");

            return new ModelAndView(viewModel, "login.hbs");
        }

        boolean esAdmin = RepositorioClientes.getInstance().esAdmin(username);

        userSession.attribute("username", username);
        userSession.attribute("esAdmin", esAdmin);

        if (!esAdmin) {
            userSession.attribute("cliente", RepositorioClientes.getInstance().getClientByUser(username));
        } else {
            // TODO: guardar admin en session?
        }

        return homeRedirect(res, esAdmin);
    }

    public static ModelAndView logout(Request req, Response res) {
        Session session = req.session();

        session.attribute("username", null);
        res.redirect("/");
        return null;
    }

    public static void isLoggedIn(Request req, Response res) {
        Session session = req.session();
        String username = session.attribute("username");

        if (username == null) {
            res.redirect("/");
        }

        if (! RepositorioClientes.getInstance().existe(username)) {
            session.removeAttribute("username");
            res.redirect("/");
        }
    }

    public static void isAdmin(Request req, Response res) {
        isLoggedIn(req, res);

        Session session = req.session();
        boolean esAdmin = session.attribute("esAdmin");

        if (! esAdmin) {
            session.removeAttribute("username");
            res.redirect("/");
        }
    }

    private static ModelAndView homeRedirect(Response res, boolean esAdmin) {
        if(esAdmin) {
            res.redirect("/admin/hogares");
            return new ModelAndView(null, "index.hbs");
        } else {
            res.redirect("/cliente/hogar");
            return new ModelAndView(null, "index.hbs");
        }
    }
}
