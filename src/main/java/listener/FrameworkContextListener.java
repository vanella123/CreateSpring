package listener;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import utils.UrlMethod;
import utils.UrlMethodMapping;
import utils.Utils;

@WebListener
public class FrameworkContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(FrameworkContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Map<UrlMethod, UrlMethodMapping> routes = new HashMap<>();

        try {
            logger.info("=== [FRAMEWORK] DÉMARRAGE DU SCAN DES ROUTES ===");

            Utils utils = new Utils(context);
            utils.buildRoutingTable(routes, annotation.Controller.class);

            context.setAttribute("routes", routes);

            logger.info("=== [FRAMEWORK] INITIALISATION RÉUSSIE ! " + routes.size() + " ROUTE(S) DÉTECTÉE(S) ===");

            for (UrlMethod key : routes.keySet()) {
                logger.info(" -> Route enregistrée : " + key.getUrl());
            }

        } catch (Exception e) {
            logger.severe("=== [FRAMEWORK] ERREUR CRITIQUE LORS DU SCAN ===");
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'initialisation du Framework", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nettoyage si nécessaire
    }
}