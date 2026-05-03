package in.codegram.taskmgrapp.repository;

import in.codegram.taskmgrapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    long countByStatus(String status);

    List<Task> findByUserId(Long userId);

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, String status);


}
