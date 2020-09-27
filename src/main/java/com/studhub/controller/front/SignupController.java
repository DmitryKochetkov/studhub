package com.studhub.controller.front;

import com.studhub.entity.User;
import com.studhub.payload.SignupRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/users/signup")
public class SignupController {
    @GetMapping
    public String signup(Model model) {
        model.addAttribute("form", new SignupRequest());
        return "signup";
    }

    @PostMapping
    public RedirectView signupSubmit(
            Model model,
            @ModelAttribute SignupRequest form,
            RedirectAttributes redirectAttributes
    ) {
        SignupRequest dto = new SignupRequest();
        dto.setFirstName(form.getFirstName());
        dto.setLastName(form.getLastName());
        dto.setUsername(form.getUsername());
        dto.setPassword(form.getPassword());
        dto.setRole(form.getRole());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = "http://localhost:8081/api/users/signup";
        HttpEntity<SignupRequest> request = new HttpEntity<>(dto, headers);
        RedirectView redirectView = new RedirectView("/users/signup");
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
}
