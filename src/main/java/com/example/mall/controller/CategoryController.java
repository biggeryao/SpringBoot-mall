package com.example.mall.controller;

import com.example.mall.common.ApiRestResponse;
import com.example.mall.common.Constant;
import com.example.mall.exception.MallExceptionEnum;
import com.example.mall.model.pojo.User;
import com.example.mall.model.request.AddCategoryReq;
import com.example.mall.service.CategoryService;
import com.example.mall.service.UserService;
import com.example.mall.model.vo.CategoryVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("后台添加目录")
    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession httpSession, @Valid @RequestBody AddCategoryReq addCategoryReq) {

        User currentUser = (User) httpSession.getAttribute(Constant.MALL_USER);

        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }

        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole) {
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
        }

    }

    @ApiOperation("后台删除目录")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id) {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }
    @ApiOperation("后台目录列表")
    @PostMapping("admin/category/list")
    @ResponseBody
    public  ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize){

        PageInfo pageInfo = categoryService.listCategoryForAdmin(pageNum, pageSize);

        return ApiRestResponse.success(pageInfo);
    }
    @ApiOperation("前台目录列表")
    @PostMapping("category/list")
    @ResponseBody
    public  ApiRestResponse listCategoryForCustomer(){

        List<CategoryVo> categoryVos = categoryService.listCategoryForCustomer(0);
        return ApiRestResponse.success(categoryVos);
    }
}
