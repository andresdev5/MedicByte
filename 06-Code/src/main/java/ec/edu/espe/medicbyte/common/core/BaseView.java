package ec.edu.espe.medicbyte.common.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public abstract class BaseView implements View {
    private final Map<String, ViewField> fields = new HashMap<>();
    private final Map<String, Consumer<Object>> events = new HashMap<>();
    protected final Map<String, Object> vars = new HashMap<>();
    
    @Override
    public <T extends Object> T get(String key) {
        return (T) vars.get(key);
    }
    
    @Override
    public void set(String key, Object value) {
        vars.put(key, value);
    }
    
    @Override
    public <T extends Object> ViewField<T> bindField(String name, Class<T> type) {
        ViewField<T> field = new ViewField(type);
        fields.put(name, field);
        return field;
    }
    
    @Override
    public void listen(String event, Consumer<Object> listener) {
        events.put(event, listener);
    }
    
    @Override
    public void listen(String event, Runnable listener) {
        listen(event, (arg) -> {
            listener.run();
        });
    }
    
    @Override
    public void emit(String event, Object arg) {
        if (!events.containsKey(event)) {
            return;
        }
        
        events.get(event).accept(arg);
    }
    
    @Override
    public void emit(String event) {
        emit(event, null);
    }
    
    protected Map<String, Object> getVars() {
        return vars;
    }
    
    protected void setField(String name, Object value) {
        if (!fields.containsKey(name)) {
            return;
        }
        
        ViewField field = fields.get(name);
        
        try {
            field.setValue(value);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
    protected abstract void init();
}
