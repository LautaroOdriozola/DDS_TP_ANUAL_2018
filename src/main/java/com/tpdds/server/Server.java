package com.tpdds.server;

import com.tpdds.server.controller.AdminController;
import com.tpdds.server.controller.Auth;
import com.tpdds.server.controller.ClienteController;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class Server {

    public static void start() {
        Spark.staticFileLocation("/public");

        Spark.port(8080);

        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

        get("/", Auth::loginView, engine);
        get("/login", Auth::loginView, engine);

        get("/logout", Auth::logout);
        post("/login", Auth::login, engine);
        
        get("/dispositivos", ClienteController::vistaAltaDispositivo, engine);
        post("/dispositivos", ClienteController::altaDispositivo, engine);

        path("/cliente", () -> {
            before("/*", Auth::isLoggedIn);
            get("/hogar", ClienteController::hogar, engine);
            get("/ingresarConsulta", ClienteController::ingresarConsulta, engine);

            // si me lo mandan por url
            get("/periodo", ClienteController::consumoPorPeriodo, engine);
            // si me lo mandan como un form
            post("/periodo", ClienteController::consumoPorPeriodo, engine);

            get("/altaDispositivo", ClienteController::vistaAltaDispositivo, engine);
            post("/altaDispositivo", ClienteController::altaDispositivo, engine);

            get("/optimizacion", ClienteController::simplex, engine);
        });

        path("/admin", () -> {
            before("/*", Auth::isAdmin);
            get("/hogares", AdminController::hogares, engine);

            get("/reportes", AdminController::reportes, engine);
            get("/reportes/transformadores",
                    AdminController::reporteTransformadores, engine);


            // si me lo mandan por url
            get("/reportes/transformadorPeriodo", AdminController::reporteTransformadorPeriodo, engine);
            // si me lo mandan como un form
            post("/reportes/transformadorPeriodo", AdminController::reporteTransformadorPeriodo, engine);
        });

        Spark.init();
        DebugScreen.enableDebugScreen();
    }
}
