package ec.edu.espe.tinyio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Andres Jonathan J.
 */
public final class FileManager {
    private final File file;
    
    public FileManager(String filename) {
        this.file = new File(filename);
    }
    
    public FileManager(String filename, boolean creates) {
        this.file = new File(filename);
        
        if (creates) {
            create();
        }
    }
    
    public List<FileLine> read() {
        List<FileLine> lines = new ArrayList<>();
        Stream<String> rawLines;
        
        try {
            rawLines = Files.lines(file.toPath()).filter(line -> !line.isEmpty());
        } catch (IOException | InvalidPathException exception) {
            return lines;
        }
        
        int index = 0;
        
        for (String line : rawLines.collect(Collectors.toList())) {
            lines.add(new FileLineImpl(line, index++));
        }
        
        return lines;
    }
    
    public void write(String line) {
        if (!file.canWrite()) {
            return;
        }
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
            new FileOutputStream(file, true), Charset.forName("UTF-8")))) {
            writer.println(line);
            writer.close();
        } catch (Exception exception) {}
    }
    
    public void write(List<String> lines) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
            new FileOutputStream(file, true), Charset.forName("UTF-8")))) {
            lines.forEach((line -> {
                writer.println(line);
            }));
            writer.close();
        } catch (Exception exception) {}
    }
    
    public void write(String... lines) {
        Arrays.asList(lines).forEach(this::write);
    }
    
    public List<FileLine> find(Function<FileLine, Boolean> comparable) {
        return read().stream()
            .filter((line) -> comparable.apply(line))
            .collect(Collectors.toList());
    }
    
    public FileLine findFirst(Function<FileLine, Boolean> comparable) {
        List<FileLine> found = find(comparable);
        
        if (found.isEmpty()) {
            return null;
        }
        
        return found.get(0);
    }
    
    public List<FileLine> remove(Function<FileLine, Boolean> comparable) {
        List<FileLine> lines = read().stream().filter(line -> {
            return !comparable.apply(line);
        }).collect(Collectors.toList());
        
        clear();
        lines.forEach((line) -> write(line.text()));
        
        return lines;
    }
    
    public List<FileLine> remove(int position) {
        List<FileLine> lines = read().stream().filter(line -> {
            return position != line.position();
        }).collect(Collectors.toList());
        
        clear();
        lines.forEach((line) -> write(line.text()));
        
        return lines;
    }
    
    public List<FileLine> remove(FileLine line) {
        return remove(line.position());
    }
    
    public void clear() {
        try {
            FileChannel
                .open(file.toPath(), StandardOpenOption.WRITE)
                .truncate(0)
                .close();
        } catch (IOException ex) {}
    }
    
    public int countLines() {
        if (!file.exists()) {
            return 0;
        }
        
        try {
            Stream<String> lines = Files.lines(file.toPath());
            return (int) lines.count();
        } catch (IOException ex) {
            return 0;
        }
    }
    
    public void create() {
        create(false);
    }
    
    public void create(boolean overwrite) {
        try {
            if (overwrite) {
                file.delete();
            }
            
            file.createNewFile();
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }
    
    public CsvFile toCsv() {
        return new CsvFileImpl(read());
    }
    
    public CsvFile toCsv(String... columnNames) {
        return new CsvFileImpl(read(), Arrays.asList(columnNames));
    }
}
