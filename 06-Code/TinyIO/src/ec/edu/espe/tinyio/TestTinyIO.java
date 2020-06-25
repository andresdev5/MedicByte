package ec.edu.espe.tinyio;

/**
 *
 * @author Andres Jonathan J.
 */
class TestTinyIO {
    public static void main(String[] args) {
        FileManager file = new FileManager("users.csv");
        
        file.create(true);
        file.write(
            "\"Andres\", \"Jonathan\", 27, \"ksprwhite@gmail.com\"",
            "\"Jhon\", \"doe\", 17, \"dummy_email_1@gmail.com\"",
            "\"Joe\", \"doe\", 19, \"dummy_email_2@gmail.com\"",
            "\"Anna\", \"doe\", 25, \"dummy_email_3@gmail.com\"",
            "\"Luke\", \"doe\", 28, \"dummy_email_4@gmail.com\""
        );
        
        file.remove(line -> line.csv().getColumn(0).getValue().equals("Joe"));
        
        CsvFile csv = file.toCsv("name", "surname", "age", "email");
        CsvRecord record = csv.getRecord(2);
        
        System.out.println(record.getColumn("name"));
    }
}
