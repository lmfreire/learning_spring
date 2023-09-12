package freire.lm.learningspring.repository;

import freire.lm.learningspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByCpf(final String cpf);
    boolean existsUserByEmail(final String email);
}
