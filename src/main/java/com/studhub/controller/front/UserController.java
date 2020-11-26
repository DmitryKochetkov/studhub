package com.studhub.controller.front;

import com.studhub.dto.PageDto;
import com.studhub.dto.UserDto;
import com.studhub.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Контроллер страниц с информацией о пользователях.
 */
@Controller
public class UserController {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @GetMapping("/user/{id}")
    public String profile(@PathVariable Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<UserDto> response = restTemplate.exchange("http://" + HOST + ":" + PORT + "/api/user/" + id.toString(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<UserDto>() {
                    });
            model.addAttribute("user", response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        }

        try {
            ResponseEntity<PageDto<UserDto>> response = restTemplate.exchange("http://" + HOST + ":" + PORT + "/api/user/" + id.toString() + "/followers", HttpMethod.GET, null,
                    new ParameterizedTypeReference<PageDto<UserDto>>() {
                    });
            model.addAttribute("following", response.getBody().getContent());
            return "profile";
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        }
    }
}
