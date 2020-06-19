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
 * @author jon_m
 */
public class ConsoleMenu {
    private final Console console;
    private final AtomicBoolean isRunning;
    private final ArrayList<ConsoleMenuOption> options;
    private ArrayList<String> prependMenuText = new ArrayList<>();
    
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
        int index = 0;
        
        header.append(prependMenuText.stream().collect(Collectors.joining()));
        header.append(StringUtils.repeat(
                "-", (consoleWidth / 2) - (title.length() / 2) - 5));
        header.append("| ");
        header.append(ansi().bold().bgMagenta().a(title).reset());
        header.append(" |");
        header.append(StringUtils.repeat(
                "-", (consoleWidth / 2) - (title.length() / 2) - 5));
        header.append(System.lineSeparator());
            
        for (ConsoleMenuOption option : options) {
            header.append(String.format(
                " %s: %s",
                ansi().bold().fgCyan().a(Integer.toString(index + 1)).reset(),
                option.getLabel()
            ));
            header.append(System.lineSeparator());
            index++;
        }

        header.append(StringUtils.repeat("-", consoleWidth - 5));
        header.append(System.lineSeparator());
        
        while(isRunning.get()) {            
            int optionNumber = 0;
            boolean isValidOption = true;

            console.clear();
            console.echo(header);
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
            
            ConsoleMenuOption option = options.get(optionNumber - 1);
            console.newLine(2);
            option.execute();

            if (option.mustAwait()) {
                console
                    .newLine(3)
                    .echo("Press <enter> to continue...")
                    .input();
            }
        }

        console.close();
    }
    
    public void exit() {
        isRunning.set(false);
    }
}
