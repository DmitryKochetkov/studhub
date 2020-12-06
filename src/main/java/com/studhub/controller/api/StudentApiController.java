package com.studhub.controller.api;

import com.studhub.dto.*;
import com.studhub.entity.Course;
import com.studhub.entity.Student;
import com.studhub.entity.User;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.NotFoundException;
import com.studhub.service.CourseService;
import com.studhub.service.HomeworkService;
import com.studhub.service.SubmissionService;
import com.studhub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *   Контроллер пользователей.
 * */
@RestController
@RequestMapping("/api")
@Api(tags = "Student", description = "Access student info.")
public class StudentApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private SubmissionService submissionService;

    @GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get student by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found")
        }
    )
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (!(user instanceof Student))
            throw new NotFoundException();

        return ResponseEntity.ok(new StudentDto((Student)user));
    }

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get student by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found")
    }
    )
    public ResponseEntity<StudentDto> getStudentByUsername(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if (!(user instanceof Student))
            throw new NotFoundException();

        return ResponseEntity.ok(new StudentDto((Student)user));
    }

    @GetMapping(value = "/student/{user_id}/course/{course_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get course by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long user_id, @PathVariable Long course_id) {
        User user = userService.getById(user_id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Course course = courseService.getById(course_id).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (course.getStudent().getId() != user_id)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new CourseDto(course));
    }

    @GetMapping(value = "/student/{user_id}/course/{course_id}/homework", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all homework in course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<PageDto<HomeworkDto>> getAllHomeworkInCourse(
            @PathVariable Long user_id,
            @PathVariable Long course_id,
            @RequestParam(defaultValue = "1") Integer page) {
        User user = userService.getById(user_id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Course course = courseService.getById(course_id).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (course.getStudent().getId() != user_id)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Page<HomeworkDto> result = homeworkService.getAllHomeworkInCourse(course, page).map(HomeworkDto::new);
        return ResponseEntity.ok(new PageDto<HomeworkDto>(result));
    }

    @GetMapping(value = "/student/{user_id}/course/{course_id}/homework/{homework_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get homework by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<HomeworkDto> getHomeworkById(
            @PathVariable Long user_id,
            @PathVariable Long course_id,
            @PathVariable Long homework_id,
            @RequestParam(defaultValue = "1") Integer page) {
        User user = userService.getById(user_id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Course course = courseService.getById(course_id).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (course.getStudent().getId() != user_id)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        HomeworkDto result = new HomeworkDto(homeworkService.getById(homework_id).orElseThrow(NotFoundException::new));
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/student/{user_id}/course/{course_id}/homework/{homework_id}/problems/{problem_number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get problem in homework")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<HomeworkProblemDto> getProblemFromHomework(
            @PathVariable Long user_id,
            @PathVariable Long course_id,
            @PathVariable Long homework_id,
            @PathVariable Integer problem_number) {
        User user = userService.getById(user_id);
        if (user == null)
            throw new NotFoundException();

        Course course = courseService.getById(course_id).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            throw new NotFoundException();

        if (!course.getStudent().getId().equals(user_id))
            throw new NotFoundException();

        if (problem_number < 1)
            throw new BadRequestException();

        HomeworkProblemDto result = new HomeworkProblemDto(homeworkService.getProblemInHomework(homework_id, problem_number).orElseThrow(NotFoundException::new));
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/student/{user_id}/course/{course_id}/homework/{homework_id}/submissions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get problem in homework")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<PageDto<SubmissionDto>> getSubmissionsFromHomework(
            @PathVariable Long user_id,
            @PathVariable Long course_id,
            @PathVariable Long homework_id,
            @RequestParam(defaultValue = "1") Integer page) {
        User user = userService.getById(user_id);
        if (user == null)
            throw new NotFoundException();

        Course course = courseService.getById(course_id).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            throw new NotFoundException();

        if (!course.getStudent().getId().equals(user_id))
            throw new NotFoundException();

        Page<SubmissionDto> result = submissionService.getByHomeworkId(homework_id, page).map(SubmissionDto::new);
        return ResponseEntity.ok(new PageDto<>(result));
    }
}
