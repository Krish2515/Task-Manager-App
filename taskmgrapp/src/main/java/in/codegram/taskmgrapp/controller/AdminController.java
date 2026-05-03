package in.codegram.taskmgrapp.controller;

import in.codegram.taskmgrapp.entity.Admin;
import in.codegram.taskmgrapp.repository.AdminRepository;
import in.codegram.taskmgrapp.repository.TaskRepository;
import in.codegram.taskmgrapp.repository.UserRepository;
import in.codegram.taskmgrapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    // ✅ Inject BOTH dependencies here
    public AdminController(AdminRepository adminRepository, UserService userService , UserRepository userRepository,
                           TaskRepository taskRepository) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // ✅ Admin Dashboard
    @GetMapping("/adminIndex")
    public String showAdminIndexPage() {
        return "adminIndex";
    }

    // ✅ Login Page
    @GetMapping("/adminLogin")
    public String showAdminLoginPage(Model model) {
        model.addAttribute("user", new Admin());
        return "admin/adminLogin";
    }

    // ✅ Register Page
    @GetMapping("/adminRegister")
    public String showAdminRegisterPage(Model model) {
        model.addAttribute("user", new Admin());
        return "admin/adminRegister";
    }

    // ✅ Register
    @PostMapping("/admin/register/save")
    public String registerAdmin(@ModelAttribute("user") Admin admin) {
        adminRepository.save(admin);
        return "redirect:/adminRegister?success";
    }

    // ✅ Login
    @PostMapping("/adminLogin")
    public String loginAdmin(
            @ModelAttribute("user") Admin admin,
            @RequestParam("appCode") String appCode) {

        String DEFAULT_CODE = "ADMIN123";

        if (!appCode.equals(DEFAULT_CODE)) {
            return "redirect:/adminLogin?error";
        }

        Admin validAdmin = adminRepository
                .findByEmailAndPassword(admin.getEmail(), admin.getPassword());

        if (validAdmin != null) {
            return "redirect:/admin/adminDashboard";
        }

        return "redirect:/adminLogin?error";
    }

    // ✅ Dashboard
    @GetMapping("/admin/adminDashboard")
    public String showAdminDashboard(Model model) {

        long totalUsers = userRepository.count();
        long totalTasks = taskRepository.count();

        long completedTasks = taskRepository.countByStatus("COMPLETED");

        // Merge TODO + PENDING
        long pendingTasks =
                taskRepository.countByStatus("PENDING") +
                        taskRepository.countByStatus("TODO");

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalTasks", totalTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("pendingTasks", pendingTasks);

        return "admin/adminDashboard";
    }

    // ✅ Manage Users
    @GetMapping("/admin/users")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "admin/adminUsers";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/tasks")
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "admin/adminTasks"; // IMPORTANT
    }

    @GetMapping("/admin/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/admin/tasks";
    }
}