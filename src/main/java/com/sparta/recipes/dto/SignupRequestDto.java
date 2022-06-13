package com.sparta.recipes.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/*회원가입 요청 Dto*/
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
