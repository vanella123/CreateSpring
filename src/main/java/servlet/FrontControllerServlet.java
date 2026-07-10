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

@WebServlet("/")
public class FrontControllerServlet extends HttpServlet {
   // private List<String> listClass;
    private Map<UrlMethod, UrlMethodMapping> mapping = new HashMap<>();
    @Override
    public void init() throws ServletException {
        try {
            super.init();
            Utils utils = new Utils(getServletContext());
          //  listClass = utils.getListClass(Controller.class);
            //System.out.println("Liste des classes annotées par @Controller : " + listClass);
            mapping = utils.mappingMethodUrl(Controller.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException  {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String requestURI = request.getServletPath();
            System.out.println("URL Interceptée : " + requestURI);
            if (mapping.containsKey(new UrlMethod(requestURI, request.getMethod()))) {
                UrlMethodMapping urlMethodMapping = mapping.get(new UrlMethod(requestURI, request.getMethod()));
                Object controllerInstance = urlMethodMapping.getControllerClass().getDeclaredConstructor().newInstance();
                Object result = urlMethodMapping.getMethod().invoke(controllerInstance);
                try (PrintWriter out = response.getWriter()) {
                    out.println(result);
                }
            } else { 
                // Si aucune route ne correspond, on renvoie une erreur 404
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Erreur 404 : Aucune méthode ne correspond à l'URL " + requestURI);
            } 
            
         
        } catch (Exception e) {
            // TRÈS IMPORTANT : On affiche l'erreur dans la console Tomcat
            System.err.println("Exception interceptée dans le FrontController : " + e.getMessage());
            e.printStackTrace();  
            
            // On envoie l'erreur proprement à Tomcat pour éviter la page blanche
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur du Framework : " + e.getMessage());
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