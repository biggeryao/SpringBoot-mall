package com.example.mall.service;


import com.example.mall.model.pojo.Product;
import com.example.mall.model.request.AddProductReq;

public interface ProductService {

    void  add (AddProductReq addProductReq);

    void update(Product product);

    void delete(Integer id);

    void  batchUpdateSellStatus(Integer[] ids, Integer sellStatus);
}
