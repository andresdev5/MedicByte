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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class<? extends Controller> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<? extends Controller> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
