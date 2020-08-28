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
@Entity("specialities")
@Indexes(
    @Index(fields = @Field("name"), options = @IndexOptions(unique = true))
)
public class Speciality extends Model {
    private String name;
    
    public Speciality() {}
    
    public Speciality(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
