package ec.edu.espe.medicbyte.service.impl;

import java.util.function.Function;

import ec.edu.espe.medicbyte.model.Role;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.UserService;
import ec.edu.espe.tinyio.CsvRecord;
import ec.edu.espe.tinyio.FileLine;
import ec.edu.espe.tinyio.FileManager;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserServiceImpl implements UserService {
    private final FileManager fileManager;

    public UserServiceImpl() {
        fileManager = new FileManager("users.csv", true);
    }

    @Override
    public User getUser(int id) {
        FileLine line = fileManager.findFirst((l) -> {
            return Integer.parseInt(l.csv().getColumnValue(0)) == id;
        });

        if (line == null) {
            return null;
        }

        return csvRecordToUser(line.csv());
    }

    @Override
    public User findUser(Function<User, Boolean> comparator) {
        FileLine foundLine = fileManager.findFirst(line -> {
            return comparator.apply(csvRecordToUser(line.csv()));
        });

        if (foundLine == null) {
            return null;
        }

        return csvRecordToUser(foundLine.csv());
    }

    private User csvRecordToUser(CsvRecord record) {
        User user = new User();
        user.setId(Integer.parseInt(record.getColumnValue(0)));
        user.setUsername(record.getColumnValue(1));
        user.setPassword(record.getColumnValue(2));
        user.setRole(new Role(record.getColumnValue(3)));

        return user;
    }
}