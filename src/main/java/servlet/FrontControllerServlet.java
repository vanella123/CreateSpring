package servlet;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.*;
import java.lang.reflect.Method;

import annotation.Controller;
import annotation.ApiRest;
import utils.UrlMethod;
import utils.UrlMethodMapping;
import utils.Utils; 
import com.fasterxml.jackson.databind.ObjectMapper; 

@WebServlet("/")
public class FrontControllerServlet extends HttpServlet {
    private List<String> listClass;
    private Map<UrlMethod, UrlMethodMapping> mapping;
    private Utils utils;
    @Override
    @SuppressWarnings("unchecked")
    public void init() throws ServletException {
        utils = new Utils(getServletContext());
        mapping = (Map<UrlMethod, UrlMethodMapping>) getServletContext().getAttribute("routes");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        try {
            //SUPPRIMÉ : Ne pas fixer le Content-Type "text/html" ici !

            String requestURI = request.getServletPath();
            String httpMethod = request.getMethod();

            UrlMethod urlKey = new UrlMethod(requestURI, httpMethod);

            // Si la route existe, on traite. Sinon, on affiche le dashboard.
            if (mapping != null && mapping.containsKey(urlKey)) {
                UrlMethodMapping routeMapping = mapping.get(urlKey);
                
                // Étape A : Exécuter le contrôleur
                Object result = utils.executeController(routeMapping, request, response);
                
                // Étape B : Traiter et afficher le résultat
                utils.handleResult(result, request, response);
                boolean isRestMethod = routeMapping.getMethod().isAnnotationPresent(ApiRest.class);
                if (isRestMethod) {
                    response.setContentType("application/json;charset=UTF-8");
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonResponse = objectMapper.writeValueAsString(result);
                    
                    // Écriture et purge explicite du buffer HTTP
                    PrintWriter out = response.getWriter();
                    out.print(jsonResponse);
                    out.flush();
                    return; 
                } else {
                    response.setContentType("text/html;charset=UTF-8");
                    utils.handleResult(result, request, response);
                }
                
            } else {
                response.setContentType("text/html;charset=UTF-8");
                utils.showDashboard(request, response, requestURI, mapping);

            }
        
        } catch (Exception e) {
            utils.handleError(response, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
