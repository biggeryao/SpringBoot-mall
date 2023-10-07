package com.example.mall.service.impl;

import com.example.mall.exception.MallException;
import com.example.mall.exception.MallExceptionEnum;
import com.example.mall.model.dao.UserMapper;
import com.example.mall.model.pojo.User;
import com.example.mall.service.UserService;
import com.example.mall.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser() {

        return userMapper.selectByPrimaryKey(4);
    }

    @Override
    public void register(String userName, String password) throws MallException, NoSuchAlgorithmException {

        User result = userMapper.selectByName(userName);

        if (result != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }

        User user = new User();
        user.setPassword(MD5Util.getMD5Str(password));
        user.setUsername(userName);
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.INSERT_FAIL);
        }
    }


    @Override
    public User login(String userName, String password) throws MallException {
        String ma5Password = null;
        try {
            ma5Password = MD5Util.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        User user = userMapper.selectLogin(userName, ma5Password);

        if (user == null) {
            throw new MallException(MallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    @Override
    public void updateInformation(User user) throws MallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        }

    }

    @Override
    public boolean checkAdminRole(User user) {
        //1：普通用户 2：管理员
        return user.getRole().equals(2);
    }
}
