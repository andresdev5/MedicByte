package ec.edu.espe.medicbyte.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public class ConsoleMenu {
    private final Console console;
    private final List<MenuOption> options;
    private final Map<String, List<String>> content;
    private boolean running = true;
    private boolean previousClear = true;
    private MenuOption lastSelectedOption;
    private String prompt = "Seleccione una opcion: ";
    
    public ConsoleMenu() {
        console = Console.getInstance();
        options = new ArrayList<>();
        content = new HashMap<>(2);
        content.put("top", new ArrayList<>()); // top menu content
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
    
    public void run() {        
        while (running) {
            display();
            requestOption();
        }
    }
    
    public MenuOption process() {
        display();
        return requestOption();
    }
    
    public void exit() {
        running = false;
    }
    
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    
    public boolean shouldPreClear() {
        return previousClear;
    }

    public void setPreClear(boolean previousClear) {
        this.previousClear = previousClear;
    }
    
    private void display() {
        int selected;
        int index = 1;
        
        if (previousClear) {
            console.clear();
        }
        
        console.newLine();
        content.get("top").forEach(console::echoln);

        for (MenuOption opt : options) {
            console.echofmt("%d: %s\n", index++, opt.getLabel());
        }

        content.get("bottom").forEach(console::echoln);
        console.newLine();
    }
    
    private MenuOption requestOption() {
        int selected;
        MenuOption option;
        
        do {
            selected = console.readInt(prompt);
        } while (selected < 1 || selected > options.size());

        option = options.get(selected - 1);

        if (option == null) {
            return null;
        }

        console.newLine(2);
        option.run();

        if (option.shouldAwait()) {
            console
                .newLine(2)
                .echo("Press <enter> to continue")
                .read();
        }
        
        return option;
    }
}
