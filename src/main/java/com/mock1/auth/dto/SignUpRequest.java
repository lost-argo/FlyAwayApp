package com.mock1.auth.dto;

import lombok.Getter;
import lombok.Setter;
import com.mock1.user.domain.Role;

@Getter
@Setter
public class SignUpRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
}
