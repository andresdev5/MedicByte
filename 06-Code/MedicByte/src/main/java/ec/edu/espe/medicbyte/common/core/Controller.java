package ec.edu.espe.medicbyte.common.core;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.Application;

/**
 *
 * @author Andres Jonathan J.
 */
public abstract class Controller {
    @Inject private Application container;
    
    public void doInit() {
        this.init();
    }
    
    protected View createView(Class<? extends View> viewClass) {
        View view = this.container.resolve(viewClass);
        return view;
    }
    
    protected abstract void init();
}
