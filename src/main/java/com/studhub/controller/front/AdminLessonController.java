package com.studhub.controller.front;

import com.studhub.dto.LessonDto;
import com.studhub.dto.PageDto;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.ResourceNotFoundException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Контроллер страницы администрирования уроков.
 */
@Controller
@RequestMapping("/admin/lessons")
public class AdminLessonController {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @GetMapping
    public String get(Model model, @RequestParam(defaultValue = "1") Integer page) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String uri = "http://" + HOST + ":" + PORT + "/api/admin/lessons?page=" + page;
            ResponseEntity<PageDto<LessonDto>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<PageDto<LessonDto>>() {
                    });
            model.addAttribute("page", response.getBody());
            return "admin_lessons";
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException();
        }
        catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException();
        }
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
        String uri = "http://" + HOST + ":" + PORT + "/api/admin/lessons/new";

        RedirectView redirectView = new RedirectView("/admin/lessons");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        try {
            restTemplate.postForEntity(uri, request, LessonDto.class);
        }
        catch (HttpClientErrorException e) {
            return redirectView;
        }

        return redirectView;
    }
}
