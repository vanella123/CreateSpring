package entity; // ou ton package de modèles/utilitaires, ex: mg.itu.model

import java.util.HashMap;
import java.util.Map;
import utils.* ; 
public class ModelAndView {
    private String view; // Le nom ou le chemin de la vue (ex: "profil.jsp")
    private Map<String, Object> model; // Les données à envoyer à la vue

    // Constructeur vide (par défaut)
    public ModelAndView() {
        this.model = new HashMap<>();
    }

    // Constructeur pratique pour définir directement la vue
    public ModelAndView(String view) {
        this.view = view;
        this.model = new HashMap<>();
    }

    // Permet d'ajouter une donnée au modèle facilement (méthode chaînable)
    public void addObject(String key, Object value) {
        this.model.put(key, value);
    }

    // Getters et Setters
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
} 