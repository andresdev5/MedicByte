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
@Entity("permissions")
@Indexes(
    @Index(fields = @Field("name"), options = @IndexOptions(unique = true))
)
public class Permission extends Model {
    private String name;
    private boolean allowed;
    
    public Permission() {}
    
    public Permission(String name, boolean allowed) {
        this.name = name;
        this.allowed = allowed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}
