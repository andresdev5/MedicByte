package ec.edu.espe.medicbyte.common.core;

import io.reactivex.rxjava3.core.Observable;
import java.util.function.Consumer;

/**
 * @author Jonathan Andres J.
 */
public interface ICommunicable {
    /**
     * emit an event with n parameters
     * @param eventName
     * @param parameters 
     */
    void emit(String eventName, UIEventArguments parameters);
    
    /**
     * emit an event with n parameters
     * @param eventName
     * @param parameters 
     */
    void emit(String eventName, Object... parameters);
    
    /**
     * listen an event
     * @param eventName
     * @param callback 
     */
    void listen(String eventName, Consumer<UIEventArguments> callback);
}
