package entity; 

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private String view;
    private Map<String, Object> model = new HashMap<>();

    // Constructeur vide
    public ModelAndView() {
    }

    // Constructeur pratique passant directement le nom de la vue
    public ModelAndView(String view) {
        this.view = view;
    }

    // --- GETTERS et SETTERS ---

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    /**
     * Ajoute une donnée au modèle qui sera transmise à la vue (JSP).
     *
     * @param attributeName  
     * @param attributeValue
     */
    public void addObject(String attributeName, Object attributeValue) {
        if (this.model == null) {
            this.model = new HashMap<>();
        }
        this.model.put(attributeName, attributeValue);
    }
}