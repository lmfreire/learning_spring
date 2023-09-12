package freire.lm.learningspring.service.impl;

import freire.lm.learningspring.dto.CreateDepositDto;
import freire.lm.learningspring.dto.UserDto;
import freire.lm.learningspring.exception.AppException;
import freire.lm.learningspring.model.User;
import freire.lm.learningspring.repository.UserRepository;
import freire.lm.learningspring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void checkEmailAndCpf(final UserDto userData){
        if(userRepository.existsUserByCpf(userData.getCpf())){
            throw  new AppException("CPF already in use", HttpStatus.CONFLICT);
        }

        if(userRepository.existsUserByEmail(userData.getEmail())){
            throw  new AppException("Email already in use", HttpStatus.CONFLICT);
        }
    }


    public User createUser(final UserDto userData){

        checkEmailAndCpf(userData);

        final User user = new User(
                userData.getName(),
                userData.getCpf(),
                userData.getEmail(),
                userData.getPassword(),
                userData.getType()
        );

        return  userRepository.save(user);
    }

    public List<User> readUsers(){
        return userRepository.findAll();
    }

    public User retrieveUser(final long id) {

        return userRepository.findById(id).orElseThrow(() -> new AppException("User Not Found", HttpStatus.NOT_FOUND));
    }

    public User updateUser(final UserDto userData, final long id){

        checkEmailAndCpf(userData);

        final User user = userRepository.findById(id).orElseThrow(() -> new AppException("User Not Found", HttpStatus.NOT_FOUND));

        user.setName(userData.getName());
        user.setCpf(userData.getCpf());
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        user.setType(userData.getType());

        return userRepository.save(user);
    }

    public void deleteUser(final long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new AppException("User Not Found", HttpStatus.NOT_FOUND));

        userRepository.delete(user);
    }

    public User createDeposit(final CreateDepositDto depositData, final long id){
        final User user = userRepository.findById(id).orElseThrow(() -> new AppException("User Not Found", HttpStatus.NOT_FOUND));

        final float currentBalance = user.getBalance();

        user.setBalance(currentBalance + depositData.getValue());

        return userRepository.save(user);
    }
}
