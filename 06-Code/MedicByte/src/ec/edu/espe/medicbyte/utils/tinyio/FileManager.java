package ec.edu.espe.medicbyte.utils.tinyio;


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
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Andres Jonathan J.
 */
public class FileManager {
    private final File file;
    
    public FileManager(String filename) {
        this.file = new File(filename);
    }
    
    public List<FileLine> read() {
        List<FileLine> lines = new ArrayList<>();
        Stream<String> rawLines;
        
        try {
            rawLines = Files.lines(file.toPath());
        } catch (IOException | InvalidPathException exception) {
            return lines;
        }
        
        int index = 0;
        
        for (String line : rawLines.collect(Collectors.toList())) {
            lines.add(new FileLineImpl(line, index++));
        }
        
        return lines;
    }
    
    public FileLine write(String line) {
        if (!file.canWrite()) {
            return null;
        }
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
            new FileOutputStream(file, true), Charset.forName("UTF-8")))) {
            writer.println(line);
            writer.close();
        } catch (Exception exception) {
            return null;
        }
        
        return new FileLineImpl(line, countLines());
    }
    
    public List<FileLine> write(List<String> lines) {
        List<FileLine> writedLines = Collections.emptyList();
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
            new FileOutputStream(file, true), Charset.forName("UTF-8")))) {
            lines.forEach((line -> {
                writedLines.add(new FileLineImpl(line, countLines()));
                writer.println(line);
            }));
            writer.close();
        } catch (Exception exception) {}
        
        return writedLines;
    }
    
    public List<FileLine> write(String... lines) {
        return write(Arrays.asList(lines));
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
    
    public void create() throws IOException {
        create(false);
    }
    
    public void create(boolean overwrite) throws IOException {
        if (overwrite) {
            file.delete();
        }
        
        file.createNewFile();
    }
}
