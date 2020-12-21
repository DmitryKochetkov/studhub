package com.studhub.controller.api.admin;

import com.studhub.dto.PageDto;
import com.studhub.dto.UserDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = "Manage users", description = "Manage registered users.")
public class AdminUserApiController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    @ApiOperation(value = "Get up to 10 users", produces = MediaType.APPLICATION_JSON_VALUE)
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
            throw new NotFoundException();
        return ResponseEntity.ok(new PageDto<>(result));
    }
}
