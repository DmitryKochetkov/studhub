package com.studhub.controller.front;

import com.studhub.dto.*;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.NotAcceptableException;
import com.studhub.exception.NotFoundException;
import com.studhub.payload.SubmissionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String getProblemInHomework(
            @PathVariable Long student_id,
            @PathVariable Long course_id,
            @PathVariable Long homework_id,
            @PathVariable Long problem_number,
            Model model) {
        model.addAttribute("submissionRequest", new SubmissionRequest());
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

    @PostMapping("/student/{student_id}/course/{course_id}/homework/{homework_id}/problems/{problem_number}/submit")
    public RedirectView submitProblemInHomework(
            @PathVariable Long student_id,
            @PathVariable Long course_id,
            @PathVariable Long homework_id,
            @PathVariable Long problem_number,
            @ModelAttribute SubmissionRequest submissionRequest) {
        RedirectView redirectView = new RedirectView("/student/{student_id}/course/{course_id}/homework/{homework_id}/problems/{problem_number}");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubmissionRequest> httpEntity = new HttpEntity<>(submissionRequest, headers);

        String url = "http://" + HOST + ":" + PORT +
                "/api/student/" + student_id +
                "/course/" + course_id.toString() +
                "/homework/" + homework_id +
                "/problems/" + problem_number + "/submit";
        try {
            restTemplate.postForEntity(url, httpEntity, SubmissionDto.class);
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        }
        catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException();
        }
        catch (HttpClientErrorException.NotAcceptable e) {
            throw new NotAcceptableException();
        }
        return redirectView;
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
            Model model,
            @RequestParam(defaultValue = "1") Integer page) {
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
                            "/homework/" + homework_id + "/submissions" + "?page=" + page, HttpMethod.GET, null,
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
