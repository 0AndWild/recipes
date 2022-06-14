package com.sparta.recipes.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationDto {
    private String username;
    private String password;
}
