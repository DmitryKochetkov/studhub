package com.studhub.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String mainpage(Model model) {
        return "index";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        return "courses";
    }

    @GetMapping("/users")
    public String users(Model model) {
        return "users";
    }

    @GetMapping("/students")
    public String students(Model model) {
        return "students";
    }

    @GetMapping("/account")
    public String account(Model model) {
        return "account";
    }
}
