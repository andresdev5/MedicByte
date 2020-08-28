package ec.edu.espe.medicbyte.common.core;

/**
 *
 * @author Andres Jonathan J.
 */
public class Route {
    private String key;
    private Class<? extends Controller> controllerClass;
    private Controller controller;
    private boolean resolved = false;
    
    public Route(Class<? extends Controller> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Route(String key, Class<? extends Controller> controllerClass) {
        this.key = key;
        this.controllerClass = controllerClass;
    }

    /**
     * get the route key
     * 
     * @return the route key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * set the key for this route
     * 
     * @param key 
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * get the controller class associated to this route
     * 
     * @return the controller class
     */
    public Class<? extends Controller> getControllerClass() {
        return controllerClass;
    }

    /**
     * set the controller class for this route
     * 
     * @param controllerClass 
     */
    public void setControllerClass(Class<? extends Controller> controllerClass) {
        this.controllerClass = controllerClass;
    }

    /**
     * get the controller instance for this route
     * 
     * @return controller instance
     */
    public Controller getController() {
        return controller;
    }
    
    /**
     * set the controller instance for this route
     * 
     * @param controller the controller instance
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * 
     * @return true if the controller instance was resolved
     */
    public boolean isResolved() {
        return resolved;
    }

    /**
     * set controller instance resolve status
     * @param resolved 
     */
    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
