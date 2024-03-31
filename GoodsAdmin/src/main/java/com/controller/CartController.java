package com.controller;

import com.service.interfaces.CartService;
import com.service.interfaces.Cart_GoodsService;
import com.service.interfaces.OrderService;
import com.utils.JsonResponse;
import com.utils.OrderUtil;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    Cart_GoodsService cart_goodsService;

    @Autowired
    OrderService orderService;


    @GetMapping("/selectAllCart")
    public JsonResponse selectAllCart(){
        return new JsonResponse(JsonResponse.OK, cartService.selectAllCart(), null);
    }

    @GetMapping("/addCart")
    public JsonResponse addCart(int userId,int goodsId,int num,double sumprice){
        return new JsonResponse(JsonResponse.OK, cartService.addCart(userId, goodsId, num, sumprice), null);
    }

    @GetMapping("/deleteCart")
    public JsonResponse deleteCart(int cartId){
        return new JsonResponse(JsonResponse.OK, cartService.deleteCart(cartId), null);
    }

    @GetMapping("/selectCartById")
    public JsonResponse selectCartById(int cartId){
        return new JsonResponse(JsonResponse.OK, cartService.selectCartById(cartId), null);
    }

    @GetMapping("/updateCart")
    public JsonResponse updateCart(int cartId,int userId,int goodsId,int num,double sumprice){
        return new JsonResponse(JsonResponse.OK, cartService.updateCart(cartId, userId, goodsId, num, sumprice), null);
    }

    @GetMapping("/updateCartprice")
    public JsonResponse updateCartprice(int cartId,int num,double sumprice){
        return new JsonResponse(JsonResponse.OK, cartService.updateCartprice(cartId,num,sumprice), null);
    }

    @GetMapping("/queryCartById")
    public JsonResponse queryCartById(Integer cartId){
        return new JsonResponse(JsonResponse.OK, cartService.queryCartById(cartId), null);
    }

    @GetMapping("/toShoppingCart")
    public JsonResponse toShoppingCart(int goodsId,int num,int userId,double sumprice){
        return new JsonResponse(JsonResponse.OK, cartService.toShoppingCart(goodsId,num,userId,sumprice), null);
    }


    @GetMapping("/queryGoodsByUserId")
    public JsonResponse queryGoodsByUserId(Integer userId){
        System.out.println( cart_goodsService.queryGoodsByUserId(userId));
        return new JsonResponse(JsonResponse.OK, cart_goodsService.queryGoodsByUserId(userId), null);
    }

    @GetMapping("/deleteGoods")
    public JsonResponse deleteGoods(int userId,int cartId) {
        return new JsonResponse(JsonResponse.OK, cartService.deleteGoods(userId,cartId), null);
    }

    @GetMapping("/addCartOrder")
    public JsonResponse addCartOrder(@RequestParam(value = "userId",required = true) Integer userId,@RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList) {
        return new JsonResponse(JsonResponse.OK, cartService.addCartOrder(userId,cartIdList), null);
    }

    @GetMapping("/selectcartIdList")
    public JsonResponse selectcartIdList(int orderId) {
        return new JsonResponse(JsonResponse.OK, orderService.selectcartIdList(orderId), null);
    }

    @GetMapping("/selectGoodsIdByList")
    public JsonResponse selectGoodsIdByList(@RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList) {
        return new JsonResponse(JsonResponse.OK, cartService.selectGoodsIdByList(cartIdList), null);
    }

    @GetMapping("/deleteAllCart")
    public JsonResponse deleteAllCart(@RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList) {
        return new JsonResponse(JsonResponse.OK, cartService.deleteAllCart(cartIdList),null);
    }

    /**
     * 支付宝沙箱支付
     */
    @GetMapping("/order")
    public String buy(@RequestParam(value = "goodsIdList",required = true) List<Integer> goodsIdList,@RequestParam(value = "totalprice",required = true)double totalprice
                      ,@RequestParam(value = "userId",required = true) int userId){

        Double sumprice=totalprice;
        String orderNo= OrderUtil.getOrderNo();
        System.out.println(goodsIdList);
        String orderTime=orderNo;
        StringBuilder goodsnameList = new StringBuilder();
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (Integer integer : goodsIdList) {
            //遍历的同时根据cartId查出商品名称
            String goodsname=cart_goodsService.selectGnameBycartId(integer);

            if (flag) {
                result.append(",");
                goodsnameList.append(",");
            } else {
                flag = true;
            }
            result.append(integer);
            goodsnameList.append(goodsname);
        }
         String  list=result.toString();
         String goodsNameList=goodsnameList.toString();
         System.out.println(list);
         System.out.println("传参——orderName："+goodsNameList);
        orderService.addOrder(userId,list,goodsNameList,orderTime,sumprice,1);
        cartService.deleteAllCart(goodsIdList);
        String payUrl="http://120.77.80.71:8081/alipay/pay?subject="+orderNo+"&traceNo="+orderNo+"&totalAmount="+sumprice;
//        String payUrl="http://127.0.0.1:8081/alipay/pay?subject="+orderNo+"&traceNo="+orderNo+"&totalAmount="+sumprice;
        System.out.println("payUrl====="+payUrl);
        return payUrl;
    }


}

