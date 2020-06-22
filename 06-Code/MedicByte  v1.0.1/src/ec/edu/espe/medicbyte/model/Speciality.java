package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Andres Jonathan J.
 */
public enum Speciality {
    Pediatry("pediatria", 1),
    Traumatology("traumatologia", 2),
    Odontology("odontologia", 3);

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
