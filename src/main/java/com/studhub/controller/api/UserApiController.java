package com.studhub.controller.api;

import com.studhub.dto.UserDto;
import com.studhub.entity.User;
import com.studhub.exception.ResourceNotFoundException;
import com.studhub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(tags = "Users", description = "Access registered users.")
public class UserApiController {
    @Autowired
    UserService userService;

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
    @ApiOperation(value = "Get all users")
    public ResponseEntity<Page<UserDto>> getUsers(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        if (pageable == null)
            throw new ResourceNotFoundException();
        Page<UserDto> result = userService.getAll(pageable).map(UserDto::new);
        return ResponseEntity.ok(result);
    }
}
