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
    
    public void add(Class<? extends Controller> controllerClass, String key) {
        add(controllerClass, key, false);
    }
    
    public void add(Class<? extends Controller> controllerClass) {
        add(controllerClass, null, false);
    }
    
    public Observable run(String key, String accessor) {
        if (key == null || !has(key)) {
            return Observable.empty();
        }
        
        Route route = get(key);
        return runController(route, accessor);
    }
    
    public Observable run(Class<? extends Controller> controllerClass, String accessor) {
        if (controllerClass == null || !has(controllerClass)) {
            return Observable.empty();
        }
        
        Route route = get(controllerClass);
        return runController(route, accessor);
    }
    
    public Observable run(String key) {
        return run(key, null);
    }
    
    public Observable run(Class<? extends Controller> controllerClass) {
        return run(controllerClass, null);
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
