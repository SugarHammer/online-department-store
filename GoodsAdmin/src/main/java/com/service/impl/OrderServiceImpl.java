package com.service.impl;

import com.mapper.CartMapper;
import com.mapper.GoodsMapper;
import com.mapper.OrderMapper;
import com.pojo.*;
import com.service.interfaces.OrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    GoodsMapper goodsMapper;

    private static Logger log = Logger.getLogger(OrderServiceImpl.class);

    @Override
    public String selectcartIdList(int orderId){
        return orderMapper.selectcartIdList(orderId);
    }

    @Override
    public int addOrder(int userId,String cartList,String orderName,String orderTime,double amount,int status){
        return orderMapper.addOrder(userId, cartList, orderName,orderTime, amount,status);
    }

    @Override
    public List<Orders> selectAllOrder() {
        log.info("/selectAllOrder start...");
        return orderMapper.selectAllOrder();
    }

    @Override
    public void addOrder(Orders Orders) {
        orderMapper.addOrder(Orders);
        return;
    }

    //按id查找货物信息
    @Override
    public Orders selectOrderByorderId(int id) {

        return orderMapper.selectOrderByorderId(id);
    }

    //按name查找货物信息
    @Override
    public List<Orders> selectOrderByuserId(int id) {

        return orderMapper.selectOrderByuserId(id);
    }

    //修改货物信息
    @Override
    public void updateOrder(Orders Orders) {
        orderMapper.updateOrder(Orders);
        return;
    }

    //更改订单状态
    @Override
    public  int updateStatus(String orderTime){
        return orderMapper.updateStatus(orderTime);
    }

    //根据id删除货物数据
    @Override
    public void deleteOrderById(int id) {
        orderMapper.deleteOrderById(id);
        return;
    }


    @Override
    //按keyword搜索货物信息
    public List<Orders> searchOrderById(int id){
        return  orderMapper.searchOrderById(id);
    }

    @Override
    public List<Cart> searchCart(int id) {
        return orderMapper.searchCart(id);
    }

    @Override
    public List<OrderDetail> selectOrderDetail(@RequestParam(value = "userId",required = true) Integer userId, @RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList) {
        List<OrderDetail> odList= new ArrayList();;
//        List<Integer> goodsIdList= cartMapper.selectGoodsIdByList(cartIdList);
        List<Cart> cartList= cartMapper.addCartOrder(userId,cartIdList);
        System.out.println("查询到的购物车列表为：" +cartList);
        for (Cart cart : cartList){
            System.out.println("正在查询的购物车为：" +cart.getCartId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCartId(cart.getCartId());
            orderDetail.setGoodsId(cart.getGoodsId());
            orderDetail.setNums(cart.getNum());
            orderDetail.setDealPrice(cart.getSumprice());
            Goods goods = goodsMapper.selectGoodsById(cart.getGoodsId());
            orderDetail.setGoodsName(goods.getGname());
            orderDetail.setImgSrc(goods.getPthumbnail());
            orderDetail.setGoodsPrice(goods.getGprice());
            odList.add(orderDetail);
        }
        System.out.println("最终生成的订单detail为：" +odList);
        return  odList;
    }

    @Override
    public List<OrderInfo> selectOrderInfo(int userId) {
        List<OrderInfo> list= new ArrayList();
        List<Orders> ordersList = orderMapper.selectOrderByuserId(userId);
        System.out.println("查询到的订单列表为：" +ordersList);
        for(Orders orders : ordersList){
            System.out.println("正在查询的订单为：" +orders.getOrderId());
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(orders.getOrderId());
            orderInfo.setOrderTime(orders.getOrderTime());


            List<String> isStr = Arrays.asList(orders.getCartList().split(","));
            List<Integer> isInt = new ArrayList<>();

            CollectionUtils.collect(isStr, new Transformer() {
                @Override
                public Object transform(Object obj) {
                    return Integer.valueOf(obj.toString());
                }},isInt);

            System.out.println("转换后cartLit为：" +isInt);

            orderInfo.setOdList( this.selectOrderDetail( userId, isInt) );
            list.add(orderInfo);
        }
        System.out.println("最终生成的订单info为：" +list);
        return  list;
    }

    @Override
    //按keyword搜索货物信息
    public List<Orders> searchOrder(String keyword){
        return  orderMapper.searchOrder(keyword);
    }


}
