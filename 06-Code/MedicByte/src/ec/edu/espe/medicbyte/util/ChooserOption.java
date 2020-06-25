package ec.edu.espe.medicbyte.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public class ChooserOption {
    private final String label;
    private final Consumer<ChooserOption> callback;
    private final List<Object> arguments = new ArrayList<>();
    private String key;
    
    public ChooserOption(String label, Consumer<ChooserOption> callback) {
        this.label = label;
        this.callback = callback;
    }
    
    public ChooserOption(String label) {
        this.label = label;
        this.callback = null;
    }
    
    public String getLabel() {
        return label;
    }
    
    public ChooserOption withKey(String key) {
        this.key = key;
        return this;
    }

    public String getKey() {
        return key;
    }
    
    public ChooserOption addArgument(Object argument) {
        arguments.add(argument);
        return this;
    }
    
    public ChooserOption addArgument(Object... arguments) {
        Arrays.asList(arguments).forEach(this::addArgument);
        return this;
    }
    
    public Object getArgument(int index) {
        if (index < 0 || index >= arguments.size()) {
            return null;
        }
        
        return arguments.get(index);
    }
    
    public List<Object> getArguments() {
        return arguments;
    }
    
    public void run() {
        if (callback != null) {
            callback.accept(this);
        }
    }
}
