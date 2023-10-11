package com.example.mall.service.impl;

import com.example.mall.common.Constant;
import com.example.mall.exception.MallException;
import com.example.mall.exception.MallExceptionEnum;
import com.example.mall.filter.UserFilter;
import com.example.mall.model.dao.CartMapper;
import com.example.mall.model.dao.OrderItemMapper;
import com.example.mall.model.dao.OrderMapper;
import com.example.mall.model.dao.ProductMapper;
import com.example.mall.model.pojo.Order;
import com.example.mall.model.pojo.OrderItem;
import com.example.mall.model.pojo.Product;
import com.example.mall.model.request.CreateOrderReq;
import com.example.mall.model.vo.CartVo;
import com.example.mall.service.CartService;
import com.example.mall.service.OrderService;
import com.example.mall.utils.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateOrderReq createOrderReq){
        //拿到用户ID
        Integer id = UserFilter.currentUser.getId();

        //用购物车中查找已勾选的商品
        List<CartVo> cartVoList = cartService.list(id);
        ArrayList<CartVo> cartVoListTemp=new ArrayList<>();
        for (int i = 0; i < cartVoList.size(); i++) {
            CartVo cartVo = cartVoList.get(i);
            if(cartVo.getSelected().equals(Constant.Cart.CHECKED)){
                cartVoListTemp.add(cartVo);
            }
        }
        cartVoList=cartVoListTemp;

        //如果购物车已经勾选为空，报错
        if(cartVoList.isEmpty()){
            throw  new MallException(MallExceptionEnum.CART_EMPTY);
        }

        //判断商品是否存在、上下架、库存
        validSaleStatusAndStock(cartVoList);

        //把购物车对象转为订单item对象
        List<OrderItem> orderItemList = cartVoListToOrderItemList(cartVoList);

        //扣库存
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            int stock = product.getStock() - orderItem.getQuantity();
            if(stock<0){
                throw  new MallException(MallExceptionEnum.NOT_ENOUGH);
            }

            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        }

        //把购物车已经勾选的商品删除
        cleanCart(cartVoList);

        //生成订单
        Order order = new Order();
        String orderCode = OrderCodeFactory.getOrderCode(Long.valueOf(id));
        order.setOrderNo(orderCode);
        order.setUserId(id);
        order.setTotalPrice(totalPrice(orderItemList));
        order.setReceiverName(createOrderReq.getReceiverName());
        order.setReceiverMobile(createOrderReq.getReceiverMobile());
        order.setReceiverAddress(createOrderReq.getReceiverAddress());
        order.setOrderStatus(Constant.OrderStatusEnum.NOT_PAID.getCode());
        order.setPostage(0);
        order.setPaymentType(1);

        //插入到order表中
        orderMapper.insertSelective(order);

        //循环保存每个商品到order_item表中
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insertSelective(orderItem);
        }

        //返回结果
        return orderCode;
    }

    private Integer totalPrice(List<OrderItem> orderItemList) {
        Integer price=0;
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            price+=orderItem.getTotalPrice();
        }
        return  price;
    }

    public void validSaleStatusAndStock(List<CartVo> cartVoList){
        for (int i = 0; i < cartVoList.size(); i++) {
            CartVo cartVo = cartVoList.get(i);
            Product product = productMapper.selectByPrimaryKey(cartVo.getProductId());
            //判断商品是否存在、是否上架
            if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
                throw new MallException(MallExceptionEnum.NOT_SALE);
            }

            //判断库存
            if (cartVo.getQuantity() > product.getStock()) {
                throw new MallException(MallExceptionEnum.NOT_ENOUGH);
            }
        }
    }

    public List<OrderItem> cartVoListToOrderItemList(List<CartVo> cartVoList){
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < cartVoList.size(); i++) {
            CartVo cartVo = cartVoList.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVo.getProductId());
            orderItem.setProductName(cartVo.getProductName());
            orderItem.setProductImg(cartVo.getProductImage());
            orderItem.setUnitPrice(cartVo.getPrice());
            orderItem.setQuantity(cartVo.getQuantity());
            orderItem.setTotalPrice(cartVo.getTotalPrice());
            orderItemList.add(orderItem);
        }
        return  orderItemList;
    }

    public void cleanCart(List<CartVo> cartVoList){
        for (int i = 0; i < cartVoList.size(); i++) {
            CartVo cartVo = cartVoList.get(i);
            cartMapper.deleteByPrimaryKey(cartVo.getId());
        }
    }
}
