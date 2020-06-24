package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Andres Jonathan J.
 */
public enum Speciality {
    Pediatry("Pediatria", 1),
    Traumatology("Traumatologia", 2),
    Odontology("Odontologia", 3);

    private String label;
    private int id;

    Speciality(String label, int id) {
        this.label = label;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
