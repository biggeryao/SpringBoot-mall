package com.example.mall.controller;

import com.example.mall.model.pojo.User;
import com.example.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    @ResponseBody

    public User personalPage(){
        User user = userService.getUser();

        return  user;
    }

}
