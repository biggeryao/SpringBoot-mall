package com.example.mall.service;

import com.example.mall.model.request.CreateOrderReq;
import com.example.mall.model.vo.CartVo;
import com.example.mall.model.vo.OrderVo;

import java.util.List;

public interface OrderService {

    String create(CreateOrderReq createOrderReq);

    OrderVo detail(String orderNo);

    void cancel(String orderNo);
}
