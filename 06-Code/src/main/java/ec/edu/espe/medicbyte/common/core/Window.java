package ec.edu.espe.medicbyte.common.core;

import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class Window extends JFrame implements ICommunicable {
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
    
    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void reveal() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        revalidate();
    }

    @Override
    public void emit(String eventName, UIEventArguments parameters) {
        events.stream()
            .filter(event -> event.getName().equals(eventName))
            .forEachOrdered(event -> event.getCallback().accept(parameters));
    }

    @Override
    public void emit(String eventName, Object... args) {
        emit(eventName, new UIEventArguments(args));
    }

    @Override
    public void listen(String eventName, Consumer<UIEventArguments> callback) {
        events.add(new UIEventContext(eventName, callback));
    }
    
    private void notifyChange(String name, Object oldValue, Object newValue) {
        onChange(name, oldValue, newValue);
    }
    
    public abstract void init();
    public abstract void display(View content);
    protected abstract void onChange(String name, Object oldValue, Object newValue);
}
