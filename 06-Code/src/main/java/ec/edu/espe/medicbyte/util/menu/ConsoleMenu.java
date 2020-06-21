package ec.edu.espe.medicbyte.util.menu;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.util.StringUtils;
import java.util.function.Consumer;

import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author Andres Jonathan J.
 */
public class ConsoleMenu {
    private enum Keys {
        ArrowUp(65),
        ArrowDown(66),
        ArrowRight(67),
        ArrowLeft(68),
        Enter(13),
        Backspace(8),
        CntrlQ(17),
        Space(32),
        Tab(9);
        
        private int value;
        
        Keys(int value) {
           this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    };
    
    public static enum MenuMode {
        Classic,
        Interactive
    }
    
    private MenuMode mode = MenuMode.Interactive;
    private final Console console;
    private final AtomicBoolean isRunning;
    private final ArrayList<ConsoleMenuOption> options;
    private final ArrayList<String> prependMenuText = new ArrayList<>();
    
    @Inject
    public ConsoleMenu(Console console) {
        this.console = console;
        isRunning = new AtomicBoolean(true);
        options = new ArrayList<>();
    }
    
    public ConsoleMenuOption addOption(ConsoleMenuOption option) {
        options.add(option);
        return option;
    }
    
    public ConsoleMenuOption addOption(String label, Runnable callback) {
        ConsoleMenuOption option = new ConsoleMenuOption(label, callback);
        options.add(option);
        return option;
    }
    
    public ConsoleMenuOption addOption(
            String label, Runnable callback, boolean mustAwait) {
        ConsoleMenuOption option = new ConsoleMenuOption(label, callback, mustAwait);
        options.add(option);
        return option;
    }
    
    public ConsoleMenuOption addOption(String label, Consumer<ConsoleMenuOption> callback) {
        ConsoleMenuOption option = new ConsoleMenuOption(label, callback);
        options.add(option);
        return option;
    }
    
    public ConsoleMenuOption addOption(
            String label, Consumer<ConsoleMenuOption> callback, boolean mustAwait) {
        ConsoleMenuOption option = new ConsoleMenuOption(label, callback, mustAwait);
        options.add(option);
        return option;
    }
    
    /**
     * prepend a text to whole menu content
     * 
     * @param text 
     */
    public void prepend(String text) {
        prependMenuText.add(text);
    }
    
    public void display(String title) {
        StringBuilder header = new StringBuilder();
        String line;
        int consoleWidth = console.getTerminal().getWidth();
        
        header.append(prependMenuText.stream().collect(Collectors.joining()));
        header.append(StringUtils.repeat(
                "-", (consoleWidth / 2) - (title.length() / 2) - 5));
        header.append("| ");
        header.append(ansi().bold().bgMagenta().a(title).reset());
        header.append(" |");
        header.append(StringUtils.repeat(
                "-", (consoleWidth / 2) - (title.length() / 2) - 5));
        header.append(System.lineSeparator());
        
        int selected = 0;
        int availableOptions = (int) options
                .stream()
                .filter(option -> option.isEnabled())
                .count();
        
        while(isRunning.get() && availableOptions > 0) {
            int index = 0;
            int optionNumber = 0;
            boolean isValidOption = true;
            ConsoleMenuOption choosed;

            console.clear();
            console.echoln(header);
            
            for (ConsoleMenuOption option : options) {
                if (!option.isEnabled()) {
                    continue;
                }
                
                String optionText = String.format(
                    " %s: %s",
                    ansi().bold().fgCyan().a(Integer.toString(index + 1)).reset(),
                    option.getLabel()
                );
                
                if (mode == MenuMode.Interactive && selected == index) {
                    optionText = ansi().bold().bgBrightCyan().a(String.format(
                        " %s: %s",
                        Integer.toString(index + 1),
                        option.getLabel()
                    )).reset().toString();
                }
                
                console.echoln(optionText);
                index++;
            }
            
            if (mode == MenuMode.Classic) {
                console.echoln(StringUtils.repeat("-", consoleWidth - 5));
                console.newLine(2);
                
                do {
                    String label = ansi()
                        .bold().fgBrightCyan().a("Enter an option")
                        .reset().toString();

                    if (!isValidOption) {
                        label = ansi()
                            .bold().fgBrightRed().a("Enter a valid option")
                            .reset().toString();
                    }

                    isValidOption = false;
                    line = console.input(label + ": ");
                    line = line.trim();

                    if (line.isEmpty()) {
                        continue;
                    }

                    try {
                        optionNumber = Integer.parseInt(line);
                    } catch (NumberFormatException ex) {
                        optionNumber = 0;
                    }

                    isValidOption = !(optionNumber <= 0 || optionNumber > options.size());
                } while (!isValidOption);

                choosed = options.get(optionNumber - 1);
            } else {
                int pressed = console.read();
                
                while (pressed != Keys.ArrowUp.getValue()
                    && pressed != Keys.ArrowDown.getValue()
                    && pressed != Keys.Enter.getValue()) {
                    pressed = console.read();
                }
                
                if (pressed == Keys.ArrowUp.getValue()) {
                    selected--;
                    selected = (selected < 0 ? options.size() - 1 : selected);
                    continue;
                } else if (pressed == Keys.ArrowDown.getValue()) {
                    selected++;
                    selected = (selected > options.size() - 1 ? 0 : selected);
                    continue;
                } else {
                    choosed = options.get(selected);
                }
            }
            
            console.newLine(2);
            choosed.execute();
            
            if (choosed.mustAwait()) {
                console
                    .newLine(3)
                    .echo("Press <enter> to continue...")
                    .input();
            }
        }
    }
    
    public void exit() {
        isRunning.set(false);
    }

    public void reset() {
        isRunning.set(true);
        options.clear();
    }
    
    public void setMode(MenuMode mode) {
        this.mode = mode;
    }
}
