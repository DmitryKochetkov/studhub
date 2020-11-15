package com.studhub.controller.api;

import com.studhub.dto.CourseDto;
import com.studhub.dto.PageDto;
import com.studhub.dto.UserDto;
import com.studhub.entity.Course;
import com.studhub.entity.User;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.ResourceNotFoundException;
import com.studhub.service.CourseService;
import com.studhub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(tags = "Users", description = "Access registered users.")
public class UserApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found")
        }
    )
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null)
            throw new ResourceNotFoundException();

        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping(value = "/user")
    @ApiOperation(value = "Get user by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found")
    }
    )
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam String username) {
        User user = userService.getByUsername(username);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get up to 10 users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not found")
    }
    )
    public ResponseEntity<PageDto<UserDto>> getUsers(@RequestParam(defaultValue = "1") Integer page) {
        if (page - 1 < 0)
            throw new BadRequestException();
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<UserDto> result = userService.getAll(pageable).map(UserDto::new);
        if (result.getNumber() + 1 > result.getTotalPages() && result.getTotalPages() > 0)
            throw new ResourceNotFoundException();
        return ResponseEntity.ok(new PageDto<>(result));
    }

    @GetMapping(value = "/user/{user_id}/course/{course_id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

        Course course = courseService.getById(course_id);
        if (course == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (course.getStudent().getId() != user_id)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new CourseDto(course));
    }
}
