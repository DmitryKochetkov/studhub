package com.studhub.controller.api;

import com.studhub.dto.CourseDto;
import com.studhub.dto.StudentDto;
import com.studhub.entity.Course;
import com.studhub.entity.Student;
import com.studhub.entity.User;
import com.studhub.exception.NotFoundException;
import com.studhub.service.CourseService;
import com.studhub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *   Контроллер пользователей.
 * */
@RestController
@RequestMapping("/api")
@Api(tags = "Users", description = "Access registered users.")
public class StudentApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get user by id")
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
    @ApiOperation(value = "Get user by id")
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
}
