package annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // TYPE signifie qu'on ne peut l'appliquer qu'aux classes
@Retention(RetentionPolicy.RUNTIME) // Visible à l'exécution
public @interface Controller {
    // Une annotation peut être vide, elle sert juste de badge !
} 
