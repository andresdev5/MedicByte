package ec.edu.espe.medicbyte.common.core;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.HashMap;
import java.util.Map;

public class WindowsManager {
    private final Map<Class<? extends Window>, Window> windows = new HashMap<>();

    public WindowsManager() {
        setSwingLookAndFeel();
    }

    /**
     * register a window class
     * 
     * @param windowClass 
     */
    public void register(Class<? extends Window> windowClass) {
        try {
            Window window = windowClass.newInstance();
            window.init();
            windows.put(windowClass, window);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * get a window instance by its class
     * @param windowClass
     * @return 
     */
    public Window get(Class<? extends Window> windowClass) {
        if (!windows.containsKey(windowClass)) {
            return null;
        }

        return windows.get(windowClass);
    }
    
    /**
     * get a window instance by its class
     * 
     * @param <T>
     * @param windowClass
     * @return 
     */
    public <T extends Window> T getAs(Class<T> windowClass) {
        return (T) get(windowClass);
    }
    
    /**
     * rebind a window instance and register again
     * @param windowClass 
     */
    public void rebind(Class<? extends Window> windowClass) {
        if (!windows.containsKey(windowClass)) {
            return;
        }
        
        Window window = windows.get(windowClass);
        window.dispose();
        
        windows.remove(windowClass);
        register(windowClass);
    }

    private void setSwingLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException
        | InstantiationException
        | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
