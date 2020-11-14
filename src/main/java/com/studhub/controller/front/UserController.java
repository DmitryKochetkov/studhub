package com.studhub.controller.front;

import com.studhub.dto.CourseDto;
import com.studhub.dto.PageDto;
import com.studhub.dto.UserDto;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @GetMapping(value = "/users", produces = "text/html")
    @ResponseBody
    public ModelAndView users(@RequestParam(defaultValue = "1") Integer page) {
        ModelAndView result = new ModelAndView("users");
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://" + HOST + ":" + PORT + "/api/users?page=" + page;
        try {
            ResponseEntity<PageDto<UserDto>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<PageDto<UserDto>>() {
                    });
            result.addObject("page", response.getBody());
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException();
        }
        catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException();
        }
        return result;
    }

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

    @GetMapping("/user/{user_id}/course/{course_id}")
    public String course(@PathVariable Long user_id, @PathVariable Long course_id, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://" + HOST + ":" + PORT + "/api/user/" + user_id + "/course/" + course_id.toString();
        try {
            ResponseEntity<CourseDto> course = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<CourseDto>() {});
            CourseDto courseDto = course.getBody();
            model.addAttribute("course", course.getBody());

            ResponseEntity<UserDto> student = restTemplate.exchange("http://" + HOST + ":" + PORT + "/api/user/" + user_id, HttpMethod.GET, null,
                    new ParameterizedTypeReference<UserDto>() {});
            model.addAttribute("student", student.getBody());

        }
        catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException();
        }
//        catch (HttpClientErrorException.Unauthorized e) {
//            throw new UnauthorizedException();
//        }


        return "course";
    }
}
