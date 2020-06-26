package ec.edu.espe.medicbyte.model;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Appointment {

    private int id;
    private Date date;
    private LocalTime hour;
    private Medic medic;
    private boolean taken = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
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

    public String getFormatDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public String getFormatDate() {
        return getFormatDate("dd/MM/yyyy");
    }

    @Override
    public String toString() {        
        return id + "," + "\"" + getFormatDate() + "\"," + "\"" + hour + "\","
                + medic.getId() + "," + (taken ? 1 : 0);
    }
}
