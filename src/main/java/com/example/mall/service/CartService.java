package com.example.mall.service;

import com.example.mall.vo.CartVo;

import java.util.List;

public interface CartService {

    List<CartVo> add(Integer userId, Integer productId, Integer count);
}
