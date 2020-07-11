package ec.edu.espe.medicbyte.util.menu;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public class ConsoleMenuOption {
    private String key;
    private String label;
    private boolean enabled = true;
    private Runnable runnableCallback;
    private Consumer<ConsoleMenuOption> consumerCallback;
    private boolean await;
    private List<Object> arguments;
    private Callable<Boolean> enabledCallback = null;

    public ConsoleMenuOption(String label, Runnable callback) {
        this.label = label;
        this.runnableCallback = callback;
        this.await = true;
    }

    public ConsoleMenuOption(String label, Consumer<ConsoleMenuOption> callback) {
        this.label = label;
        this.consumerCallback = callback;
        this.await = true;
    }

    public ConsoleMenuOption(String label, Runnable callback, boolean await) {
        this.label = label;
        this.runnableCallback = callback;
        this.await = await;
    }

    public ConsoleMenuOption(String label, Consumer<ConsoleMenuOption> callback, boolean await) {
        this.label = label;
        this.consumerCallback = callback;
        this.await = await;
    }

    public ConsoleMenuOption withKey(String key) {
        this.key = key;
        return this;
    }

    public void execute() {
        if (consumerCallback != null) {
            consumerCallback.accept(this);
        } else {
            runnableCallback.run();
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setEnabled(Callable<Boolean> callback) {
        this.enabledCallback = callback;
    }

    public boolean isEnabled() {
        if (enabledCallback != null) {
            try {
                return enabledCallback.call();
            } catch (Exception e) {
                return enabled;
            }
        }
        
        return enabled;
    }
    
    public boolean hasKey() {
        return this.getKey() != null;
    }
    
    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }
    
    public boolean mustAwait() {
        return await;
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public ConsoleMenuOption addArguments(Object... arguments) {
        this.arguments.addAll(Arrays.asList(arguments));
        return this;
    }

    public ConsoleMenuOption addArgument(Object argument) {
        this.arguments.add(argument);
        return this;
    }
}
