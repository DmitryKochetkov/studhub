package com.studhub.dto;

import com.studhub.entity.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private String name;

    RoleDto(Role role) {
        this.name = role.getName();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof RoleDto))
            return true;

        return ((RoleDto)obj).getName() == this.name;
    }

    public String getNameForUI() {
        if (name.equals("ROLE_USER"))
            return "Пользователь";

        if (name.equals("ROLE_STUDENT"))
            return "Ученик";

        if (name.equals("ROLE_ADMIN"))
            return "Администратор";

        return name;
    }
}
