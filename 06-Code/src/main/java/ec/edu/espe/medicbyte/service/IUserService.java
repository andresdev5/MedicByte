package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Role;
import java.util.function.Function;

import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.model.UserProfile;
import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    int getTotalUsers();
    User getUser(int id);
    User getUser(String username);
    boolean userExists(int id);
    boolean userExists(String username);
    User findUser(Function<User, Boolean> comparator);
    List<User> findUsers(Function<User, Boolean> comparator);
    User createUser(String username, String email, String password, Role role);
    UserProfile getUserProfile(int userId);
    List<UserProfile> getUserProfiles();
}