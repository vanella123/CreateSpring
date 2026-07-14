package utils;
import java.lang.reflect.Method;
public class UrlMethodMapping {
    private Class<?> controllerClass;
    private Method method;

    public UrlMethodMapping(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }
    public Method getMethod() {
        return method;
    }

    public void setController(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }
    public void setMethod(Method method) {
        this.method = method;
    }
    public UrlMethodMapping() {
    }

    
}
