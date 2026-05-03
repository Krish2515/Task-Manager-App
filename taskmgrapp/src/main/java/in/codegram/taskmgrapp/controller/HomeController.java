package in.codegram.taskmgrapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // First page (default)
    @GetMapping("/")
    public String roleSelection() {
        return "role-selection";
    }

}