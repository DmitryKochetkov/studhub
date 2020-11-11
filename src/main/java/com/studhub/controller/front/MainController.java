package com.studhub.controller.front;

import com.studhub.dto.PageDto;
import com.studhub.dto.UserDto;
import com.studhub.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

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

    @GetMapping("/courses")
    public String courses(Model model) {
        return "about_courses";
    }

    @GetMapping("/users")
    public String users(Model model, @RequestParam(defaultValue = "1") Integer page) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://" + HOST + ":" + PORT + "/api/users?page=" + page;
        ResponseEntity<PageDto<UserDto>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageDto<UserDto>>() {
                });
        model.addAttribute("users", response.getBody().getContent());
        return "users";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }
}
