package main.java.servlet;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/")
public class MoodServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // 1. On récupère l'URL tapée par l'utilisateur
        String requestURI = request.getRequestURI();
        
        // 2. On l'affiche dans la console du serveur (pour le développeur)
        System.out.println("URL Interceptée : " + requestURI);
        
        // 3. On l'affiche sur la page web (pour tester)
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><body>");
            out.println("<h1>Mon Framework Spring Maison 🚀</h1>");
            out.println("<p>Tu as demandé l'URL : <strong>" + requestURI + "</strong></p>");
            out.println("</body></html>");
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
    
