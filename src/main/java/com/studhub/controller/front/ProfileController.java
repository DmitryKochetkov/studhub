package com.studhub.controller.front;

import com.studhub.dto.UserDto;
import com.studhub.exception.ResourceNotFoundException;
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

@Controller

public class ProfileController {
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
            return "profile";
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException();
        }
    }
}
