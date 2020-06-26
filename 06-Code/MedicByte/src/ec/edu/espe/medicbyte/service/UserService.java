package ec.edu.espe.medicbyte.service;

import java.util.function.Function;

import ec.edu.espe.medicbyte.model.User;

public interface UserService {
    public User getUser(int id);
    public User findUser(Function<User, Boolean> comparator);
}