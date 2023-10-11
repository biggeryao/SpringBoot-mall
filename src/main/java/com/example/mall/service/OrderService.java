package com.example.mall.service;

import com.example.mall.model.request.CreateOrderReq;
import com.example.mall.model.vo.CartVo;

import java.util.List;

public interface OrderService {

    String create(CreateOrderReq createOrderReq);
}
