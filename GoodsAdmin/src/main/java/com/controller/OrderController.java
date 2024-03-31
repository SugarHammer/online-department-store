package com.controller;


import com.pojo.Orders;
import com.service.interfaces.OrderService;
import com.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //查询所有订单列表
    @GetMapping("/selectAllOrder")
    public JsonResponse selectAllOrder(){
        return new JsonResponse(JsonResponse.OK, orderService.selectAllOrder(), null);
    }

    //按orderId查找订单信息
    @GetMapping("/selectOrderByorderId")
    public JsonResponse selectOrderById(int id){
        return new JsonResponse(JsonResponse.OK, orderService.selectOrderByorderId(id), null);
    }

    //按查找订单信息
    @GetMapping("/selectOrderByuserId")
    public JsonResponse selectOrderByuserId(int id){
        return new JsonResponse(JsonResponse.OK, orderService.selectOrderByuserId(id), null);
    }



    //添加订单
    @GetMapping("/addOrder")
    public JsonResponse addOrder(int userId, String cartList, String orderName,String orderTime, int status, int amount){
        Orders Orders = new Orders(0,userId,cartList,orderName,orderTime,status,amount);
        orderService.addOrder(Orders);
        //返回添加好的数据
        return new JsonResponse(JsonResponse.OK, null, "添加成功！");
    }

    //修改订单信息
    @GetMapping("/updateOrder")
    public JsonResponse updateOrder(int orderId, int userId, @RequestParam(value = "cartList",required = true) List<Integer> cartList, String orderName,String orderTime, int status, int amount){
        Orders Orders = new Orders(orderId,userId,cartList.toString(),orderName,orderTime,status,amount);
        orderService.updateOrder(Orders);
        //返回添加好的数据
        return new JsonResponse(JsonResponse.OK, orderService.selectOrderByorderId(orderId), "修改成功");
    }

    //根据orderId删除订单数据
    @GetMapping("/deleteOrderById")
    public JsonResponse deleteOrderById(int id){
        orderService.deleteOrderById(id);
        //返回添加好的数据
        return new JsonResponse(JsonResponse.OK, null, null);
    }


    //按orderId查询订单列表
    @GetMapping("/searchOrderById")
    public JsonResponse searchOrderById(int id){
        return new JsonResponse(JsonResponse.OK, orderService.searchOrderById(id), null);
    }

    @GetMapping("/updateStatus")
    public JsonResponse searchOrderById(String orderTime){
        return new JsonResponse(JsonResponse.OK, orderService.updateStatus(orderTime), null);
    }

//    @GetMapping("/searchCartByid")
//    public JsonResponse searchCartByid(int id){
//        return new JsonResponse(JsonResponse.OK, orderService.searchCartByid(id), null);
//    }
//
//    @GetMapping("/select")
//    public JsonResponse select(int id){
//        return new JsonResponse(JsonResponse.OK, orderService.select(id), null);
//    }

    @GetMapping("/searchCart")
    public JsonResponse searchCart(int id){
        return new JsonResponse(JsonResponse.OK, orderService.searchCart(id), null);
    }

    @GetMapping("/selectOrderInfo")
    public JsonResponse selectOrderInfo(int userId){
        return new JsonResponse(JsonResponse.OK, orderService.selectOrderInfo(userId), null);
    }

    //按keyword查询商品列表
    @GetMapping("/searchOrder")
    public JsonResponse searchOrder(String keyword){
        return new JsonResponse(JsonResponse.OK, orderService.searchOrder(keyword), null);
    }

}
