package ec.edu.espe.medicbyte.utils;

import java.awt.Robot;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

/**
 *
 * @author Andres Jonathan J.
 */
public class Console {
    private static Console instance;
    private PrintStream printer;
    private Scanner reader;
    
    private Console() {
        this.setupIO();
    }
    
    public static Console getInstance() {
        if (instance == null) {
            instance = new Console();
        }
        
        return instance;
    }
    
    public Console echo(Object content) {
        printer.print(content);
        return this;
    }
    
    public Console echoln(Object content) {
        return echo(content).newLine();
    }
    
    public Console newLine(int times) {
        String breakLines = new String(
                new char[times]).replace("\0", System.lineSeparator());
        return echo(breakLines);
    }
    
    public Console newLine() {
        return newLine(1);
    }
    
    public Console echofmt(String content, Object... args) {
        printer.format(content, args);
        return this;
    }
    
    public String read(String label) {
        if (label != null) {
            System.out.print(label);
        }
        
        String input = reader.nextLine();
        return input;
    }
    
    public String read() {
        return read(null);
    }
    
    public String read(String label, Function<String, Boolean> validator) {
        String input;
        boolean valid;
        
        do {
            input = read(label);
            valid = validator.apply(input);
        } while (!valid);
        
        return input;
    }
    
    public int readInt(String label) {
        int input;
        
        do {
            System.out.print(label);
            
            try {
                input = reader.nextInt();
                reader.nextLine();
                break;
            } catch (Exception exception) {
                reader.next();
            }
        } while (true);
        
        return input;
    }
    
    public int readInt() {
        return readInt("");
    }
    
    public void pause() {
        newLine();
        echoln("press <enter> to continue...");
        newLine();
        read();
    }
    
    public void clear() {
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
        } catch (Exception exception) {
            echo("\033[H\033[2J");
            printer.flush();
        }
    }
    
    private void setupIO() {
        try {
            printer = new PrintStream(
                    new BufferedOutputStream(System.out), true, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            printer = System.out;
        }
        
        try {
            reader = new Scanner(new InputStreamReader(System.in, "UTF-8"));
        } catch (UnsupportedEncodingException exception) {
            reader = new Scanner(System.in);
        }
    }
}
