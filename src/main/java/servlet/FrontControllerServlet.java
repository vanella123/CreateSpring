package servlet;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.*;
import annotation.Controller;
import utils.UrlMethod;
import utils.UrlMethodMapping;
import utils.Utils;
import listener.FrameworkContextListener;
@WebServlet("/")
public class FrontControllerServlet extends HttpServlet {
    private List<String> listClass;
    private Map<UrlMethod, UrlMethodMapping> mapping;
    private Utils utils;
    @Override
    public void init() throws ServletException {
        utils = new Utils(getServletContext());
        mapping = (Map<UrlMethod, UrlMethodMapping>) getServletContext().getAttribute("routes");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
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
            } else {
                utils.showDashboard(request, response, requestURI , mapping);
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