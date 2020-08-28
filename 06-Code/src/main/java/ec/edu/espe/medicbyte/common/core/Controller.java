package ec.edu.espe.medicbyte.common.core;

import com.google.inject.Inject;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import ec.edu.espe.medicbyte.common.Application;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Base controller class
 * 
 * @author Andres Jonathan J.
 */
public abstract class Controller {
    private static class ViewEventMethod {
        public String name;
        public Method method;

        public ViewEventMethod(String name, Method method) {
            this.name = name;
            this.method = method;
        }
    }
    
    @Inject private Application container;
    private final Map<String, Method> accessors = new HashMap<>();
    private final Map<Class<? extends View>, View> views = new HashMap<>();
    private final Set<ViewEventMethod> viewEventMethods = new HashSet<>();
    
    public void doInit() {
        this.init();
    }
    
    /**
     * set controller method accessors
     */
    public final void setAccessors() {
        List<Method> methods = Arrays.asList(this.getClass().getMethods());
        Stream<Method> filtered = methods.stream().filter((Method method) -> {
            return method.isAnnotationPresent(Routed.class);
        });
        
        filtered.forEach((method) -> {
            Routed annotation = method.getAnnotation(Routed.class);
            String key = annotation.value();
            accessors.put(key, method);
        });
    }
    
    /**
     * call a decorated method accessor by key
     * 
     * @param key the accessor key
     */
    public final void runAccessor(String key) {
        if (!accessors.containsKey(key)) {
            return;
        }
        
        try {
            accessors.get(key).invoke(this);
        } catch (IllegalAccessException 
                | InvocationTargetException 
                | IllegalArgumentException exception) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
    
    /**
     * Register a view event for the controller
     * 
     * @param viewClass the view class
     */
    protected final void registerViewEvents(Class<? extends View> viewClass) {
        views.put(viewClass, instanceView(viewClass));
    }

    /**
     * get a view instance
     * 
     * @param viewClass the view class
     * @return 
     */
    protected final View getView(Class<? extends View> viewClass) {
        return views.get(viewClass);
    }

    /**
     * get and set all decorated controller methods
     */
    private void setAnnotatedViewEvents() {
        Method[] methods = this.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(UIEvent.class)) {
                continue;
            }

            UIEvent annotation = method.getAnnotation(UIEvent.class);
            String eventName = annotation.value();

            System.out.println("registering event " + eventName);
            viewEventMethods.add(new ViewEventMethod(eventName, method));
        }
    }
    
    /**
     * get an view instance
     * 
     * @param viewClass the view class
     * @return 
     */
    private View instanceView(Class<? extends View> viewClass) {
        try {
            View view = viewClass.newInstance();

            // set view events
            for (ViewEventMethod viewEventMethod  : viewEventMethods) {
                view.listen(viewEventMethod.name, (args) -> {
                    try {
                        viewEventMethod.method.invoke(this, args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
            
            return view;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    protected abstract void init();
}
