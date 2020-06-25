package ec.edu.espe.tinyio;

import ec.edu.espe.tinyio.FileManager;
import java.util.List;

/**
 *
 * @author Andres Jonathan J.
 */
class TestTinyIO {
    public static void main(String[] args) {
        FileManager file = new FileManager("users.csv");
        file.create();
        
        // escritura de datos
        file.write("linea 1", "linea 2", "linea 3");
        
        // leer datos
        List<FileLine> lines = file.read();
        
        FileLine linea1 = lines.get(0);
        
        System.out.println("linea 1 -> " + linea1.text());
    }
}
