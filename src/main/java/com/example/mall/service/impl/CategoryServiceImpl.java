package com.example.mall.service.impl;

import com.example.mall.exception.MallException;
import com.example.mall.exception.MallExceptionEnum;
import com.example.mall.model.dao.CategoryMapper;
import com.example.mall.model.pojo.Category;
import com.example.mall.model.request.AddCategoryReq;
import com.example.mall.service.CategoryService;
import com.example.mall.model.vo.CategoryVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import springfox.documentation.annotations.Cacheable;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }

        int count = categoryMapper.insertSelective(category);

        if (count == 0) {
            throw new MallException(MallExceptionEnum.CREATE_FAILED);

        }
    }

    @Override
    public void delete(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listCategoryForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "type,order_num");
        List<Category> categoryList = categoryMapper.selectList();

        PageInfo pageInfo = new PageInfo(categoryList);

        return pageInfo;
    }

    @Override
    @Cacheable(value="listCategoryForCustomer")
    public List<CategoryVo> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryVo> categoryVoList = new ArrayList<>();
        recursivelyFindCategories(categoryVoList, parentId);
        return categoryVoList;
    }

    private void recursivelyFindCategories(List<CategoryVo> CategoryVoList, Integer parentId) {
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category, categoryVo);
                CategoryVoList.add(categoryVo);
                recursivelyFindCategories(categoryVo.getChildCategory(), categoryVo.getId());
            }

        }
    }
}