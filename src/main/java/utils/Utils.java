package utils;

import annotation.UrlMapping;
import jakarta.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
}