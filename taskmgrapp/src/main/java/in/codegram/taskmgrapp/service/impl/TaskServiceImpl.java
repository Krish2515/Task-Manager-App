package in.codegram.taskmgrapp.service.impl;

import in.codegram.taskmgrapp.dto.TaskDto;
import in.codegram.taskmgrapp.entity.Task;
import in.codegram.taskmgrapp.entity.User;
import in.codegram.taskmgrapp.repository.TaskRepository;
import in.codegram.taskmgrapp.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{


    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map((Task task) -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createTask(TaskDto taskDto, User user) {

        Task task = new Task();

        // 🔥 manually set (avoid ModelMapper issue)
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setStartDate(taskDto.getStartDate());
        task.setEndDate(taskDto.getEndDate());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus());

        task.setUser(user); // 🔥 VERY IMPORTANT

        taskRepository.save(task);
    }

    @Override
    public TaskDto getTaskById(Long id) {

        Task task = taskRepository.findById(id).get();
        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        return taskDto;
    }

    @Override
    public void updateTask(TaskDto taskDto) {

        taskRepository.save(modelMapper.map(taskDto, Task.class));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

//    @Override
//    public long getTotalTasks() {
//        return taskRepository.count();
//    }
//
//    @Override
//    public long getCompletedTasks() {
//        return taskRepository.countByStatus("COMPLETED");
//    }
//
//    @Override
//    public long getPendingTasks() {
//        return taskRepository.countByStatus("PENDING")
//                + taskRepository.countByStatus("TODO");
//    }

    @Override
    public long getTotalTasksByUser(Long userId) {
        return taskRepository.countByUserId(userId);
    }

    @Override
    public long getCompletedTasksByUser(Long userId) {
        return taskRepository.countByUserIdAndStatus(userId, "COMPLETED");
    }

    @Override
    public long getPendingTasksByUser(Long userId) {
        return taskRepository.countByUserIdAndStatus(userId, "PENDING") +
                taskRepository.countByUserIdAndStatus(userId, "TODO");
    }

    @Override
    public List<TaskDto> getTasksByUser(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);

        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }
}
