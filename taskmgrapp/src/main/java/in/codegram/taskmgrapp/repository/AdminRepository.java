package in.codegram.taskmgrapp.repository;

import in.codegram.taskmgrapp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);

    Admin findByEmailAndPassword(String email, String password);
}