package ec.edu.espe.medicbyte.model;


/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Appointment {

    private String code;
    private String date;
    private String hour;
    private Medic medic;
    private boolean taken = false;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Medic getMedic() {
        return medic;
    }
    
    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }    
    
    @Override
    public String toString() {
        // code, date, hour, medic
        return code + "," + "\"" + date + "\"," + "\"" + hour + "\"," 
            + medic.getId() + "," + (taken ? 1: 0);
    }
}
