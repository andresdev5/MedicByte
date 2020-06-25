package ec.edu.espe.tinyio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Andres Jonathan J.
 */
final class CsvFileImpl implements CsvFile {
    private final List<FileLine> lines;
    private final List<String> columnNames;
    private final List<CsvRecord> records;
    
    public CsvFileImpl(List<FileLine> lines) {
        this.lines = lines;
        records = new ArrayList<>();
        columnNames = new ArrayList<>();
        setRecords();
    }
    
    public CsvFileImpl(List<FileLine> lines, List<String> columnNames) {
        this.lines = lines;
        records = new ArrayList<>();
        this.columnNames = columnNames;
        setRecords();
    }
    
    @Override
    public CsvRecord getRecord(int index) {
        if (index < 0 || index >= records.size()) {
            return null;
        }
        
        return records.get(index);
    }
    
    @Override
    public List<CsvRecord> getRecords() {
        return records;
    }
    
    @Override
    public List<CsvRecord> find(Function<CsvRecord, Boolean> comparator) {
        return records.stream()
            .filter(comparator::apply)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<CsvRecord> find(String columnName, String term) {
        return find(record -> {
            return record.getColumn(columnName)
                .getValue()
                .equalsIgnoreCase(term);
        });
    }
    
    @Override
    public List<CsvRecord> matches(String columnName, String regex) {
        return find(record -> {
            return record.getColumn(columnName)
                .getValue()
                .matches(regex);
        });
    }
    
    @Override
    public int count() {
        return records.size();
    }
    
    @Override
    public List<FileLine> getLines() {
        return lines;
    }
    
    private void setRecords() {
        for (FileLine line : lines) {
            records.add(new CsvRecordImpl(line, columnNames));
        }
    }
}
