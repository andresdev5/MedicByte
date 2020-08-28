package ec.edu.espe.medicbyte.model;

import dev.morphia.annotations.Entity;
import ec.edu.espe.medicbyte.common.core.Model;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Andres Jonathan J.
 */
@Entity("profiles")
public class UserProfile extends Model {
    public static enum Gender { MALE, FEMALE, UNSPECIFIED }
    private byte[] avatar;
    private String fullName;
    private String phone;
    private Gender gender;
    private LocalDate birthday;

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatarId) {
        this.avatar = avatarId;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    
    public int getAge() {
        if (getBirthday() == null) {
            return -1;
        }
        
        return Period.between(getBirthday(), LocalDate.now()).getYears();
    }
}
