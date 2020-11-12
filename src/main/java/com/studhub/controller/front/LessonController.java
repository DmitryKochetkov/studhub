package com.studhub.controller.front;

import com.studhub.dto.LessonDto;
import com.studhub.payload.CreateLessonRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/lessons")
public class LessonController {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @GetMapping
    public String get(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://" + HOST + ":" + PORT + "/api/admin/lessons/";
        ResponseEntity<List<LessonDto>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<LessonDto>>() {});
        List<LessonDto> lessons = response.getBody();
        model.addAttribute("lessons", lessons);
        return "admin_lessons";
    }

    @GetMapping("/new")
    public String createPage(Model model) {
        CreateLessonRequest form = new CreateLessonRequest();
        model.addAttribute("form", form);
        return "create_lesson";
    }

    @PostMapping("/new")
    public RedirectView createLesson(Model model, CreateLessonRequest request, RedirectAttributes redirectAttributes) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8081/api/admin/lessons/new";

        RedirectView redirectView = new RedirectView("/lessons");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        try {
            ResponseEntity<LessonDto> response = restTemplate.postForEntity(uri, request, LessonDto.class);
        }
        catch (HttpClientErrorException e) {
            return redirectView;
        }

        return redirectView;
    }
}
