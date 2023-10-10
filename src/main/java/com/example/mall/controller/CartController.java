package com.example.mall.controller;

import com.example.mall.common.ApiRestResponse;
import com.example.mall.filter.UserFilter;
import com.example.mall.model.vo.CartVo;
import com.example.mall.service.CartService;
import io.swagger.models.auth.In;
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
    public ApiRestResponse update(Integer productId, Integer count) {
        List<CartVo> list = cartService.update(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(list);
    }

    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam Integer productId) {
        List<CartVo> list = cartService.delete(UserFilter.currentUser.getId(), productId);
        return ApiRestResponse.success(list);
    }

    @PostMapping("/select")
    public ApiRestResponse select(@RequestParam Integer productId, @RequestParam Integer selected) {
        List<CartVo> list = cartService.selected(UserFilter.currentUser.getId(), productId, selected);
        return ApiRestResponse.success(list);
    }
}
