package servlet;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import annotation.Controller;
import annotation.GetMapping;
import utils.Utils;
@WebServlet("/")
public class FrontControllerServlet extends HttpServlet {
    private List<String> listClass;
    @Override
    public void init() throws ServletException {
        super.init();
        // Initialiser la liste des classes annotées par @Controller
        Utils utils = new Utils(getServletContext());
        listClass = utils.getListClass(Controller.class);
        System.out.println("Liste des classes annotées par @Controller : " + listClass);
    }
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException  {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String requestURI = request.getServletPath();
            System.out.println("URL Interceptée : " + requestURI);

            boolean routeTrouvee = false;

            // On parcourt la liste des classes trouvées dynamiquement au lieu de tricher avec "TestController"
            for (String className : listClass) {
                Class<?> clazz = Class.forName(className);
                
                // Pas besoin de vérifier @Controller ici car listClass ne contient que ça
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    
                    if (method.isAnnotationPresent(annotation.GetMapping.class)) {
                        annotation.GetMapping getMapping = method.getAnnotation(annotation.GetMapping.class);
                        
                        if (getMapping.value().equals(requestURI)) {
                            routeTrouvee = true;
                            
                            // Invocation dynamique du contrôleur
                            Object controllerInstance = clazz.getDeclaredConstructor().newInstance();
                            String result = (String) method.invoke(controllerInstance);
                            
                            // Écriture de la réponse
                            try (PrintWriter out = response.getWriter()) {
                                // out.println("<html><body>");
                                // out.println("<h1>Mon Framework Spring Maison 🚀</h1>") ;
                                // out.println("<p>Résultat de l'action : <strong>" + result + "</strong></p>");
                                out.println("<h3>Liste des contrôleurs enregistrés :</h3><ul>");
                                for(String c : listClass){
                                    out.println("<li>" + c + "</li>");
                                }
                                out.println("</ul></body></html>");
                            }
                            return; // On a trouvé et traité la route, on arrête la méthode ici !
                        }
                    }
                }
            }

            // Si on arrive ici, c'est qu'aucune classe/méthode ne correspond à l'URL demandée
            if (!routeTrouvee) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Erreur 404 : Aucun contrôleur trouvé pour l'URL " + requestURI);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur est survenue : " + e.getMessage());
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
    
