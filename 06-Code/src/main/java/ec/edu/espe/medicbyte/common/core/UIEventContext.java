package ec.edu.espe.medicbyte.common.core;

import java.util.function.Consumer;

/**
 * @author Jonathan Andres J.
 */
class UIEventContext {
    private final String name;
    private final Consumer<UIEventArguments> callback;

    public UIEventContext(String name, Consumer<UIEventArguments> callback) {
        this.name = name;
        this.callback = callback;
    }

    public String getName() {
        return name;
    }

    public Consumer<UIEventArguments> getCallback() {
        return callback;
    }
}
