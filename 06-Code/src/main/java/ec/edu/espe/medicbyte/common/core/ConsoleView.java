package ec.edu.espe.medicbyte.common.core;

import com.google.inject.Inject;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public abstract class ConsoleView extends BaseView {
    @Inject protected Console console;
    private Consumer<String> printer;
    
    @Override
    public void init() {}
    
    @Override
    public void display() {
        console.echoln(render());
    }
    
    public void set(String key, String value) {
        super.set(key, value);
    }
    
    @Override
    public String render() {
        return parse(template());
    }
    
    public String parse(String template) {
        for (Entry<String, Object> entry : getVars().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            
            template = template.replace("${" + key + "}", value);
        }
        
        return template;
    }
    
    protected abstract String template();
}
