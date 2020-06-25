package ec.edu.espe.medicbyte.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.Function;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.LineReader.Option;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;
import ec.edu.espe.medicbyte.util.StringUtils;
import java.awt.Robot;
import java.util.Locale;
import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Attributes;

import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author jon_m
 */
public class Console {
    private static Console instance;
    private LineReader reader;
    private Terminal terminal;
    
    private Console() {
        try {
            this.setup();
        } catch (IOException exception) {
            System.err.print("Error while setup console");
            System.err.println(exception);
            System.exit(-1);
        }
    }
    
    public static Console getInstance() {
        if (instance == null) {
            instance = new Console();
        }
        
        return instance;
    }
    
    public <T extends Object> T input(Class<T> type, String label) {
        Object value = new Object();
        
        while(true) {
            String line = reader.readLine(label);
            
            if (line.trim().isEmpty()) {
                continue;
            }
            
            try {
                if (type.isAssignableFrom(String.class)) {
                    value = line;
                }

                if (type.isAssignableFrom(Integer.class)) {
                    value = Integer.parseInt(line);
                }

                if (type.isAssignableFrom(Double.class)) {
                    value = Double.parseDouble(line);
                }

                if (type.isAssignableFrom(Float.class)) {
                    value = Float.parseFloat(line);
                }

                if (type.isAssignableFrom(Boolean.class)) {
                    value = Boolean.parseBoolean(line);
                }
            } catch (NumberFormatException exception) {
                continue;
            }
            
            try {
                T casted = (T) value;
            } catch (ClassCastException exception) {
                continue;
            }
            
            break;
        }
        
        return (T) value;
    }
    
    public <T extends Object> T input(
        Class<T> type, String label, Function<T, Boolean> validation) {
        T value;
        boolean valid;
        
        do {
          value = input(type, label);
          valid = validation.apply(value);
        } while (!valid);
        
        return value;
    }
    
    public String input(String label, Function<String, Boolean> validation) {
        String value;
        
        try {
            value = input(String.class, label, validation);
        } catch (Exception exception) {
            value = null;
        }
        
        return value;
    }
    
    public String input(String label) {
        String value = "";
        
        do {
            try {
                value = reader.readLine(label);
                terminal.flush();
            } catch (EndOfFileException | UserInterruptException exception) {}
            
            if (value.trim().isEmpty()) {
                continue;
            }
            
            break;
        } while (true);
        
        return value;
    }
    
    public String input() {
        try {
            String line = reader.readLine();
            terminal.flush();
            return line;
        } catch (EndOfFileException | UserInterruptException exception) {
            return "";
        }
    }
    
    public int read() {
        int captured = -2;
        
        Attributes attributes = terminal.getAttributes();
        terminal.enterRawMode();
        
        while (true) {
            try {
                int c = terminal.reader().read(1000L);
                
                if (c == 27) {
                    if (terminal.reader().read(1000) == 79) {
                        captured = terminal.reader().read(1000);
                    }
                } else {
                    captured = c;
                }
                
                terminal.flush();
                terminal.reader().reset();
                terminal.reader().skip(999);
                terminal.writer().flush();
            } catch (Exception exception) {}
            
            if (captured != -2 && captured != -1) {
                break;
            }
        }
        
        terminal.resume();
        terminal.setAttributes(attributes);
        
        return captured;
    }
    
    public Console echo(String template, Object ...args) {
        terminal.writer().printf(template, args);
        return this;
    }
    
    public Console echo(Object content) {
        terminal.writer().print(content.toString());
        return this;
    }

    public Console echoln(Object content) {
        return echo(content).newLine();
    }

    public Console echoln(String template, Object ...args) {
        return echo(template, args).newLine();
    }

    public Console error(Object content) {
        return echo(ansi().fgBrightRed().a(content).reset().toString());
    }
    
    public Console error(String template, Object ...args) {
        return echo(String.format(
                ansi().fgBrightRed().a(template).reset().toString(), args));
    }

    public Console errorln(Object content) {
        return error(content).newLine();
    }
    
    public Console errorln(String template, Object ...args) {
        return error(template, args).newLine();
    }
    
    public Console newLine() {
        return echo(System.lineSeparator());
    }
    
    public Console newLine(int repeat) {
        return echo(StringUtils.repeat(System.lineSeparator(), repeat));
    }

    public Console clear() {
        String os = System
            .getProperty("os.name", "generic")
            .toLowerCase(Locale.ENGLISH);
        
        try {
            if (System.console() == null) {
                Robot pressbot = new Robot();
                pressbot.keyPress(17);
                pressbot.keyPress(76);
                pressbot.keyRelease(17);
                pressbot.keyRelease(76);
            }
            
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
            
            terminal.puts(Capability.clear_screen);
            terminal.flush();
        } catch (Exception exception) {
            echo("\033[H\033[2J");
            terminal.flush();
        }
        
        return this;
    }

    public void close() {
        try {
            terminal.close();
            AnsiConsole.systemUninstall();
        } catch (IOException e) {
            errorln(e);
        }
    }
    
    public static boolean confirm(String prompt) {
        String value = Console.getInstance().input(prompt + " (y/n): ", (input) -> {
            return input.trim().equalsIgnoreCase("y")
                || input.trim().equalsIgnoreCase("n");
        });
        
        return value.equals("y");
    }
    
    public Terminal getTerminal() {
        return terminal;
    }
    
    public LineReader getReader() {
        return reader;
    }
    
    private void setup() throws IOException {
        AnsiConsole.systemInstall();
        
        terminal = TerminalBuilder.builder()
            .encoding(Charset.forName("UTF-8"))
            .jna(true)
            .jansi(true)
            .streams(System.in, System.out)
            .system(true)
            .build();
        
        reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .variable(LineReader.INDENTATION, 2)
                .option(Option.INSERT_BRACKET, true)
                .build();

        terminal.echo(false);
    }
}
