package com.studhub.controller.front;

import com.studhub.dto.RegisterRequestDto;
import com.studhub.dto.UserDto;
import com.studhub.entity.User;
import com.studhub.entity.UserStatus;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.Charset;
import java.util.List;

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
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8081/api/users";
        ResponseEntity<List<UserDto>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<UserDto>>(){});
        model.addAttribute("users", response.getBody());
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

    @GetMapping("/users/register")
    public String register(Model model) {
        model.addAttribute("form", new RegisterRequestDto());
        return "register";
    }

    @PostMapping("/users/register")
    public String registerSubmit(
            Model model,
            @ModelAttribute RegisterRequestDto form
    ) {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setFirstName(form.getFirstName());
        dto.setLastName(form.getLastName());
        dto.setUsername(form.getUsername());
        dto.setPassword(form.getPassword());
        dto.setRole(form.getRole());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = "http://localhost:8081/api/users/register";
        HttpEntity<RegisterRequestDto> request = new HttpEntity<>(dto, headers);
//        try {
//            ResponseEntity<User> response = restTemplate.postForEntity(uri, request, User.class);
//        }
//        catch (HttpClientErrorException.Conflict e) {
//            model.addAttribute("loginError",true);
//        }
        ResponseEntity<User> response = restTemplate.postForEntity(uri, request, User.class);
        return "register";
    }

    @GetMapping("/err500")
    public String testErr() {
        throw new NullPointerException();
    }

    @GetMapping("/err409")
    public String test409() {
        RegisterRequestDto dto = new RegisterRequestDto();
        HttpHeaders headers = new HttpHeaders();
        byte[] b = new byte[6];
        throw new HttpClientErrorException(HttpStatus.CONFLICT, "msg", new HttpHeaders(), b, Charset.defaultCharset());
    }

    @SneakyThrows
    @GetMapping("/err405")
    public String test405() {
        throw new HttpRequestMethodNotSupportedException("sdfs", "sdfs");
    }
}
