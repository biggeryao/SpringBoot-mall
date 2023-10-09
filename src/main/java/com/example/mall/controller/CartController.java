package com.example.mall.controller;

import com.example.mall.common.ApiRestResponse;
import com.example.mall.filter.UserFilter;
import com.example.mall.model.vo.CartVo;
import com.example.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/list")
    public ApiRestResponse list() {
        List<CartVo> list = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(list);

    }

    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {

        List<CartVo> list = cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(list);
    }

    @PostMapping("/update")
    public ApiRestResponse update ()
}
