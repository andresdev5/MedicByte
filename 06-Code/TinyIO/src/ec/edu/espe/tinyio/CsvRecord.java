package ec.edu.espe.tinyio;

import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
public interface CsvRecord {
    public CsvColumn getColumn(int index);
    public CsvColumn getColumn(String name);
    public List<CsvColumn> getColumns();
    public int count();
    public FileLine getLine();
}
