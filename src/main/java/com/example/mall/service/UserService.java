package com.example.mall.service;

import com.example.mall.exception.MallException;
import com.example.mall.model.pojo.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
     User getUser();


     void  register(String userName,String password) throws MallException, NoSuchAlgorithmException;

    User login(String userName, String password) throws MallException;

    void updateInformation(User user) throws MallException;

    boolean checkAdminRole(User user);
}
