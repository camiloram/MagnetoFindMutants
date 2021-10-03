package co.com.magneto.magnetofindmutants.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * @author Camilo Andres Ramirez Pava
 * @versión 1.0
 */
@javax.ws.rs.ApplicationPath("")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.com.magneto.magnetofindmutants.rest.FindMutantsResource.class);
    }
    
}
