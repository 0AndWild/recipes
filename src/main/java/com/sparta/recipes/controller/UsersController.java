package com.sparta.recipes.controller;

import com.sparta.recipes.dto.SignupRequestDto;
import com.sparta.recipes.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Controller
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
    @PostMapping("/user/register")
    public void registerUser(@RequestBody SignupRequestDto requestDto){
        usersService.registerUser(requestDto);



    }
}
