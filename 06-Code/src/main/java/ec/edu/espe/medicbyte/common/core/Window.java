package ec.edu.espe.medicbyte.common.core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class Window extends JFrame implements ICommunicable {
    private final Set<UIEventContext> events = new HashSet<>();
    
    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void reveal() {
        pack();
        setVisible(true);
        revalidate();
    }

    @Override
    public void emit(String eventName, UIEventArguments parameters) {
        events.stream()
            .filter(event -> event.getName().equals(eventName))
            .forEachOrdered(event -> event.getCallback().accept(parameters));
    }

    @Override
    public void emit(String eventName, Object... args) {
        emit(eventName, new UIEventArguments(args));
    }

    @Override
    public void listen(String eventName, Consumer<UIEventArguments> callback) {
        events.add(new UIEventContext(eventName, callback));
    }

    public abstract void init();
    public abstract void display(JPanel content);
}
