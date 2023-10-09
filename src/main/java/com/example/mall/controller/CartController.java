package com.example.mall.controller;

import com.example.mall.common.ApiRestResponse;
import com.example.mall.filter.UserFilter;
import com.example.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {

        cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success();
    }
}
