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

    public void register(Class<? extends Window> windowClass) {
        try {
            Window window = windowClass.newInstance();
            window.init();
            windows.put(windowClass, window);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Window get(Class<? extends Window> windowClass) {
        if (!windows.containsKey(windowClass)) {
            return null;
        }

        return windows.get(windowClass);
    }
    
    public <T extends Window> T getAs(Class<T> windowClass) {
        return (T) get(windowClass);
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
