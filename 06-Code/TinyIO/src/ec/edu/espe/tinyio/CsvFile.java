package ec.edu.espe.tinyio;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @author Andres Jonathan J.
 */
public interface CsvFile {
    
    public CsvRecord getRecord(int index);
    
    public List<CsvRecord> getRecords();
    
    public List<CsvRecord> find(Function<CsvRecord, Boolean> comparator);
    
    public List<CsvRecord> find(String columnName, String term);
    
    public List<CsvRecord> matches(String columnName, String regex);
    
    public int count();
    
    public List<FileLine> getLines();
}
