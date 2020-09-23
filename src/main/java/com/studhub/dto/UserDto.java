package com.studhub.dto;

import com.studhub.entity.Role;
import com.studhub.entity.User;
import com.studhub.entity.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private UserStatus status;
    private List<RoleDto> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getStatus();
        roles = new ArrayList<>();
        for (Role role: user.getRoles())
            roles.add(new RoleDto(role));
    }

    public String getRolesString() {
        if (roles.isEmpty())
            return "NaN";
        String result = roles.get(0).toString();
        for (int i = 1; i < roles.size(); i++)
            result = result + ", " + roles.get(i).toString();
        return result;
    }
}
