package ec.edu.espe.medicbyte.common.core;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.JFrame;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * This class represents a GUI window, each window inherit from JFrame
 * 
 * @author Andres Jonathan J.
 */
public abstract class Window extends JFrame implements ICommunicable {
    private final Set<UIEventContext> events = new HashSet<>();
    private final Map<String, Object> vars = new HashMap<>();
    
    /**
     * get a variable by key
     * 
     * @param key
     * @return 
     */
    public Object get(String key) {
        return vars.get(key);
    }
    
    /**
     * set a variable with a key
     * 
     * @param key
     * @param value 
     */
    public void set(String key, Object value) {
        Object oldValue = vars.get(key);
        
        vars.put(key, value);
        notifyChange(key, oldValue, value);
    }
    
    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent event) {
                events.clear();
                vars.clear();
            }
        });
    }
    
    /**
     * show this window
     */
    public void reveal() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        revalidate();
    }
    
    /**
     * close this window
     */
    public void close() {
        dispose();
    }

    @Override
    public void emit(String eventName, UIEventArguments parameters) {
        events.stream().filter(event -> event.getName().equals(eventName)).forEachOrdered(event -> {
            new Thread(() -> {
                event.getCallback().accept(parameters);
            }).start();
        });
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
    
    
    protected abstract void init();
    public abstract void display(View content);
    protected abstract void onChange(String name, Object oldValue, Object newValue);
}
