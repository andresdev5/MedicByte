package ec.edu.espe.medicbyte.common.core;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres Jonathan J.
 */
public class Router {
    private final Container container;
    private final ArrayList<Route> routes = new ArrayList<>();
    
    public Router(Container container) {
        this.container = container;    
    }
    
    public void add(Class<? extends Controller> controllerClass, String key, boolean initialize) {
        if (has(controllerClass)) {
            return;
        }
        
        try {
            Route route = new Route(key, controllerClass);
            
            if (initialize) {
                route.setController(container.resolve(controllerClass));
                route.setResolved(true);
            }
            
            routes.add(route);
        } catch (Exception ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add(Class<? extends Controller> controllerClass, String key) {
        add(controllerClass, key, false);
    }
    
    public void add(Class<? extends Controller> controllerClass) {
        add(controllerClass, null, false);
    }
    
    public void run(Class<? extends Controller> controllerClass) {
        if (controllerClass == null || !has(controllerClass)) {
            return;
        }
        
        Route route = get(controllerClass);
        runController(route);
    }
    
    public void run(String key) {
        if (key == null || !has(key)) {
            return;
        }
        
        Route route = get(key);
        runController(route);
    }
    
    public Route get(String key) {
        Route found = routes.stream()
            .filter((route) -> route.getKey() != null && route.getKey().equals(key))
            .findAny()
            .orElse(null);
        
        return found;
    }
    
    public Route get(Class<? extends Controller> target) {
        Route found = routes.stream()
            .filter((route) -> route.getControllerClass() == target)
            .findAny()
            .orElse(null);
        
        return found;
    }
    
    public boolean has(String key) {
        return routes.stream().anyMatch((Route route) -> {
            return route.getKey() != null && route.getKey().equals(key);
        });
    }
    
    public boolean has(Class<? extends Controller> controllerClass) {
        return routes.stream().anyMatch((Route route) -> {
            return route.getControllerClass() == controllerClass;
        });
    }
    
    private void runController(Route route) {
        if (route == null) {
            return;
        }
        
        if (!route.isResolved()) {
            route.setController(container.resolve(route.getControllerClass()));
        }
        
        Controller controller = route.getController();
        
        if (controller != null) {
            controller.doInit();
        }
    }
}
