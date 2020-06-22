package ec.edu.espe.medicbyte.utils;

import ec.edu.espe.medicbyte.model.ListMedic;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior Stalin
 */
public class FileManager {

    File file;
    ListMedic listMedic = new ListMedic();
    String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
        createFile();

    }
    public void createFile() {
        file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void writeFile(String content) {
        try (FileWriter toWriter = new FileWriter(file, true);
                PrintWriter toWriterLine = new PrintWriter(toWriter)) {

            toWriterLine.println(content);
            toWriterLine.close();
            try {
                toWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String readFile() {
        String output;
        output = new String();
        try {
            File file = new File(fileName);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    output = output + scanner.nextLine();
                    output += "\n";
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            
        }
        return output;
    }
}
