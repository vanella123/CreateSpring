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

    @Override
    public void init() throws ServletException {
        mapping = (Map<UrlMethod, UrlMethodMapping>) getServletContext().getAttribute("routes");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String requestURI = request.getServletPath();
            System.out.println("URL Interceptée : " + requestURI);

            // 1. Récupérer la Map "routes" générée par le Listener au démarrage
            ServletContext context = getServletContext();
            Map<UrlMethod, UrlMethodMapping> mapping = (Map<UrlMethod, UrlMethodMapping>) context.getAttribute("routes");

            // 2. Générer la page HTML pour afficher le contenu du mapping
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Framework Dashboard - Routes</title>");
                out.println("<style>");
                out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 40px; background-color: #f4f7f6; }");
                out.println("h1 { color: #333; border-bottom: 2px solid #333; padding-bottom: 10px; }");
                out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }");
                out.println("th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }");
                out.println("th { background-color: #4CAF50; color: white; text-transform: uppercase; font-size: 14px; }");
                out.println("tr:hover { background-color: #f5f5f5; }");
                out.println(".method { font-weight: bold; padding: 4px 8px; border-radius: 4px; color: white; }");
                out.println(".get { background-color: #61afff; }");
                out.println(".post { background-color: #49cc90; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                
                out.println("<h1>🗺️ Liste des Routes Enregistrées</h1>");
                out.println("<p>URL actuelle appelée : <strong>" + requestURI + "</strong></p>");

                if (mapping == null || mapping.isEmpty()) {
                    out.println("<p style='color: red;'>⚠️ Aucune route n'a été détectée dans l'application.</p>");
                } else {
                    out.println("<table>");
                    out.println("<thead>");
                    out.println("<tr>");
                    out.println("<th>URL</th>");
                    out.println("<th>Méthode HTTP</th>");
                    out.println("<th>Classe Contrôleur</th>");
                    out.println("<th>Méthode Java</th>");
                    out.println("</tr>");
                    out.println("</thead>");
                    out.println("<tbody>");

                    // On parcourt la Map pour afficher chaque route dans une ligne du tableau
                    for (Map.Entry<UrlMethod, UrlMethodMapping> entry : mapping.entrySet()) {
                        UrlMethod urlKey = entry.getKey();
                        UrlMethodMapping mappingVal = entry.getValue();

                        String methodClass = urlKey.getMethod().toLowerCase();

                        out.println("<tr>");
                        out.println("<td><code>" + urlKey.getUrl() + "</code></td>");
                        out.println("<td><span class='method " + methodClass + "'>" + urlKey.getMethod() + "</span></td>");
                        out.println("<td>" + mappingVal.getControllerClass().getName() + "</td>");
                        out.println("<td><code>" + mappingVal.getMethod().getName() + "()</code></td>");
                        out.println("</tr>");
                    }

                    out.println("</tbody>");
                    out.println("</table>");
                }

                out.println("</body>");
                out.println("</html>");
            }

        } catch (Exception e) {
            System.err.println("Exception interceptée dans le FrontController : " + e.getMessage());
            e.printStackTrace();  
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