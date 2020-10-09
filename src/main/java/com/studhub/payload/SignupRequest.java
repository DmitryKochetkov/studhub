package com.studhub.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;
}