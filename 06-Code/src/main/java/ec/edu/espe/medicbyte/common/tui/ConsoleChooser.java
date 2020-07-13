package ec.edu.espe.medicbyte.common.tui;

import ec.edu.espe.medicbyte.common.core.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Andres Jonathan J.
 */
public class ConsoleChooser {
    private final Console console = Console.getInstance();
    private final List<ChooserOption> options = new ArrayList<>();
    
    public ChooserOption addOption(ChooserOption option) {
        options.add(option);
        return option;
    }
    
    public ChooserOption addOption(String label, Consumer<ChooserOption> callback) {
        ChooserOption option = new ChooserOption(label, callback);
        options.add(option);
        return option;
    }
    
    public ChooserOption addOption(String label) {
        return addOption(label, null);
    }
    
    public ChooserOption choose(String prompt) {
        int index = 1;
        int selected;
        
        for (ChooserOption option : options) {
            console.echoln("%d: %s", index++, option.getLabel());
        }
        
        console.newLine();
        
        do {
            selected = console.input(Integer.class, prompt);
        } while (selected < 1 || selected > options.size());
        
        ChooserOption option = options.get(selected - 1);
        option.run();
        
        return option;
    }
}
