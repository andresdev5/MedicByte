package ec.edu.espe.medicbyte.common.core;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Andres Jonathan J.
 * @param <T>
 */
public interface IDaoService<T extends Model> {
    boolean save(T item);
    boolean update(T item);
    T get(ObjectId id);
    List<T> getAll();
    long count();
}
