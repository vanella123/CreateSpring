package utils;

import annotation.UrlMapping;
import entity.ModelAndView;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

public class Utils {
    private ServletContext servletContext;

    public Utils(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    public Utils() {
    }

    // Retourne la liste des classes (Class<?>) directement pour faciliter la réflexion
    public List<Class<?>> getListClass(Class<?> annotationController) {
        List<Class<?>> listClass = new ArrayList<>();
        
        // Récupération dynamique du package grâce au paramètre d'initialisation global !
        String namePackage = servletContext.getInitParameter("package.controller");
        
        if (namePackage == null || namePackage.trim().isEmpty()) {
            throw new RuntimeException("Le paramètre 'package.controller' n'est pas configuré dans le web.xml !");
        }
        
        Reflections reflections = new Reflections(namePackage);
        Set<Class<?>> classes = reflections.get(Scanners.TypesAnnotated.with(annotationController).asClass());
        
        for (Class<?> cls : classes) {
            listClass.add(cls);
        }
        return listClass;
    }

    public List<String> getListMethod(List<String> listeClasse, String requestURI) {
        boolean routeTrouvee = false; 
        List<String> response = new ArrayList<>();
        response.add("RequestURI : " + requestURI); 
        
        try {
            for (String className : listeClasse) {
                Class<?> clazz = Class.forName(className);
                
                if (clazz.isAnnotationPresent(annotation.Controller.class)) {
                    Method[] methods = clazz.getDeclaredMethods();
                    
                    for (Method method : methods) { 
                        if (method.isAnnotationPresent(annotation.UrlMapping.class)) {
                            annotation.UrlMapping getMapping = method.getAnnotation(annotation.UrlMapping.class);
                            
                            if (getMapping.url().equals(requestURI)) {
                                routeTrouvee = true;
                                response.add("URL : " + requestURI + " | CONTROLLER : " + className + " | METHODE : " + method.getName());
                            }
                        }
                    }
                }
            }
            
            if (!routeTrouvee) {
                response.add("❌ Erreur 404 : Aucune méthode ne correspond à l'URL " + requestURI);
                response.add("Voici la liste des routes disponibles dans l'application : ");
                
                for (String className : listeClasse) {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(annotation.Controller.class)) {
                        for (Method method : clazz.getDeclaredMethods()) {
                            if (method.isAnnotationPresent(annotation.UrlMapping.class)) {
                                annotation.UrlMapping getMapping = method.getAnnotation(annotation.UrlMapping.class);
                                response.add("👉 URL : " + getMapping.url() + " | CONTROLLER : " + className + " | METHODE : " + method.getName());
                            }
                        }
                    }
                }
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();  
            return null; 
        }     
    }
    

    // 🚀 La méthode buildRoutingTable adaptée !
    // Elle n'est plus statique pour pouvoir utiliser 'getListClass' et est 100% compatible avec tes objets
    public void buildRoutingTable(Map<UrlMethod, UrlMethodMapping> routes, Class<?> annotationController) {
        
        // 1. On récupère directement les classes de contrôleurs
        List<Class<?>> controllers = getListClass(annotationController);

        for (Class<?> controller : controllers) {
            // getDeclaredMethods() évite de récupérer les méthodes héritées d'Object (comme getClass, hashCode, etc.)
            for (Method method : controller.getDeclaredMethods()) {

                if (!method.isAnnotationPresent(UrlMapping.class)) {
                    continue;
                }

                String url = method.getAnnotation(UrlMapping.class).url();
                String httpMethod = method.getAnnotation(UrlMapping.class).method().toUpperCase();

                UrlMethod urlMethod = new UrlMethod(url, httpMethod);

                // Instanciation de ton mapping en lui passant la classe et la méthode dans le constructeur !
                UrlMethodMapping mapping = new UrlMethodMapping(controller, method);

                // Gestion des doublons de route
                if (routes.containsKey(urlMethod)) {
                    throw new RuntimeException(
                            "Erreur de routage : Route dupliquée détectée -> " + url + " [" + httpMethod + "]");
                }

                routes.put(urlMethod, mapping);
            }
        }

    }
     public Map<UrlMethod, UrlMethodMapping> mappingMethodUrl(Class<?> annotationController) throws Exception {
        Map<UrlMethod, UrlMethodMapping> mapping = new HashMap<>();
        String namePackage = servletContext.getInitParameter("package.controller");
        Reflections reflections = new Reflections(namePackage);
        // 2. Extraire toutes les classes annotées par @MaSuperAnnotation
        Set<Class<?>> classes = reflections.get(Scanners.TypesAnnotated.with(annotationController).asClass());
        for (Class<?> cls : classes) {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(annotation.UrlMapping.class)) {
                    UrlMethodMapping urlmethodmapping = new UrlMethodMapping(cls, method) ; 
                    String url = method.getAnnotation(annotation.UrlMapping.class).url();
                    String methodType = method.getAnnotation(annotation.UrlMapping.class).method();
                    UrlMethod urlmethod = new UrlMethod(url , methodType) ;
                    if(mapping.containsKey(urlmethod)){
                        throw new Exception("Erreur de routage : L'URL [" + url + "] avec la méthode [" + methodType + "] est déjà associée à un autre contrôleur !");
                    } else{
                        mapping.put(urlmethod, urlmethodmapping); 
                    }
                }
            }
        } 
        return mapping ; 
    } 

    public String resolveView(String viewName) {
        if (viewName == null) {
            return null;
        }

        // 1. On récupère le préfixe et le suffixe du web.xml
        String prefix = servletContext.getInitParameter("view.prefix");
        String suffix = servletContext.getInitParameter("view.suffix");

        // Valeurs de secours (fallback) si ce n'est pas configuré dans le web.xml
        if (prefix == null) prefix = "/WEB-INF/views/";
        if (suffix == null) suffix = ".jsp";

        // 2. Si le chemin est déjà absolu (ex: commence par /), on le laisse tel quel
        if (viewName.startsWith("/")) {
            return viewName;
        }

        // 3. On assemble le tout
        return prefix + viewName + suffix;
    }


    public Object executeController(UrlMethodMapping routeMapping, HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        Object controllerInstance = routeMapping.getControllerClass()
                                                .getDeclaredConstructor()
                                                .newInstance();
        
        Object[] methodArguments = resolveArguments(routeMapping.getMethod(), request, response);
        return routeMapping.getMethod().invoke(controllerInstance, methodArguments);
    }

    public Object[] resolveArguments(Method method, HttpServletRequest request, HttpServletResponse response) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] arguments = new Object[parameterTypes.length];
        
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (parameterTypes[i] == HttpServletResponse.class) {
                arguments[i] = response;
            } else {
                arguments[i] = null; // Paramètre non pris en charge pour le moment
            }
        }
        return arguments;
    }

    public void handleResult(Object result, HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        if (result instanceof ModelAndView) {
            ModelAndView mv = (ModelAndView) result;
            String viewPath = resolveView(mv.getView());
            
            if (mv.getModel() != null) {
                for (Map.Entry<String, Object> entry : mv.getModel().entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            }
            request.getRequestDispatcher(viewPath).forward(request, response);
            
        } else if (result instanceof String) {
            // Affichage direct du texte
            try (PrintWriter out = response.getWriter()) {
                out.println(result);
            }
        }
    }

    public void handleError(HttpServletResponse response, Exception e) throws IOException {
        System.err.println("[Framework Error] : " + e.getMessage());
        e.printStackTrace();  
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du Framework : " + e.getMessage());
    }

    public void showDashboard(HttpServletRequest request, HttpServletResponse response, String requestURI , Map<UrlMethod, UrlMethodMapping> mapping) 
            throws IOException {
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
    }

} 

