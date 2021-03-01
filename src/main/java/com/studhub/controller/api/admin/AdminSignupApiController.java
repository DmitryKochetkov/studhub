package com.studhub.controller.api.admin;

import com.studhub.dto.UserDto;
import com.studhub.entity.Role;
import com.studhub.entity.User;
import com.studhub.exception.BadRequestException;
import com.studhub.payload.SignupRequest;
import com.studhub.repository.RoleRepository;
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

@RestController
@RequestMapping("/api/admin/signup")
@Api(tags = "Signup", description = "Signup a new user.")
public class AdminSignupApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @ApiOperation(value = "Sign up a new user")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict")
        }
    )
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequest signupRequest) {
        User registered = userService.getUserByUsername(signupRequest.getUsername());
        if (registered != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        Role role = roleRepository.findByName("ROLE_" + signupRequest.getRole());
        if (role == null)
            throw new BadRequestException();

        return new ResponseEntity<UserDto>(new UserDto(userService.register(signupRequest)), HttpStatus.CREATED);
    }
}
