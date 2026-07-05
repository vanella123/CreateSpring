package utils;

import java.util.Objects;

public class UrlMethod {
    private String url;
    private String method;
    public UrlMethod(String url , String method) {
        this.url = url;
        this.method = method;
    }
    public String getUrl() {
        return url; 
    }
    public String getMethod() {
        return method; 
    }
   @Override
    public boolean equals(Object obj) {
        // 1. Si c'est exactement le même objet en mémoire, ils sont égaux
        if (this == obj) {
            return true;
        }
        
        // 2. Si l'objet est nul ou n'est pas de la classe UrlMethod, ils ne sont pas égaux
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        // 3. On convertit l'objet en UrlMethod pour comparer les attributs
        UrlMethod other = (UrlMethod) obj;
        
        // 4. Comparaison de l'URL et de la méthode HTTP
        return this.url.equals(other.url) && this.method.equals(other.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }

}
