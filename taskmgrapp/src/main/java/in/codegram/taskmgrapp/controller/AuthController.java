package in.codegram.taskmgrapp.controller;

import in.codegram.taskmgrapp.dto.UserDto;
import in.codegram.taskmgrapp.entity.User;
import in.codegram.taskmgrapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "users/register";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users/login";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model){

        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email",null,"There is already an account registered with the same email");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "users/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users/user_list";
    }

//    @PostMapping("/authenticate")
//    public String login(@ModelAttribute("user") User user, Model model) {
//
//        User validUser = userService.findUserByEmail(user.getEmail());
//
//        if(validUser != null &&
//                validUser.getPassword().equals(user.getPassword())) {
//
//            return "redirect:/dashboard"; // ✅ success
//        } else {
//            model.addAttribute("error", "Invalid Email or Password");
//            return "users/login"; // ❌ fail
//        }
//    }

    @PostMapping("/authenticate")
    public String login(@ModelAttribute("user") User user,
                        Model model,
                        HttpSession session) {

        User validUser = userService.findUserByEmail(user.getEmail());

        if(validUser != null &&
                validUser.getPassword().equals(user.getPassword())) {

            session.setAttribute("loggedInUser", validUser); // 🔥 MUST
            return "redirect:/dashboard";

        } else {
            model.addAttribute("error", "Invalid Email or Password");
            return "users/login";
        }
    }
}
