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
            Utils utils = new Utils(getServletContext());
        // Initialiser la liste des classes annotées par @Controller
        listClass = utils.getListClass(Controller.class);
        System.out.println("Liste des classes annotées par @Controller : " + listClass);
    }
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException  {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String requestURI = request.getServletPath();
            System.out.println("URL Interceptée : " + requestURI);
            Utils utils = new Utils(getServletContext());
            List<String> listeMethode = utils.getListMethod(listClass , requestURI) ; 

            // On parcourt la liste des classes trouvées dynamiquement au lieu de tricher avec "TestController"
            for (String className : listClass) {
                Class<?> clazz = Class.forName(className);
                            // Écriture de la réponse
                try (PrintWriter out = response.getWriter()) {
                // out.println("<h3>Liste des contrôleurs enregistrés :</h3><ul>");
                //     for(String c : listClass){
                //         out.println("<li>" + c + "</li>");
                //     }

                out.println("Liste Methode : "); 
                    for(String c : listeMethode){
                        out.println("<li>" + c + "</li>") ; 
                    }
                out.println("</ul></body></html>");
                    }
                    return; // On a trouvé et traité la route, on arrête la méthode ici !
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
    
