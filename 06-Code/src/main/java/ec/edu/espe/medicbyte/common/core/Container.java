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
    
    /**
     * IOC container
     * 
     * @param module 
     */
    public Container(AbstractModule module) {
        injector = Guice.createInjector(module);
        this.module = module;
    }
    
    /**
     * resolve an instance given an injector key
     * 
     * @param <T> instance instance key type
     * @param key instance instance key
     * 
     * @return 
     */
    public <T extends Object> T resolve(Key<T> key) {
        return injector.getInstance(key);
    }
    
    /**
     * resolve an instance given Class type
     * 
     * @param <T> the instance Class date type
     * @param type the instance Class
     * @return 
     */
    public <T extends Object> T resolve(Class<T> type) {
        return injector.getInstance(type);
    }
    
    /**
     * 
     * @return the container injector
     */
    public Injector getInjector() {
        return injector;
    }
}
