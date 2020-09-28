package com.studhub.controller.front;

import com.studhub.controller.service.UserService;
import com.studhub.dto.UserDto;
import com.studhub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class ProfileController {
    @Autowired
    UserService userService;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", new UserDto(user));
        return "profile";
    }
}
