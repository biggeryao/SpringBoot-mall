package com.example.mall.service;

import com.example.mall.model.request.AddCategoryReq;
import com.example.mall.vo.CategoryVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void delete(Integer id);

    PageInfo listCategoryForAdmin(Integer pageNum, Integer pageSize);


    List<CategoryVo> listCategoryForCustomer();
}
