package com.studhub.service;

import com.studhub.entity.Role;
import com.studhub.entity.User;
import com.studhub.entity.UserStatus;
import com.studhub.payload.SignupRequest;
import com.studhub.repository.RoleRepository;
import com.studhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User register(SignupRequest dto) {
        User user = new User();
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        if (dto.getRole().equals("STUDENT"))
            roles.add(roleRepository.findByName("ROLE_STUDENT"));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword()); //TODO: bCryptPasswordEncoder
        user.setStatus(UserStatus.ENABLED);
        user.setRoles(roles);
        Date now = new Date();
        user.setCreated(now);
        user.setLastModified(now);
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
