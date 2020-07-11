package ec.edu.espe.medicbyte.common.core;

import ec.edu.espe.medicbyte.util.StringUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.Function;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;
import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Attributes;
import java.awt.Robot;
import java.util.Locale;

import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author jon_m
 */
public class Console {
    public static enum Keys {
        ArrowUp(65),
        ArrowDown(66),
        ArrowRight(67),
        ArrowLeft(68),
        Enter(13),
        Escape(27),
        Backspace(8),
        CntrlQ(17),
        Space(32),
        Tab(9);
        
        private final int value;
        
        Keys(int value) {
           this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    };
    
    private static Console instance;
    private LineReader reader;
    private Terminal terminal;
    private boolean maskLastInput = false;
    
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
    
    @SuppressWarnings({"unchecked", "unused"})
    public <T extends Object> T input(Class<T> type, String label) {
        Object value = new Object();
        
        while(true) {
            String line;
            
            if (maskLastInput) {
                line = reader.readLine(label, '*');
                maskLastInput = false;
            } else {
                line = reader.readLine(label);
            }
            
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
                if (maskLastInput) {
                    value = reader.readLine(label, '*');
                    maskLastInput = false;
                } else {
                    value = reader.readLine(label);
                }

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
            String line;
            
            if (maskLastInput) {
                line = reader.readLine('*');
                maskLastInput = false;
            } else {
                line = reader.readLine();
            }

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
                int c = terminal.reader().read(200L);
                
                if (c == 27) {
                    if (terminal.reader().read(200) == 79) {
                        captured = terminal.reader().read(200);
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

    public Console mask() {
        maskLastInput = true;
        return this;
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
            
                if (os.contains("windows")) {
                    new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO().start().waitFor();
                } else {
                    Runtime.getRuntime().exec("clear");
                }
            }

            terminal.puts(Capability.clear_screen);

            if (os.contains("windows")) {
                terminal.flush();
            }
        } catch (Exception exception) {
            echo("\033[H\033[2J");
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

    public Console pause() {
        newLine(2);
        echo("Press any key to continue...");
        read();
        return this;
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
            .system(true)
            .build();
        
        reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        terminal.echo(false);
    }
}
