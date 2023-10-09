package com.example.mall.service;

import com.example.mall.model.vo.CartVo;

import java.util.List;

public interface CartService {

    List<CartVo> list(Integer userId);

    List<CartVo> add(Integer userId, Integer productId, Integer count);
}
