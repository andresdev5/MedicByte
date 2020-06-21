package ec.edu.espe.medicbyte.common.core;

/**
 *
 * @author Andres Jonathan J.
 * @param <T>
 */
public class ViewField<T extends Object> {

    private final Class<T> type;
    private T value;
    
    public ViewField(Class<T> type) {
        this.type = type;
    }
    
    public void notifyChange() {}
    
    public void onChange() {}
    
    public T getValue() {
        return value;
    }

    public void setValue(T value) throws Exception {
        if (!value.getClass().isAssignableFrom(type)) {
            throw new Exception(String.format(
                "Invalid type for field, cannot cast %s to %s",
                value.getClass().getSimpleName(),
                this.value.getClass().getSimpleName()
            ));
        }
        
        this.value = value;
    }
}
