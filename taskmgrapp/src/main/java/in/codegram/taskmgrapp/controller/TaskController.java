package in.codegram.taskmgrapp.controller;

import in.codegram.taskmgrapp.dto.TaskDto;
import in.codegram.taskmgrapp.entity.User;
import in.codegram.taskmgrapp.service.TaskService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    // ✅ BOTH ADMIN + USER can view tasks
   // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    @GetMapping("tasks")
//    public String getAllTasks(Model model) {
//        List<TaskDto> tasks = taskService.getAllTasks();
//        model.addAttribute("tasks", tasks);
//        return "tasks/taskList";
//    }

    @GetMapping("tasks")
    public String getAllTasks(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        List<TaskDto> tasks = taskService.getTasksByUser(user.getId()); // ✅ FIX

        model.addAttribute("tasks", tasks);

        return "tasks/taskList";
    }

    // ✅ ONLY ADMIN can save (create task)
    //@PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/tasks")
//    public String saveTask(@ModelAttribute("task") TaskDto taskDto) {
//        taskService.createTask(taskDto);
//        return "redirect:/tasks?success";
//    }

    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute("task") TaskDto taskDto,
                           HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        taskService.createTask(taskDto, user); // ✅ pass user

        return "redirect:/tasks?success";
    }

    // ✅ ONLY ADMIN can access create form
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("createTask")
    public String createTaskForm(Model model) {
        model.addAttribute("task", new TaskDto());
        return "tasks/createTask";
    }

    // ✅ ONLY ADMIN can delete
   // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    // ✅ ONLY ADMIN can open edit page
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        TaskDto taskDto = taskService.getTaskById(id);
        model.addAttribute("task", taskDto);
        return "tasks/editTask";
    }

    // ✅ ADMIN + USER can update (you can restrict if needed)
   // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/tasks/{id}")
    public String updateTask(@PathVariable("id") Long id,
                             @ModelAttribute("task") TaskDto taskDto) {
        taskDto.setId(id);
        taskService.updateTask(taskDto);
        return "redirect:/tasks?updated";
    }
}