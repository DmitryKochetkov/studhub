package com.studhub.controller.api;

import com.studhub.service.UserService;
import com.studhub.payload.SignupRequest;
import com.studhub.dto.UserDto;
import com.studhub.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "Users", description = "Access registered users.")
public class UserApiController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get user by id")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam String username) {
        User user = userService.getByUsername(username);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get all users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> result = new ArrayList<>();
        for (User user: userService.getAll())
            result.add(new UserDto(user));
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Sign up a new user")
    @PostMapping(
            value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequest dto) {
        User registered = userService.getByUsername(dto.getUsername());
        if (registered != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        return ResponseEntity.ok(new UserDto(userService.register(dto)));
    }
}
