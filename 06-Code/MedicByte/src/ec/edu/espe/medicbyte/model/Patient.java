package ec.edu.espe.medicbyte.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Junior Stalin Jurado Peña - ESPE - DCCO
 */
public class Patient {
    private String identificationcard;
    private String fullName;
    private String phone;
    private String email;
    private Gender gender;
    private Date birthday;

    public String getIdentificationcard() {
        return identificationcard;
    }

    public void setIdentificationcard(String identificationcard) {
        this.identificationcard = identificationcard;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public String getBirthdayFormat(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(birthday);
    }
    
    public String getBirthdayFormat() {
        return getBirthdayFormat("dd/MM/yyyy");
    }

    @Override
    public String toString() {
        return "\"" + identificationcard + "\",\"" + fullName + "\",\""
                + phone + "\",\"" + email + "\",\"" + gender.name() 
                + "\",\"" + getBirthdayFormat() + "\"";
    }
}
