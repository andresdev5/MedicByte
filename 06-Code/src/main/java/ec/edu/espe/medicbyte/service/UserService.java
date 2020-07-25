package ec.edu.espe.medicbyte.service;

import ec.edu.espe.medicbyte.model.Role;
import java.util.function.Function;

import ec.edu.espe.medicbyte.model.User;
import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public int getTotalUsers();
    public User getUser(int id);
    public User getUser(String username);
    public boolean userExists(int id);
    public boolean userExists(String username);
    public User findUser(Function<User, Boolean> comparator);
    public List<User> findUsers(Function<User, Boolean> comparator);
    public boolean createUser(String username, String password, Role role);
}