package com.mapper;

import com.pojo.Cart;
import com.pojo.Goods;
import com.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper

public interface OrderMapper {

    String selectcartIdList(int orderId);

    int addOrder(int userId,String cartList,String orderName,String orderTime,double amount,int status);

    //查找所有货物信息
    List<Orders> selectAllOrder();
    //按id查找货物信息
    Orders selectOrderByorderId(@Param("orderId") int id);
    //按name查找货物信息
    List<Orders> selectOrderByuserId(@Param("userId") int id);
    //添加货物信息
    void addOrder(Orders orders);
    //修改货物信息
    void updateOrder(Orders orders);
    //根据id删除货物数据
    void deleteOrderById(@Param("orderId") int id);

    //按orderId搜索订单信息
    List<Orders> searchOrderById(@Param("orderId") int id);

    List<Cart> searchCart(@Param("userId") int id);

    //根据orderId将该订单由未支付——>已支付
    int updateStatus(String orderTime);

    //搜索
    List<Orders> searchOrder(@Param("keyword") String keyword);

    //List<OrderInfo> select(@RequestParam(value = "userId",required = true) Integer userId, @RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList);
}
