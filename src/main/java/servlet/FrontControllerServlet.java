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
    private List<String> listClass;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            Utils utils = new Utils(getServletContext());
            listClass = utils.getListClass(Controller.class);
            System.out.println("Liste des classes annotées par @Controller : " + listClass);
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

            UrlMethod url1 = new UrlMethod("/test", "GET");
            UrlMethod url2 = new UrlMethod("/test", "GET"); // C'est le doublon !

            UrlMethodMapping um1 = new UrlMethodMapping(getClass(), null);
            Map<UrlMethod, UrlMethodMapping> mapping = new HashMap<>();
            mapping.put(url1, um1);
            
            System.out.println("Vérification du doublon en cours...");
            // On teste si le deuxième déclenche la détection
            if (mapping.containsKey(url2)) {
                System.out.println(" Doublon détecté avec succès dans la console !");
                throw new IllegalArgumentException(" Erreur Framework : L'URL '" + url2.getUrl() + "' en mode " + url2.getMethod() + " est déjà enregistrée !");
            } else {
                mapping.put(url2, um1);
            }
            
            // Si par malheur ça ne plante pas, on écrit un message de secours
            try (PrintWriter out = response.getWriter()) {
                out.println("<html><body>Tout est OK (l'exception n'a pas fonctionné)</body></html>");
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