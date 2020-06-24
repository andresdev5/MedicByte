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
    public String[] splitAsCSVLine() {
        String[] fields = text().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        int fieldIndex = 0;
        
        for (String field : fields) {
            fields[fieldIndex++] = field.replaceAll("^\"|\"$", "");
        }
        
        return fields;
    }
    
    @Override
    public String toString() {
        return text();
    }
}
