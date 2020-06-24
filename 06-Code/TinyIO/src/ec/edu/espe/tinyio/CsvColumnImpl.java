package ec.edu.espe.tinyio;

/**
 *
 * @author Andres Jonathan J.
 */
final class CsvColumnImpl implements CsvColumn {
    private final int index;
    private final String value;
    private final String name;
    
    public CsvColumnImpl(int index, String value) {
        this.index = index;
        this.value = value;
        this.name = null;
    }
    
    public CsvColumnImpl(int index, String value, String name) {
        this.index = index;
        this.value = value;
        this.name = name;
    }
    
    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getValue() {
        return value;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
