package com.studhub.dto;

import com.studhub.entity.Role;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoleDto {
    private String name;

    RoleDto(Role role) {
        this.name = role.getName();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (name.equals("ROLE_USER"))
            return "Пользователь";

        if (name.equals("ROLE_STUDENT"))
            return "Ученик";

        if (name.equals("ROLE_ADMIN"))
            return "Администратор";

        return name;
    }
}
