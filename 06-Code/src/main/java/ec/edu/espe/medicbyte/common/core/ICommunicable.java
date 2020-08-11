package ec.edu.espe.medicbyte.common.core;

import io.reactivex.rxjava3.core.Observable;
import java.util.function.Consumer;

/**
 * @author Jonathan Andres J.
 */
public interface ICommunicable {
    void emit(String eventName, UIEventArguments parameters);
    void emit(String eventName, Object... parameters);
    void listen(String eventName, Consumer<UIEventArguments> callback);
}
