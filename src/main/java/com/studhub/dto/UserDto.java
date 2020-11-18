package com.studhub.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.studhub.entity.Course;
import com.studhub.entity.Role;
import com.studhub.entity.User;
import com.studhub.entity.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"id", "created", "lastModified", "username", "firstName", "lastName", "status", "roles", "followers", "following", "courses"})
public class UserDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String username;
    private UserStatus status;
    private List<RoleDto> roles;
    private List<CourseDto> courses;

    public UserDto(User user) {
        super(user);
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.status = user.getStatus();
        roles = new ArrayList<>();
        for (Role role: user.getRoles())
            roles.add(new RoleDto(role));

        courses = new ArrayList<>();
        if (user.getCourses() != null)
            for (Course course: user.getCourses())
                courses.add(new CourseDto(course));
    }

    @JsonIgnore
    public boolean isStudent() {
        for (RoleDto roleDto: roles)
            if (roleDto.getName().equals("ROLE_STUDENT"))
                return true;
        return false;
    }

    @JsonIgnore
    public String getFullname() {
        return firstName + " " + lastName;
    }
}
