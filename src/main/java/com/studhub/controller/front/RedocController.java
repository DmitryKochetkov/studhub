package com.studhub.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/docs")
public class RedocController {
    @GetMapping
    public String redoc() {
        return "redoc";
    }
}
