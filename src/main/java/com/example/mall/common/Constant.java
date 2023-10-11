package com.example.mall.common;

import com.example.mall.exception.MallException;
import com.example.mall.exception.MallExceptionEnum;
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

    public interface SaleStatus{
        int NOT_SALE=0;
        int SALE=1;
    }

    public  interface  Cart {
        int UN_CHECKED= 0;
        int CHECKED=1;
    }

    public enum OrderStatusEnum{
        CANCELED("用户已取消",0),
        NOT_PAID("未付款",10),
        PAID("已付款",20),
        DELIVERED("已发货",30),
        FINISHED("交易完成",40);

        private String value;
        private int code;

        OrderStatusEnum(String value, int code){
                this.value=value;
                this.code=code;
        }

        public  static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode()==code){
                    return  orderStatusEnum;
                }
            }
            throw  new MallException(MallExceptionEnum.NO_ENUM);
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }


    }
}
