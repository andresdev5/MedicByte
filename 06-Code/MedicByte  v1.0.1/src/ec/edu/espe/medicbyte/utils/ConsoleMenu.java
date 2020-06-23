package ec.edu.espe.medicbyte.utils;

import ec.edu.espe.medicbyte.model.MenuOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public class ConsoleMenu {
    private final Console console;
    private final List<MenuOption> options;
    private final Map<String, List<String>> content;
    private AtomicBoolean running = new AtomicBoolean(true);
    private MenuOption lastSelectedOption;
    private String prompt = "seleccione una opcion: ";
    
    public ConsoleMenu() {
        console = Console.getInstance();
        options = new ArrayList<>();
        content = new HashMap<>(3);
        content.put("top", new ArrayList<>()); // top menu content
        content.put("middle", new ArrayList<>()); // middle menu content
        content.put("bottom", new ArrayList<>()); // bottom menu content
    }
    
    public MenuOption addOption(String label, Runnable callback) {
        MenuOption option = new MenuOption(label, callback);
        options.add(option);
        return option;
    }
    
    public MenuOption addOption(String label, Consumer<MenuOption> callback) {
        MenuOption option = new MenuOption(label, callback);
        options.add(option);
        return option;
    }
    
    public MenuOption addOption(String label) {
        MenuOption option = new MenuOption(label);
        options.add(option);
        return option;
    }
    
    public void prepend(String text) {
        content.get("top").add(0, text);
    }
    
    public void append(String text) {
        content.get("bottom").add(text);
    }
    
    public void display(boolean keepAlive) {
        while (running.get()) {
            int selected;
            int index = 1;
            
            try {
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start().waitFor();
            } catch (Exception exception) {}
            
            content.get("top").forEach(console::echoln);
            
            for (MenuOption opt : options) {
                console.echofmt("%d: %s\n", index++, opt.getLabel());
            }
            
            content.get("bottom").forEach(console::echoln);
            console.newLine();
            
            do {
                selected = console.readInt(prompt);
            } while (selected < 1 || selected > options.size());
            
            lastSelectedOption = options.get(selected - 1);
            lastSelectedOption.run();
            
            if (!keepAlive) {
                exit();
            }
        }
    }
    
    public void display() {
        display(true);
    }
    
    public MenuOption getLastOption() {
        return lastSelectedOption;
    }
    
    public MenuOption readOption() {
        display(false);
        return lastSelectedOption;
    }
    
    public void exit() {
        running.set(false);
    }
    
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
