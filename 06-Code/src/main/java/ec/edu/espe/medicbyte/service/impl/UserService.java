package ec.edu.espe.medicbyte.service.impl;

import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.common.core.DaoService;
import ec.edu.espe.medicbyte.model.UserProfile;
import ec.edu.espe.medicbyte.service.IUserService;
import org.bson.types.ObjectId;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserService extends DaoService<User>  implements IUserService {
    @Override
    public User get(String username) {
        return field("username").equal(username).first();
    }
    
    @Override
    public UserProfile createProfile() {
        ObjectId id = (ObjectId) databaseManager.getDatastore().save(new UserProfile()).getId();
        return databaseManager.getDatastore().find(UserProfile.class).field("_id").equal(id).first();
    }
    
    @Override 
    public boolean exists(String username) {
        return get(username) != null;
    }
}