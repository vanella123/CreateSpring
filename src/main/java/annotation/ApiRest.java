package annotation;
import java.lang.annotation.*;

@Target (ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // Visible à l'exécution

public @interface ApiRest {
    
}
