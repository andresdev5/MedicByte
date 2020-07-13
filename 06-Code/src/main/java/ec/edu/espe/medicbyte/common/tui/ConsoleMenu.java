package ec.edu.espe.medicbyte.common.tui;

import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.function.Consumer;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author Andres Jonathan J.
 */
public class ConsoleMenu {
    public static enum MenuMode {
        Classic,
        Interactive
    }
    
    private MenuMode mode = MenuMode.Interactive;
    private final Console console;
    private String prompt = "Enter an option: ";
    private final AtomicBoolean isRunning;
    private final List<ConsoleMenuOption> options;
    private final List<String> prependMenuText = new ArrayList<>();
    private boolean preClear = true;
    private boolean displayOnce = false;
    
    public ConsoleMenu() {
        console = Console.getInstance();
        isRunning = new AtomicBoolean(true);
        options = new ArrayList<>();
    }
    
    public ConsoleMenuOption addOption(ConsoleMenuOption option) {
        options.add(option);
        return option;
    }
    
    public ConsoleMenuOption addOption(String label) {
        ConsoleMenuOption option = new ConsoleMenuOption(label, () -> {});
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
        
        if (consoleWidth < 0 || System.console() == null) {
            setMode(MenuMode.Classic);
            header.append(ansi().bold().bgMagenta().a(title).reset());
        } else {
            header.append(prependMenuText.stream().collect(Collectors.joining()));
            header.append(StringUtils.repeat(
                    "-", (consoleWidth / 2) - (title.length() / 2) - 5));
            header.append("| ");
            header.append(ansi().bold().bgMagenta().a(title).reset());
            header.append(" |");
            header.append(StringUtils.repeat(
                    "-", (consoleWidth / 2) - (title.length() / 2) - 5));
            header.append(System.lineSeparator());
        }
        
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

            if (shouldPreClear()) {
                console.clear();
            }

            console.echoln(header);

            List<ConsoleMenuOption> enabledOptions = options
                .stream()
                .filter(option -> option.isEnabled())
                .collect(Collectors.toList());

            for (ConsoleMenuOption option : enabledOptions) {
                String optionText = "";

                if (mode == MenuMode.Classic) {
                    optionText = String.format(
                        "%s: %s",
                        ansi().bold().fgCyan().a(Integer.toString(index + 1)).reset(),
                        option.getLabel()
                    );
                } else {
                    optionText = String.format(
                        " %s",
                        ansi().bold().a(" " + option.getLabel() + " ").reset()
                    );

                    if (selected == index) {
                        optionText = String.format(
                            "%s%s",
                            ansi().bold().bg(Ansi.Color.WHITE).a(" ").reset(),
                            ansi().bold().bg(Ansi.Color.BLUE).a(" " + option.getLabel() + " ").reset()
                        );
                    }
                }
                
                console.echoln(" " + optionText);
                index++;
            }
            
            if (mode == MenuMode.Classic) {
                console.echoln(StringUtils.repeat("-", consoleWidth - 5));
                console.newLine(2);
                
                do {
                    String label = ansi()
                        .bold().fgBrightCyan().a(prompt)
                        .reset().toString();

                    if (!isValidOption) {
                        label = ansi()
                            .bold().fgBrightRed().a("\n[invalid option]\n" + prompt)
                            .reset().toString();
                    }

                    isValidOption = false;
                    line = console.input(label);
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
                
                while (pressed != Console.Keys.ArrowUp.getValue()
                    && pressed != Console.Keys.ArrowDown.getValue()
                    && pressed != Console.Keys.Enter.getValue()) {
                    pressed = console.read();
                }
                
                if (pressed == Console.Keys.ArrowUp.getValue()) {
                    selected--;
                    selected = (selected < 0 ? enabledOptions.size() - 1 : selected);
                    continue;
                } else if (pressed == Console.Keys.ArrowDown.getValue()) {
                    selected++;
                    selected = (selected > enabledOptions.size() - 1 ? 0 : selected);
                    continue;
                } else {
                    choosed = enabledOptions.get(selected);
                }
            }
            
            console.newLine(2);
            choosed.execute();
            console.newLine(2);
            
            if (choosed.mustAwait()) {
                console
                    .echo("Press <enter> to continue...")
                    .input();
            }
            
            if (displayOnce) {
                exit();
            }
        }
    }
    
    public void display() {
        display("");
    }
    
    public ConsoleMenu once() {
        displayOnce = true;
        return this;
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

    public String getPrompt() {
        return prompt;
    }

    public boolean shouldPreClear() {
        return preClear;
    }

    public void setPreClear(boolean preClear) {
        this.preClear = preClear;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
