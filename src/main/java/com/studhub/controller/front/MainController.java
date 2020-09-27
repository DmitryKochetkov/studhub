package com.studhub.controller.front;

import com.studhub.payload.RegisterRequest;
import com.studhub.dto.UserDto;
import com.studhub.entity.User;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @GetMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }

    @GetMapping("/users/register")
    public String register(Model model) {
        model.addAttribute("form", new RegisterRequest());
        return "register";
    }

    @PostMapping("/users/register")
    public RedirectView registerSubmit(
            Model model,
            @ModelAttribute RegisterRequest form,
            RedirectAttributes redirectAttributes
    ) {
        RegisterRequest dto = new RegisterRequest();
        dto.setFirstName(form.getFirstName());
        dto.setLastName(form.getLastName());
        dto.setUsername(form.getUsername());
        dto.setPassword(form.getPassword());
        dto.setRole(form.getRole());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = "http://localhost:8081/api/users/register";
        HttpEntity<RegisterRequest> request = new HttpEntity<>(dto, headers);
        RedirectView redirectView = new RedirectView("/users/register");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        redirectAttributes.addFlashAttribute("loginError", true);
        try {
            ResponseEntity<User> response = restTemplate.postForEntity(uri, request, User.class);
        }
        catch (HttpClientErrorException.Conflict e) {
            model.addAttribute("loginError", true);
        }

        return redirectView;
    }

    @GetMapping("/err500")
    public String testErr() {
        throw new NullPointerException();
    }

    @GetMapping("/err409")
    public String test409() {
        RegisterRequest dto = new RegisterRequest();
        HttpHeaders headers = new HttpHeaders();
        byte[] b = new byte[6];
        throw new HttpClientErrorException(HttpStatus.CONFLICT, "msg", new HttpHeaders(), b, Charset.defaultCharset());
    }

    @SneakyThrows
    @GetMapping("/err405")
    public String test405() {
        throw new HttpRequestMethodNotSupportedException("sdfs", "sdfs");
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }
}
