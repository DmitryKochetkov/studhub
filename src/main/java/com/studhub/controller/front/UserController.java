package com.studhub.controller.front;

import com.studhub.dto.CourseDto;
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

    @GetMapping("/student/{student_id}/course/{course_id}")
    public String course(@PathVariable Long student_id, @PathVariable Long course_id, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://" + HOST + ":" + PORT + "/api/student/" + student_id + "/course/" + course_id.toString();
        try {
            ResponseEntity<CourseDto> course = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<CourseDto>() {});
            model.addAttribute("course", course.getBody());

            ResponseEntity<UserDto> student = restTemplate.exchange("http://" + HOST + ":" + PORT + "/api/user/" + student_id, HttpMethod.GET, null,
                    new ParameterizedTypeReference<UserDto>() {});
            model.addAttribute("student", student.getBody());

        }
        catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        }
//        catch (HttpClientErrorException.Unauthorized e) {
//            throw new UnauthorizedException();
//        }


        return "course";
    }
}
