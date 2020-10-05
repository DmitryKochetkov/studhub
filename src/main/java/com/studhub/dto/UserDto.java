package com.studhub.dto;

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
public class UserDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private UserStatus status;
    private List<RoleDto> roles;
    private List<UserDto> followers;
    private List<UserDto> following;
    private List<CourseDto> courses;

    public UserDto(User user) {
        super(user);
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getStatus();
        roles = new ArrayList<>();
        for (Role role: user.getRoles())
            roles.add(new RoleDto(role));

        following = new ArrayList<>();
        //not sure about efficiency here
        if (user.getFollowing() != null)
            for (User followingUser: user.getFollowing())
                following.add(new UserDto(followingUser));

        courses = new ArrayList<>();
        if (user.getCourses() != null)
            for (Course course: user.getCourses())
                courses.add(new CourseDto(course));
    }

    public String getRolesString() {
        if (roles.isEmpty())
            return "NaN";
        String result = roles.get(0).getNameForUI();
        for (int i = 1; i < roles.size(); i++)
            result = result + ", " + roles.get(i).getNameForUI();
        return result;
    }

    public boolean isStudent() {
        for (RoleDto roleDto: roles)
            if (roleDto.getName().equals("ROLE_STUDENT"))
                return true;
        return false;
    }

    public String getFullname() {
        return firstName + " " + lastName;
    }
}
