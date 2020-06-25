package ec.edu.espe.tinyio;

/**
 *
 * @author Andres Jonathan J.
 */
class FileLineImpl implements FileLine {
    private final String line;
    private final int index;
    
    FileLineImpl(String line, int index) {
        this.line = line;
        this.index = index;
    }
    
    @Override
    public String text() {
        return line;
    }
    
    @Override
    public int position() {
        return index;
    }
    
    @Override
    public CsvRecord csv() {
        return new CsvRecordImpl(this);
    }
    
    @Override
    public String toString() {
        return text();
    }
}
