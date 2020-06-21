
package ec.edu.espe.medicbyte.utils;

import ec.edu.espe.medicbyte.model.Appointment;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Junior Stalin
 */
public class FileManager {

       /*File  file;
       Appointment appointment = new Appointment();
       
       
       
    public void createFile() throws IOException {
        file = new File("Appointment.txt");
        if (!file.exists()) {
            file.createNewFile();
            writeFile();
        } else {
            writeFile();
        }
    }
    
    public void writeFile() throws IOException{
         
      try (FileWriter toWriter = new FileWriter(file, true);
                PrintWriter toWriterLine = new PrintWriter(toWriter)) {
            
            toWriterLine.print("--->  " + appointment.getCode());
            toWriterLine.print("; " + appointment.getDate() );
            toWriterLine.print("; " + appointment.getHour());
            //toWriterLine.print("; " + books.getAuthor());
            toWriterLine.print(".");
            toWriterLine.print("\n");
            toWriterLine.close();
            toWriter.close();
      
     }
   }*/
  
    public void readFile(){
       try {
      File file = new File("Appointment.txt");
           try (Scanner scanner = new Scanner(file)) {
               while (scanner.hasNextLine()) {
                   String data = scanner.nextLine();
                   System.out.println(data);
             }
           }
      } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
      }
    }
}
