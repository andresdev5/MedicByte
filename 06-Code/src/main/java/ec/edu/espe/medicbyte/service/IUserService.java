package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.common.core.IDaoService;

import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.model.UserProfile;

public interface IUserService extends IDaoService<User> {
    User get(String username);
    UserProfile createProfile();
    boolean exists(String username);
}