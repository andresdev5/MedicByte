package ec.edu.espe.medicbyte.model;

import java.time.LocalDate;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserProfile {
    public static enum Gender { MALE, FEMALE, UNSPECIFIED }
    
    private int userId;
    private byte[] avatar;
    private String fullName;
    private String phone;
    private Gender gender;
    private LocalDate birthday;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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
}
