package com.studhub.controller.api;

import com.studhub.dto.UserDto;
import com.studhub.entity.User;
import com.studhub.payload.SignupRequest;
import com.studhub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/signup")
@Api(tags = "Signup", description = "Signup a new user.")
public class SignupApiController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "Sign up a new user")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequest dto) {
        User registered = userService.getByUsername(dto.getUsername());
        if (registered != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        return new ResponseEntity<UserDto>(new UserDto(userService.register(dto)), HttpStatus.CREATED);
    }
}
