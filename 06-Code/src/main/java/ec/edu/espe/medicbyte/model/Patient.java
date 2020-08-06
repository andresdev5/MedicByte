package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Patient extends User {
    private String idCard;
    private boolean affiliated;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public boolean isAffiliated() {
        return affiliated;
    }

    public void setAffiliated(boolean affiliated) {
        this.affiliated = affiliated;
    }
}
