package in.codegram.taskmgrapp.repository;

import in.codegram.taskmgrapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


//    Optional<User> findByUsername(String username);
//
//    Boolean existsByEmail(String email);
//
//    Optional<User> findByUsernameOrEmail(String username, String email);

    User findByEmail(String email);
}
