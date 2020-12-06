package com.studhub.controller.front;

import com.studhub.dto.VerdictDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class VerdictsInfoController {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @GetMapping("/verdicts")
    public String verdicts(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<VerdictDto>> response = restTemplate.exchange("http://" + HOST + ":" + PORT +
                        "/api/verdicts/", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<VerdictDto>>() {
                });

        model.addAttribute("verdicts", response.getBody());
        return "verdicts";
    }
}
