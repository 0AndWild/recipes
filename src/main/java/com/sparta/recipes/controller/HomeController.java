package com.sparta.recipes.controller;

import com.sparta.recipes.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//@CrossOrigin("http://localhost:3000")
@RestController
@Controller
public class HomeController {

//    @GetMapping("/")
//    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails != null) {
//            model.addAttribute("username", userDetails.getUsername()); //key:value
//            model.addAttribute("loginStatus", true);
//            return "index";
//        } else {
//            model.addAttribute("username");
//            return "index";
//        }
//    }
    @GetMapping("/")
    public boolean home(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return true;
        }
            return false;
    }
    }


