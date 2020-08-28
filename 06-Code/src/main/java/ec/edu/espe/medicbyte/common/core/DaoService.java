package ec.edu.espe.medicbyte.common.core;

import com.google.inject.Inject;
import com.mongodb.WriteConcern;
import dev.morphia.InsertOptions;
import dev.morphia.query.FieldEnd;
import dev.morphia.query.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;
import java.lang.reflect.ParameterizedType;

/**
 *
 * @author Andres Jonathan J.
 * @param <T>
 */
public abstract class DaoService<T extends Model> implements IDaoService<T> {;
    @Inject() protected DatabaseManager databaseManager;
    
    @Override
    public boolean save(T item) {
        try {
            if (get(item.getId()) != null) {
                return false;
            }
            
            databaseManager.getDatastore()
                .save(item, new InsertOptions().writeConcern(WriteConcern.JOURNALED));
        } catch (Exception exception) {
            Logger.getLogger(DaoService.class.getName())
                .log(Level.SEVERE, "Error inserting an element to database", exception);
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean update(T item) {
        try {
            if (get(item.getId()) == null) {
                return false;
            }
            
            InsertOptions options = new InsertOptions().writeConcern(WriteConcern.JOURNALED);
            databaseManager.getDatastore().save(item, options);
        } catch (Exception exception) {
            Logger.getLogger(DaoService.class.getName())
                .log(Level.SEVERE, "Error updating an element in database", exception);
            return false;
        }
        
        return true;
    }
    
    @Override
    public T get(ObjectId id) {
        return databaseManager.getDatastore()
            .find(getModelClass())
            .field("_id").equal(id)
            .first();
    }
    
    @Override
    public List<T> getAll() {
        return databaseManager.getDatastore()
            .createQuery(getModelClass())
            .find()
            .toList();
    }
    
    @Override
    public long count() {
        return databaseManager.getDatastore()
            .find(getModelClass())
            .count();
    }
    
    @Override
    public FieldEnd<? extends Query<T>> field(String field) {
        return databaseManager.getDatastore()
            .find(getModelClass())
            .field(field);
    }
    
    private Class<T> getModelClass() {
        return (Class<T>) ((ParameterizedType)getClass()
            .getGenericSuperclass())
            .getActualTypeArguments()[0];
    }
}
