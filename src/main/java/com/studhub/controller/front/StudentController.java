package com.studhub.controller.front;

import com.studhub.dto.*;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class StudentController {
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

    @GetMapping("/student/{student_id}/course/{course_id}/homework")
    public String homeworkInCourse(@PathVariable Long student_id, @PathVariable Long course_id, Model model, @RequestParam(defaultValue = "1") String page) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://" + HOST + ":" + PORT + "/api/student/" + student_id + "/course/" + course_id.toString() + "/homework";
        try {
            ResponseEntity<PageDto<HomeworkDto>> homeworkPage = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<PageDto<HomeworkDto>>() {});
            model.addAttribute("homeworkList", homeworkPage.getBody().getContent());

            ResponseEntity<UserDto> student = restTemplate.exchange("http://" + HOST + ":" + PORT + "/api/user/" + student_id, HttpMethod.GET, null,
                    new ParameterizedTypeReference<UserDto>() {});
            model.addAttribute("student", student.getBody());
            model.addAttribute("course_id", course_id);

        }
        catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        }
        return "course-homework";
    }

    @GetMapping("/student/{student_id}/course/{course_id}/homework/{homework_id}")
    public RedirectView homeworkRedirectToDescription(
        @PathVariable Long student_id,
        @PathVariable Long course_id,
        @PathVariable Long homework_id
    ) {
        String url = "/student/" + student_id +
        "/course/" + course_id +
                "/homework/" + homework_id + "/description";
        RedirectView redirectView = new RedirectView(url);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        return redirectView;
    }

    @GetMapping("/student/{student_id}/course/{course_id}/homework/{homework_id}/description")
    public String homeworkById_description(
            @PathVariable Long student_id,
            @PathVariable Long course_id,
            @PathVariable Long homework_id,
            Model model,
            @RequestParam(defaultValue = "1") String page) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://" + HOST + ":" + PORT + "/api/student/" + student_id + "/course/" + course_id.toString() + "/homework/" + homework_id;
        try {
            ResponseEntity<HomeworkDto> homework = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<HomeworkDto>() {});
            model.addAttribute("homework", homework.getBody());
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        }
        return "homework-description";
    }

    @GetMapping("/student/{student_id}/course/{course_id}/homework/{homework_id}/problems/{problem_number}")
    public String homeworkById_problems(
            @PathVariable Long student_id,
            @PathVariable Long course_id,
            @PathVariable Long homework_id,
            @PathVariable Long problem_number,
            Model model,
            @RequestParam(defaultValue = "1") String page) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://" + HOST + ":" + PORT +
                "/api/student/" + student_id +
                "/course/" + course_id.toString() +
                "/homework/" + homework_id +
                "/problems/" + problem_number;
        try {
            ResponseEntity<HomeworkProblemDto> problem = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<HomeworkProblemDto>() {});
            model.addAttribute("problemInfo", problem.getBody());

            ResponseEntity<HomeworkDto> homework = restTemplate.exchange("http://" + HOST + ":" + PORT +
                            "/api/student/" + student_id +
                            "/course/" + course_id.toString() +
                            "/homework/" + homework_id, HttpMethod.GET, null,
                    new ParameterizedTypeReference<HomeworkDto>() {});
            model.addAttribute("homework", homework.getBody());
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        }
        catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException();
        }
        return "homework-problems";
    }

    @GetMapping(value = "/student/{student_id}/course/{course_id}/homework/{homework_id}/problems")
    public RedirectView getProblemsRedirect(
            @PathVariable Long student_id,
            @PathVariable Long course_id,
            @PathVariable Long homework_id) {
        String url = "/student/" + student_id +
                "/course/" + course_id +
                "/homework/" + homework_id + "/problems/1";
        RedirectView redirectView = new RedirectView(url);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        return redirectView;
    }

    @GetMapping(value = "/student/{student_id}/course/{course_id}/homework/{homework_id}/submissions")
    public String getSubmissionsInHomework(
            @PathVariable Long student_id,
            @PathVariable Long course_id,
            @PathVariable Long homework_id,
            Model model) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<HomeworkDto> homework = restTemplate.exchange("http://" + HOST + ":" + PORT +
                            "/api/student/" + student_id +
                            "/course/" + course_id.toString() +
                            "/homework/" + homework_id, HttpMethod.GET, null,
                    new ParameterizedTypeReference<HomeworkDto>() {
                    });
            model.addAttribute("homework", homework.getBody());

            ResponseEntity<PageDto<SubmissionDto>> submissionsPage = restTemplate.exchange("http://" + HOST + ":" + PORT +
                            "/api/student/" + student_id +
                            "/course/" + course_id.toString() +
                            "/homework/" + homework_id + "/submissions", HttpMethod.GET, null,
                    new ParameterizedTypeReference<PageDto<SubmissionDto>>() {
                    });
            model.addAttribute("submissionsPage", submissionsPage.getBody());
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        }
        catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException();
        }
        return "homework-submissions";
    }

}
