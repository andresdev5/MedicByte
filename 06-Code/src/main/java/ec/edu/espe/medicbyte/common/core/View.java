package ec.edu.espe.medicbyte.common.core;

import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public interface View {
    public void display();
    public void listen(String name, Consumer<Object> listener);
    public void listen(String name, Runnable listener);
    public void emit(String name, Object arg);
    public void emit(String name);
    public String render();
    public <T extends Object> T get(String key);
    public void set(String key, Object value);
    public <T extends Object> ViewField bindField(String name, Class<T> type);
}
