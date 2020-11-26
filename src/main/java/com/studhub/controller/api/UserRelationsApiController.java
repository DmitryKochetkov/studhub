package com.studhub.controller.api;

import com.studhub.dto.PageDto;
import com.studhub.dto.UserDto;
import com.studhub.entity.User;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.NotFoundException;
import com.studhub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
*   Контроллер взаимотношений между пользователями.
* */
@RestController
@RequestMapping("/api")
@Api(tags = "Relations", description = "Get relations between users.")
public class UserRelationsApiController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/{user_id}/followers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users who follow a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<PageDto<UserDto>> getFollowers(@PathVariable Long user_id, @RequestParam(defaultValue = "1") Integer page) {
        User user = userService.getById(user_id);
        if (user == null)
            throw new NotFoundException();

        if (page - 1 < 0)
            throw new BadRequestException();
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<UserDto> result = userService.getUsersWhoFollowUser(user, pageable).map(UserDto::new);
        if (result.getNumber() + 1 > result.getTotalPages() && result.getTotalPages() > 0)
            throw new NotFoundException();
        return ResponseEntity.ok(new PageDto<>(result));
    }

    @GetMapping(value = "/user/{user_id}/following", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users followed by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<PageDto<UserDto>> getUsersWhoAreFollowedByUser(@PathVariable Long user_id, @RequestParam(defaultValue = "1") Integer page) {
        User user = userService.getById(user_id);
        if (user == null)
            throw new NotFoundException();

        if (page - 1 < 0)
            throw new BadRequestException();
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<UserDto> result = userService.getUsersWhoAreFollowedByUser(user, pageable).map(UserDto::new);
        if (result.getNumber() + 1 > result.getTotalPages() && result.getTotalPages() > 0)
            throw new NotFoundException();
        return ResponseEntity.ok(new PageDto<>(result));
    }
}
