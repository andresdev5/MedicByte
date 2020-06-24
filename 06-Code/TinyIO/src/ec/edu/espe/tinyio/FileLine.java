package ec.edu.espe.tinyio;



/**
 *
 * @author Andres Jonathan J.
 */
public interface FileLine {
    public String text();    
    public int position();    
    public String[] splitAsCSVLine();
}
