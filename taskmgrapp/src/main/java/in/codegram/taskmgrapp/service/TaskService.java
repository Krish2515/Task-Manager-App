package in.codegram.taskmgrapp.service;

import in.codegram.taskmgrapp.dto.TaskDto;
import in.codegram.taskmgrapp.entity.User;

import java.util.List;

public interface TaskService {

    List<TaskDto> getAllTasks();

    void createTask(TaskDto taskDto, User user);

    TaskDto getTaskById(Long id);

    void updateTask(TaskDto taskDto);

    void deleteTask(Long id);

//    long getTotalTasks();
//    long getCompletedTasks();
//    long getPendingTasks();

    long getTotalTasksByUser(Long userId);
    long getCompletedTasksByUser(Long userId);
    long getPendingTasksByUser(Long userId);

    List<TaskDto> getTasksByUser(Long userId);

}
