package in.codegram.taskmgrapp.controller;

import in.codegram.taskmgrapp.entity.User;
import in.codegram.taskmgrapp.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private TaskService taskService;

    public DashboardController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        model.addAttribute("totalTasks",
                taskService.getTotalTasksByUser(user.getId()));

        model.addAttribute("completedTasks",
                taskService.getCompletedTasksByUser(user.getId()));

        model.addAttribute("pendingTasks",
                taskService.getPendingTasksByUser(user.getId()));

        return "dashboard";
    }
}