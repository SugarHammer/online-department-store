package com.mapper;

import com.pojo.Cart;
import com.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@Mapper
public interface CartMapper {

    List<Cart> selectAllCart();

    int addCart(int userId,int cartId,int num,double sumprice);

    int deleteCart(int cartId);

    Cart selectCartById(int cartId);

    int updateCart(int cartId,int userId,int goodsId,int num,double sumprice);

    /**
     * =============================================================
     * 根据cartId查询购物车信息
     */
     List<Cart> queryCartById(Integer cartId);

    /**
     * 加入购物车
     */
    int insertShoppingCart(int goodsId,int num,int userId,double sumprice);

//    /**
//     * 根据用户Id查询信息
//     */
//    List<Cart> queryCartByUserId(Integer userId);

    /**
     * 删除购物车商品
     */
    int deleteGoods(int userId,int cartId);

    int updateCartprice(int cartId,int num,double sumprice);

    List<Cart> addCartOrder(@RequestParam(value = "userId",required = true) Integer userId, @RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList);

    List<Integer> selectGoodsIdByList(@RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList);

    /**
     * 删除购物车所有信息（点击支付按钮）
     */
    int deleteAllCart(@RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList);

}
