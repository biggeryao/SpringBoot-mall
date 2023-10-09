package com.example.mall.service.impl;

import com.example.mall.common.Constant;
import com.example.mall.exception.MallException;
import com.example.mall.exception.MallExceptionEnum;
import com.example.mall.model.dao.ProductMapper;
import com.example.mall.model.pojo.Product;
import com.example.mall.model.query.ProductListQuery;
import com.example.mall.model.request.AddProductReq;
import com.example.mall.model.request.ProductListReq;
import com.example.mall.service.CategoryService;
import com.example.mall.service.ProductService;
import com.example.mall.vo.CategoryVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void add(AddProductReq addProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);
        Product productOld = productMapper.selectByName(addProductReq.getName());
        if (productOld != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Product product) {
        Product productOld = productMapper.selectByName(product.getName());
        if (productOld != null && !productOld.getId().equals(product.getId())) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective(product);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        }

    }

    @Override
    public void delete(Integer id) {
        Product productOld = productMapper.selectByPrimaryKey(id);
        if (productOld == null) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) {
        productMapper.batchUpdateSellStatus(ids, sellStatus);
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Product> productList = productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;

    }

    @Override
    public Product detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    @Override
    public PageInfo list(ProductListReq productListReq) {
        ProductListQuery productListQuery = new ProductListQuery();

        if (!StringUtils.isEmpty(productListReq.getKeyword())) {
            String keyWord = new StringBuilder().append("%").append(productListReq.getKeyword()).append("%").toString();
            productListQuery.setKeyWord(keyWord);
        }


        if (productListReq.getCategoryId() != null) {
            List<CategoryVo> categoryVoList = categoryService.listCategoryForCustomer(productListReq.getCategoryId());
            ArrayList<Integer> categoryIds = new ArrayList<>();

            categoryIds.add(productListReq.getCategoryId());
            getCategoryIds(categoryVoList, categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }

        String orderBy = productListReq.getOrderBy();
        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize(), orderBy);
        } else {
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize());
        }
        List<Product> productList = productMapper.selectList(productListQuery);
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }

    private void getCategoryIds(List<CategoryVo> categoryVoList, ArrayList<Integer> categoryIds) {
        for (int i = 0; i < categoryVoList.size(); i++) {
            CategoryVo categoryVo = categoryVoList.get(i);
            if (categoryVo != null) {
                categoryIds.add(categoryVo.getId());
                getCategoryIds(categoryVo.getChildCategory(), categoryIds);
            }
        }
    }
}
