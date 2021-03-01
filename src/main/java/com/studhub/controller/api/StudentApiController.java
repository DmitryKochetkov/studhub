package com.studhub.controller.api;

import com.studhub.dto.StudentDto;
import com.studhub.entity.Student;
import com.studhub.entity.User;
import com.studhub.exception.NotFoundException;
import com.studhub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get student by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found")
        }
    )
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        User user = userService.getById(id).orElseThrow(NotFoundException::new);
        if (user instanceof Student)
            return ResponseEntity.ok(new StudentDto((Student)user));
        else throw new NotFoundException();
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
}
