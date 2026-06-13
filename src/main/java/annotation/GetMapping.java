package annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD) // METHOD signifie qu'on ne peut l'appliquer qu'aux méthodes
@Retention(RetentionPolicy.RUNTIME)
public @interface GetMapping {
    String value(); // Permet de passer une variable, par exemple @GetMapping(value = "/accueil")
}