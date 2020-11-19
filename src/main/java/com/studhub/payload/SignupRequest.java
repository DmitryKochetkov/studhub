package com.studhub.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Тело запроса на регистрацию пользователя
 */
@Data
@NoArgsConstructor
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;
}
