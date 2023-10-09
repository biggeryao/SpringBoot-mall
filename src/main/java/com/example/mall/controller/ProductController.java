package com.example.mall.controller;


import com.example.mall.common.ApiRestResponse;
import com.example.mall.model.pojo.Product;
import com.example.mall.model.request.ProductListReq;
import com.example.mall.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("product/detail")
    public ApiRestResponse detail(@RequestParam Integer id) {
        Product detail = productService.detail(id);
        return ApiRestResponse.success(detail);

    }
    @PostMapping("product/list")
    public ApiRestResponse list( ProductListReq productListReq) {
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);

    }
}
