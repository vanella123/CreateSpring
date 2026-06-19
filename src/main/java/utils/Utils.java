package utils;
import java.io.InputStream;
import java.util.Set;

import javax.print.Doc;

import jakarta.servlet.ServletContext;
import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import annotation.*;
import java.util.List;
import java.util.ArrayList;
public class Utils {
    private ServletContext servletContext;

    public Utils(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getNamePackage(String path) {
        try {
            String namePackage = null;
    // lis les flux d'octet du fichier web.xml
            String file = servletContext.getRealPath(path);
            /* cree une variable en xml 
                <configuration>
                    <mon-parametre>ma_valeur</mon-parametre>
                </configuration>
            */
            // 
            if(file !=null){
                File f = new File(file);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(f);

                // Step 3: Normalize the XML structure (Recommended)
                doc.getDocumentElement().normalize();

                // Step 4: Get all elements with the tag name "mon-parametre"
                NodeList ParamList = doc.getElementsByTagName("configuration");
                for (int i = 0; i < ParamList.getLength(); i++) {
                    Node node = ParamList.item(i);
                    // Ensure the node is an Element node (ignores whitespaces and comments)
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        // Step 5: Get the value of the "mon-parametre" element
                        namePackage = element.getElementsByTagName("mon-parametre").item(0).getTextContent();
                    }
                }
            }
            return namePackage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    } 
    // public void getListController(){

    // }
    public List<String> getListClass(Class<?> annotationController){
        List<String> listClass = new ArrayList<>();
        // 1. Initialiser le scanner sur le package cible
        String namePackage = getNamePackage("/WEB-INF/web.xml");
        Reflections reflections = new Reflections(namePackage);
        // 2. Extraire toutes les classes annotées par @MaSuperAnnotation
        Set<Class<?>> classes = reflections.get(Scanners.TypesAnnotated.with(annotationController).asClass());
        for (Class<?> cls : classes) {
            listClass.add(cls.getName());
        }
        return listClass;
    }
} 
