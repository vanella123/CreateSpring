package servlet;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import annotation.Controller;
import annotation.GetMapping;

@WebServlet("/")
public class FrontControllerServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException  {
        try{
            response.setContentType("text/html;charset=UTF-8");
        
            // 1. On récupère l'URL tapée par l'utilisateur
            String requestURI = request.getServletPath();
            
            // 2. On l'affiche dans la console du serveur (pour le développeur)
            System.out.println("URL Interceptée : " + requestURI);
            
            // // 3. On l'affiche sur la page web (pour tester)
            // try (PrintWriter out = response.getWriter()) {
            //     out.println("<html><body>");
            //     out.println("<h1>Mon Framework Spring Maison 🚀</h1>");
            //     out.println("<p>Tu as demandé l'URL : <strong>" + requestURI + "</strong></p>");
            //     out.println("</body></html>");
            // }

            // Logique : 
            // Charger la classe du contrôleur correspondant à l'URL
            Class<?> clazz = Class.forName("controller.TestController");
            // verifier si l'annotion @Controller est présente sur la classe
            if (clazz.isAnnotationPresent(annotation.Controller.class)) {
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(annotation.GetMapping.class)) {
                        annotation.GetMapping getMapping = method.getAnnotation(annotation.GetMapping.class);
                        String mappingValue = getMapping.value();
                        if (mappingValue.equals(requestURI)) {
                            // Appeler la méthode du contrôleur
                            Object controllerInstance = clazz.getDeclaredConstructor().newInstance();
                            String result = (String) method.invoke(controllerInstance);
                            // Afficher le résultat dans la réponse HTTP
                            try (PrintWriter out = response.getWriter()) {
                                out.println("<html><body>");
                                out.println("<h1>Mon Framework Spring Maison 🚀</h1>") ;
                                out.println("<p>Résultat : " + result + "</p>");
                                out.println("</body></html>");
                            }
                        }
                    }
                }
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
    
