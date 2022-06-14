package com.sparta.recipes.controller;

import com.sparta.recipes.dto.LoginRequestDto;
import com.sparta.recipes.dto.SignupRequestDto;
import com.sparta.recipes.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
//@Controller
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

//    /*로그인페이지 이동*/
//    @GetMapping("/user/login")
//    public String login(){return "login";}

    /*회원가입페이지 이동*/
    @GetMapping("/user/register")
    public String signup(){return "register";}


    /*회원가입요청처리*/
    /*유저네임 중복시 response로 보내줘야함*/
    @PostMapping("/user/register")
    public Long registerUser(@RequestBody SignupRequestDto signupRequestDto){
        if (usersService.registerUser(signupRequestDto)){
            return 1L;
        } else {
            return 0L;
        }
    }

//    @PostMapping("/user/login")
//    public String loginUser(@RequestBody LoginRequestDto loginRequestDto, User){
//        usersService.loginUser(loginRequestDto);
//    }
}
