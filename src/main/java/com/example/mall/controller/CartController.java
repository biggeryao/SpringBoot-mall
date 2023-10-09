package com.example.mall.controller;

import com.example.mall.common.ApiRestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @PostMapping("/add")
    public ApiRestResponse add (@RequestParam Integer productId,@RequestParam Integer count){
        return  ApiRestResponse.success();
    }
}
