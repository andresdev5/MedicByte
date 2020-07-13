package ec.edu.espe.medicbyte.common.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

/**
 *
 * @author Andres Jonathan J.
 */
public abstract class Container {
    private final AbstractModule module;
    private final Injector injector;
    
    public Container(AbstractModule module) {
        injector = Guice.createInjector(module);
        this.module = module;
    }
    
    public <T extends Object> T resolve(Key<T> key) {
        return injector.getInstance(key);
    }
    
    public <T extends Object> T resolve(Class<T> type) {
        return injector.getInstance(type);
    }
    
    public Injector getInjector() {
        return injector;
    }
}
