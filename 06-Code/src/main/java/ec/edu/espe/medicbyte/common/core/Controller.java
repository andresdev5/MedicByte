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


/**
 *
 * @author Andres Jonathan J.
 */
public abstract class Controller {
    @Inject private Application container;
    Map<String, Method> accessors = new HashMap<>();
    
    public void doInit() {
        this.init();
    }
    
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
    
    public final void runAccessor(String key) {
        if (!accessors.containsKey(key)) {
            return;
        }
        
        try {
            accessors.get(key).invoke(this);
        } catch (IllegalAccessException 
                | InvocationTargetException 
                | IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
    
    protected final View createView(Class<? extends View> viewClass) {
        BaseView view = (BaseView) this.container.resolve(viewClass);
        view.init();
        return view;
    }
    
    protected abstract void init();
}
