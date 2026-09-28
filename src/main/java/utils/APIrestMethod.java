package utils;
import annotation.APIrest;

public class APIrestMethod {
    boolean checkAnnotion(String namePackage){
        Class<?> clazz = Claas.forName(namePackage);
        if(clazz.isAnnotationPresent(APIrest.class)){
            return true ; 
        }
        return false ;
    } 

    

}
