package com.example.mall.controller;

import com.example.mall.common.ApiRestResponse;
import com.example.mall.model.request.CreateOrderReq;
import com.example.mall.model.vo.OrderVo;
import com.example.mall.service.CartService;
import com.example.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("order/create")
    public ApiRestResponse create(@RequestBody CreateOrderReq createOrderReq) {
        String orderNo = orderService.create(createOrderReq);
        return ApiRestResponse.success(orderNo);
    }

    @PostMapping("order/detail")
    public ApiRestResponse detail(@RequestParam String orderNo){
        OrderVo detail = orderService.detail(orderNo);
        return  ApiRestResponse.success(detail);
    }

}
