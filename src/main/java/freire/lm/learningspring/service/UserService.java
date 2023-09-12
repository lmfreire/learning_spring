package freire.lm.learningspring.service;

import freire.lm.learningspring.dto.CreateDepositDto;
import freire.lm.learningspring.dto.UserDto;
import freire.lm.learningspring.model.User;

import java.util.List;

public interface UserService {

    User createUser(final UserDto userData);

    List<User> readUsers();
    User retrieveUser(final long id);

    User updateUser(final UserDto userData, final long id);

    void deleteUser(final long id);

    User createDeposit(final CreateDepositDto depositData, final long id);
}
