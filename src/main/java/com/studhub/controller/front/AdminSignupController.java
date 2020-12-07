package com.studhub.controller.front;

import com.studhub.entity.User;
import com.studhub.payload.SignupRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Контроллер страницы регистрации пользователя.
 */
@Controller
@RequestMapping("/admin/signup")
public class AdminSignupController {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @GetMapping
    public String signupPage(Model model) {
        model.addAttribute("form", new SignupRequest());
        return "signup";
    }

    @PostMapping
    public RedirectView signupSubmit(
            Model model,
            @ModelAttribute SignupRequest signupRequest,
            RedirectAttributes redirectAttributes
    ) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = "http://" + HOST + ":" + PORT + "/api/admin/signup";
        HttpEntity<SignupRequest> httpEntity = new HttpEntity<>(signupRequest, headers);
        RedirectView redirectView = new RedirectView("/users/signup");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        try {
            restTemplate.postForEntity(uri, httpEntity, User.class); //todo: User -> UserDto
        }
        catch (HttpClientErrorException.Conflict e) {
            model.addAttribute("loginError", true);
            redirectAttributes.addFlashAttribute("loginError", true);
        }

        return redirectView;
    }
}
