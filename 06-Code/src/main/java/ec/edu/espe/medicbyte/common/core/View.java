package ec.edu.espe.medicbyte.common.core;

import java.util.HashMap;
import javax.swing.JPanel;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 *
 * @author Jonathan Andres J.
 */
public abstract class View extends JPanel implements ICommunicable {
    private final Set<UIEventContext> events = new HashSet<>();
    private final Map<String, Object> vars = new HashMap<>();
    
    public Object get(String key) {
        return vars.get(key);
    }
    
    public void set(String key, Object value) {
        Object oldValue = vars.get(key);
        
        vars.put(key, value);
        notifyChange(key, oldValue, value);
    }

    @Override
    public final void listen(String eventName, Consumer<UIEventArguments> callback) {
        events.add(new UIEventContext(eventName, callback));
    }

    @Override
    public final void emit(String eventName, UIEventArguments parameters) {
        for (UIEventContext event : events) {
            if (!event.getName().equals(eventName)) {
                continue;
            }

            new Thread(() -> {
                event.getCallback().accept(parameters);
            }).start();
        }
    }

    @Override
    public final void emit(String eventName, Object... args) {
        emit(eventName, new UIEventArguments(args));
    }
    
    public void leave() {
        onLeave();
    }
    
    public void enter() {
        onEnter();
    }
    
    private void notifyChange(String name, Object oldValue, Object newValue) {
        onChange(name, oldValue, newValue);
    }
    
    protected abstract void onChange(String name, Object oldValue, Object newValue);
    protected abstract void onLeave();
    protected abstract void onEnter();
}
