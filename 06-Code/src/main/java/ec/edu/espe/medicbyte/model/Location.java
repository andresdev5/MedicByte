package ec.edu.espe.medicbyte.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexes;
import ec.edu.espe.medicbyte.common.core.Model;

/**
 *
 * @author Andres Jonathan J.
 */
@Entity("locations")
@Indexes(
    @Index(fields = @Field("name"), options = @IndexOptions(unique = true))
)
public class Location extends Model {
    private String name;
    private String direction;
    private String phone;
    private double latitude;
    private double longitude;
    
    public Location() {}

    public Location(String name, String direction, String phone, double latitude, double longitude) {
        this.name = name;
        this.direction = direction;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
