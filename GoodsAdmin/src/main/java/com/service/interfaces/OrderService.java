package com.service.interfaces;

import com.pojo.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrderService {

    String selectcartIdList(int orderId);

    int addOrder(int userId,String cartList,String orderName,String orderTime,double amount,int status);

    //查询所有商品列表
    List<Orders> selectAllOrder();
    //添加货物
    public void addOrder(Orders orders);
    //按id查找货物信息
    public Orders selectOrderByorderId(int id);

    //按name查找货物信息
    public  List<Orders> selectOrderByuserId(int id);

    //修改货物信息
    public void updateOrder(Orders Orders);

    //根据id删除货物数据
    public void deleteOrderById( int id);

    //按keyword搜索货物信息
    public List<Orders> searchOrderById(int id);

//    public List<Cart> searchCartByid(int id);

//    public List<OrderInfo> select(int id);

    public List<Cart> searchCart(int id);

    public List<OrderDetail> selectOrderDetail(Integer userId,List<Integer> cartIdList);

    public List<OrderInfo> selectOrderInfo(int userId);

    public int updateStatus(String orderTime);

    public List<Orders> searchOrder(String keyword);

}
