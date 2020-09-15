package ec.edu.espe.medicbyte.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;
import ec.edu.espe.medicbyte.common.core.Model;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
@Entity("appointments")
public class Appointment extends Model {
    public static enum Status { PENDENT, RESCHEDULED, APPROVED, CANCELLED, REJECTED, FINISHED }
    
    private LocalDate date;
    private LocalTime hour;
    @Reference private Medic medic;
    @Reference private Patient patient;
    @Reference private Speciality speciality;
    @Reference private Location location;
    private Status status;
    private String description;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    

    public void setMedic(Medic medic) {
        this.medic = medic;
    }


    public Medic getMedic() {
        return medic;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
