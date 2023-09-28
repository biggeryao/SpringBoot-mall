package com.example.mall.service.impl;

import com.example.mall.exception.MallException;
import com.example.mall.exception.MallExceptionEnum;
import com.example.mall.model.dao.UserMapper;
import com.example.mall.model.pojo.User;
import com.example.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser() {

        return userMapper.selectByPrimaryKey(4);
    }

    @Override
    public void register(String userName, String password) throws MallException {

        User result = userMapper.selectByName(userName);

        if (result != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }

        User user = new User();
        user.setPassword(password);
        user.setUsername(userName);
        int count = userMapper.insertSelective(user);
        if(count==0){
            throw new MallException(MallExceptionEnum.INSERT_FAIL);
        }
    }
}
