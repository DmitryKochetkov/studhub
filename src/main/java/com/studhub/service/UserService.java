package com.studhub.service;

import com.studhub.entity.Role;
import com.studhub.entity.Student;
import com.studhub.entity.User;
import com.studhub.entity.UserStatus;
import com.studhub.payload.SignupRequest;
import com.studhub.repository.RoleRepository;
import com.studhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User register(SignupRequest dto) {
        if (dto.getUsername() == null || dto.getUsername().equals(""))
            return null;

        User user = new User();
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        if (dto.getRole().equals("STUDENT")) {
            user = new Student();
            roles.add(roleRepository.findByName("ROLE_STUDENT"));
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(UserStatus.ENABLED);
        user.setRoles(roles);
        user.setFollowers(new ArrayList<>());
        user.setFollowing(new ArrayList<>());
        LocalDateTime now = LocalDateTime.now();
        user.setCreated(now);
        user.setLastModified(now);
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Page<User> getUsersWhoFollowUser(User user, Pageable pageable) {
        return userRepository.findUsersByFollowingContains(user, pageable);
    }

    public Page<User> getUsersWhoAreFollowedByUser(User user, Pageable pageable) {
        return userRepository.findUsersByFollowersContains(user, pageable);
    }
}
