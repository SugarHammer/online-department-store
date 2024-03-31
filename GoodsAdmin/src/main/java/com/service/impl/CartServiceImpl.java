package com.service.impl;

import com.mapper.CartMapper;
import com.pojo.Cart;
import com.service.interfaces.CartService;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    private static Logger log = Logger.getLogger(CartServiceImpl.class);


    @Override
    public List<Cart> selectAllCart(){
        log.info("/selectAllCart start...");
        return cartMapper.selectAllCart();
    }

    @Override
    public int addCart(int userId,int goodsId,int num,double sumprice){
        log.info("/addCart start...");
        return cartMapper.addCart(userId, goodsId, num,sumprice) ;
    }

    @Override
    public int deleteCart(int cartId){
        log.info("/deleteCart start...");
        return cartMapper.deleteCart(cartId);
    }

    @Override
    public  Cart selectCartById(int cartId){
        log.info("/selectCartById start...");
        return cartMapper.selectCartById(cartId);
    }

    @Override
    public int updateCart(int cartId,int userId,int goodsId,int num,double sumprice){
        log.info("/updateCart start...");
        return cartMapper.updateCart(cartId, userId, goodsId, num, sumprice);
    }

    @Override
    public int updateCartprice(int cartId,int num,double sumprice){
        log.info("/updateCartprice start...");
        return cartMapper.updateCartprice(cartId,num,sumprice);
    }

    @Override
    public int deleteAllCart(@RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList){
        log.info("/deleteAllCart start...");
        return cartMapper.deleteAllCart(cartIdList);
    }





    @Override
    public List<Cart> queryCartById(Integer cartId) {
        log.info("/queryCardById start...");
       return cartMapper.queryCartById(cartId);
    }

    @Override
    public int toShoppingCart(int goodsId,int num,int userId,double sumprice){
        log.info("/toShoppingCart start...");
        cartMapper.insertShoppingCart(goodsId,num,userId,sumprice);
        return 1;
    }

//    @Override
//    public List<Cart> queryCartByUserId(Integer userId){
//        log.info("/queryCardByUserId start...");
//        return cartMapper.queryCartByUserId(userId);
//    }

    @Override
    public int deleteGoods(int userId,int cartId){
        log.info("/deleteGoods start...");
        cartMapper.deleteGoods(userId,cartId);
        return 1;
    }

    @Override
   public List<Cart> addCartOrder(@RequestParam(value = "userId",required = true) Integer userId, @RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList){
        log.info("/addCartOrder start...");
        return cartMapper.addCartOrder(userId,cartIdList);
    }

    @Override
    public List<Integer> selectGoodsIdByList(@RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList){

//        String cart_str = "";
//        for (int i = 0; i < cartIdList.length(); i++) {
//
//                cart_str+=cartIdList.charAt(i)+",";
//
//        }
//
//        String cart_list=cart_str.substring(0,cart_str.length()-1);
//        System.out.println(cart_list);
        System.out.println(cartIdList);
        return cartMapper.selectGoodsIdByList(cartIdList);
    }

}
