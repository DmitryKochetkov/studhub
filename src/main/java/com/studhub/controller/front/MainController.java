package com.studhub.controller.front;

import com.studhub.payload.SignupRequest;
import com.studhub.dto.UserDto;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
        return "about_courses";
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

    @GetMapping("/err500")
    public String testErr() {
        throw new NullPointerException();
    }

    @GetMapping("/err409")
    public String test409() {
        SignupRequest dto = new SignupRequest();
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
