package com.service.interfaces;

import com.pojo.Cart;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CartService {


    List<Cart> selectAllCart();

    int addCart(int userId,int goodsId,int num,double sumprice);

    int deleteCart(int cartId);

    Cart selectCartById(int cartId);

    int updateCart(int cartId,int userId,int goodsId,int num,double sumprice);

    /** 通过购物车ID查询购物车信息 */
    List<Cart> queryCartById(Integer cartId);

    /** 实时修改总价格  */
    int updateCartprice(int cartId,int num,double sumprice);

    /** 点击“加入购物车” */
    int toShoppingCart(int goodsId,int num,int userId,double sumprice);

//    /** 通过用户ID查询购物车信息 */
//    List<Cart> queryCartByUserId(Integer userId);

    /** 从购物车删除商品 */
    int deleteGoods(int userId,int cartId);

    /** 生成订单，返回订单数据 */
    List<Cart> addCartOrder(@RequestParam(value = "userId",required = true) Integer userId, @RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList);

    List<Integer> selectGoodsIdByList(@RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList);

    /** 删除所有购物车（点击支付） */
    int deleteAllCart(@RequestParam(value = "cartIdList",required = true) List<Integer> cartIdList);

}
