package com.example.mall.common;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Constant {

    public  static  final  String SALT = "1xqwiuejko1?>1!@";

    public  static  final  String MALL_USER = "mall_user";

    public  static String FILE_UPLOAD_DIR;


    @Value("${file.upload.dir}")
    public  void  setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR=fileUploadDir;
    }

    public  interface  ProductListOrderBy{
        Set<String> PRICE_ASC_DESC=Sets.newHashSet("price desc","price acs");
    }
}
