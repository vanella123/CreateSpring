package listener;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import utils.*;

public class FrameworkContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        Map<UrlMethod, UrlMethodMapping> routes = new HashMap<>();

        try {
            Utils utils = new Utils(context);
            utils.buildRoutingTable(routes, annotation.Controller.class);

            context.setAttribute("routes", routes);
            
            System.out.println("[Framework] Initialisation réussie ! " + routes.size() + " route(s) détectée(s).");
            
        } catch (Exception e) {
            throw new RuntimeException("Erreur critique lors de l'initialisation du Framework : " + e.getMessage(), e);
        }
    } 

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    } 
}