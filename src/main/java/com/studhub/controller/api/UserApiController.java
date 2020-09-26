package com.studhub.controller.api;

import com.studhub.dto.RegisterRequestDto;
import com.studhub.dto.RoleDto;
import com.studhub.dto.UserDto;
import com.studhub.entity.Role;
import com.studhub.entity.User;
import com.studhub.entity.UserStatus;
import com.studhub.repository.RoleRepository;
import com.studhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> result = new ArrayList<>();
        for (User user: userRepository.findAll())
            result.add(new UserDto(user));
        return ResponseEntity.ok(result);
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequestDto dto) {
        User registered = userRepository.findByUsername(dto.getUsername());
        if (registered != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        User user = new User();
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(dto.getRole()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword()); //TODO: bCryptPasswordEncoder
        user.setStatus(UserStatus.ENABLED);
        user.setRoles(roles);
        Date now = new Date();
        user.setCreated(now);
        user.setLastModified(now);
        return ResponseEntity.ok(new UserDto(userRepository.save(user)));
    }
}
