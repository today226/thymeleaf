package com.denis.thymeleaf.thymeleaf.controller;

import com.denis.thymeleaf.thymeleaf.model.User;
import com.denis.thymeleaf.thymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){

        return "account/login";
    }

    @GetMapping("/register")
    public String register(){

        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user){

        userService.save(user);
        return "redirect:/"; //redirect를 이용해 HomeController에 index를 태우는 효과를 줄 수 있다
    }
}
