package ec.edu.espe.tinyio;

import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface CsvRecord {
    public CsvColumn getColumn(int index);
    public CsvColumn getColumn(String name);
    public String getColumnValue(int index);
    public String getColumnValue(String name);
    public List<CsvColumn> getColumns();
    public List<String> getColumnValues();
    public int count();
    public FileLine getLine();
}
