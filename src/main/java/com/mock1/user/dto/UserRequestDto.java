package com.mock1.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto{
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserRequestDto(){}

    public UserRequestDto(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
