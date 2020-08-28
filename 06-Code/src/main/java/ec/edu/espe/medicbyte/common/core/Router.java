package ec.edu.espe.medicbyte.common.core;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.Application;
import ec.edu.espe.medicbyte.view.MainWindow;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.SingleSubject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Router class that handle controllers
 * 
 * @author Andres Jonathan J.
 */
public class Router {
    private final Application container;
    private final ArrayList<Route> routes = new ArrayList<>();
    private final SingleSubject onRunController = SingleSubject.create();
    
    @Inject
    public Router(Application container) {
        this.container = container;    
    }
    
    /**
     * Add a controller class to the router
     * 
     * @param controllerClass controller class
     * @param key the key/alias for this route
     * @param initialize if true the controller is instanced
     */
    public void add(Class<? extends Controller> controllerClass, String key, boolean initialize) {
        if (has(controllerClass)) {
            return;
        }
        
        try {
            Route route = new Route(key, controllerClass);
            
            if (initialize) {
                Controller controller = container.resolve(controllerClass);
                route.setController(controller);
                route.setResolved(true);
                controller.setAccessors();
            }
            
            routes.add(route);
        } catch (Exception ex) {
            Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Add a controller class to the router
     * 
     * @param controllerClass controller class
     * @param key the key/alias for this route
     */
    public void add(Class<? extends Controller> controllerClass, String key) {
        add(controllerClass, key, false);
    }
    
    /**
     * Add a controller class to the router
     * 
     * @param controllerClass controller class
     */
    public void add(Class<? extends Controller> controllerClass) {
        add(controllerClass, null, false);
    }
    
    /**
     * run a controller accessor
     * 
     * @param key
     * @param accessor
     * @return 
     */
    public Observable run(String key, String accessor) {
        if (key == null || !has(key)) {
            return Observable.empty();
        }
        
        Route route = get(key);
        return runController(route, accessor);
    }
    
    /**
     * run a controller accessor
     * 
     * @param controllerClass
     * @param accessor
     * @return 
     */
    public Observable run(Class<? extends Controller> controllerClass, String accessor) {
        if (controllerClass == null || !has(controllerClass)) {
            return Observable.empty();
        }
        
        Route route = get(controllerClass);
        return runController(route, accessor);
    }
    
    /**
     * run init method controller given a key
     * 
     * @param key
     * @return 
     */
    public Observable run(String key) {
        return run(key, null);
    }
    
    /**
     * run the init method controller given a class controller
     * 
     * @param controllerClass
     * @return 
     */
    public Observable run(Class<? extends Controller> controllerClass) {
        return run(controllerClass, null);
    }
    
    /**
     * get a route given a key
     * 
     * @param key
     * @return 
     */
    public Route get(String key) {
        Route found = routes.stream()
            .filter((route) -> route.getKey() != null && route.getKey().equals(key))
            .findAny()
            .orElse(null);
        
        return found;
    }
    
    /**
     * get a route given a controller class
     * 
     * @param target
     * @return 
     */
    public Route get(Class<? extends Controller> target) {
        Route found = routes.stream()
            .filter((route) -> route.getControllerClass() == target)
            .findAny()
            .orElse(null);
        
        return found;
    }
    
    /**
     * check if a controller was registered given a key
     * @param key
     * @return 
     */
    public boolean has(String key) {
        return routes.stream().anyMatch((Route route) -> {
            return route.getKey() != null && route.getKey().equals(key);
        });
    }
    
    /**
     * 
     * @param controllerClass
     * @return 
     */
    public boolean has(Class<? extends Controller> controllerClass) {
        return routes.stream().anyMatch((Route route) -> {
            return route.getControllerClass() == controllerClass;
        });
    }
    
    /**
     * run a controller by route and accessor
     * @param route
     * @param accessor
     * @return 
     */
    private Observable runController(Route route, String accessor) {
        if (route == null) {
            return Observable.empty();
        }
        
        if (!route.isResolved()) {
            Controller controller = container.resolve(route.getControllerClass());
            route.setResolved(true);
            route.setController(controller);
            controller.setAccessors();
        }
        
        Controller controller = route.getController();
        
        Observable.create((emitter) -> {
            if (controller != null) {
                try {
                    if (accessor == null) {
                        controller.doInit();
                    } else {
                        controller.runAccessor(accessor);
                    }
                    
                    onRunController.onSuccess(controller);
                } catch (Exception exception) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, exception);
                    onRunController.onError(exception);
                }
            }
        }).subscribe().dispose();
        
        return onRunController.toObservable();
    }
}
