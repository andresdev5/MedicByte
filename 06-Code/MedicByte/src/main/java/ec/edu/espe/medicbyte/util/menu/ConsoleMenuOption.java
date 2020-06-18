package ec.edu.espe.medicbyte.util.menu;

import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public class ConsoleMenuOption {
    private String key;
    private String label;
    private Runnable runnableCallback;
    private Consumer<ConsoleMenuOption> consumerCallback;
    private boolean await;
    
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
}
