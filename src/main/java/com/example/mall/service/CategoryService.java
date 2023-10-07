package com.example.mall.service;

import com.example.mall.model.request.AddCategoryReq;
import org.springframework.stereotype.Service;

public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);
}
