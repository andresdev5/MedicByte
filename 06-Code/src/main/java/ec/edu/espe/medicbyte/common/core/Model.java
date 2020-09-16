package ec.edu.espe.medicbyte.common.core;

import com.google.gson.GsonBuilder;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

/**
 *
 * @author Andres Jonathan J.
 */
public abstract class Model {
    @Id
    private ObjectId id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    
    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public ObjectId getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    @PrePersist
    public void prePersist() {
        createdAt = (createdAt == null) ? LocalDateTime.now() : createdAt;
        updatedAt = (updatedAt == null) ? createdAt : LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return new GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(this);
    }
}
