package com.studhub.controller.front;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @GetMapping
    public String mainpage(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }
}
