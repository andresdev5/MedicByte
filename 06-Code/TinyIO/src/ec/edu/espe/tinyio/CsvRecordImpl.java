package ec.edu.espe.tinyio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andres Jonathan J.
 */
class CsvRecordImpl implements CsvRecord {
    private final FileLine line;
    private final List<CsvColumn> columns;
    private final List<String> columnNames;
    
    public CsvRecordImpl(FileLine line) {
        this.line = line;
        columnNames = Collections.emptyList();
        columns = parseColumns();
    }
    
    public CsvRecordImpl(FileLine line, List<String> columnNames) {
        this.line = line;
        this.columnNames = columnNames;
        columns = parseColumns();
    }
    
    @Override
    public CsvColumn getColumn(int index) {
        if (index < 0 || index > getColumns().size() - 1) {
            return null;
        }
        
        return columns.get(index);
    }
    
    @Override
    public CsvColumn getColumn(String columnName) {
        if (!columnNames.contains(columnName)) {
            return null;
        }
        
        CsvColumn found = columns.stream().filter(column -> {
            String name = column.getName();
            
            if (name == null) {
                return false;
            }
            
            return name.equals(columnName);
        }).findAny().orElse(null);
        
        return found;
    }
    
    @Override
    public String getColumnValue(String columnName) {
        return getColumn(columnName).getValue();
    }
    
    @Override
    public String getColumnValue(int index) {
        return getColumn(index).getValue();
    }
    
    @Override
    public List<CsvColumn> getColumns() {
        return columns;
    }
    
    @Override
    public List<String> getColumnValues() {
        return getColumns().stream()
            .map(column -> column.getValue())
            .collect(Collectors.toList());
    }
    
    @Override
    public int count() {
        return getColumns().size();
    }
    
    @Override
    public FileLine getLine() {
        return line;
    }
    
    private List<CsvColumn> parseColumns() {
        List<CsvColumn> parsed = new ArrayList<>();
        String[] tokens = line.text().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        int index = 0;
        
        for (String field : tokens) {
            CsvColumn column;
            String content = field.replaceAll("^\"|\"$", "");
            String columnName = null;
            
            try {
                columnName = columnNames.get(index);
            } catch (Exception exception) {}
            
            if (columnName == null) {
                column = new CsvColumnImpl(index, content);
            } else {
                column = new CsvColumnImpl(index, content, columnName);
            }
            
            parsed.add(column);
            index++;
        }
        
        return parsed;
    }
}
