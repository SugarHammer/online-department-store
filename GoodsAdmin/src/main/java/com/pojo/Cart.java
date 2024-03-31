package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;


/**
 * 使用lombok插件自动生成有参、无餐构造
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("cart")
public class Cart {

    private int cartId;         /** 购物车ID  */
    private int userId;         /** 用户ID  */
    private int goodsId;        /** 商品ID  */
    private int num;            /** 商品数量  */
    private double sumprice;    /** 总价（商品数量*商品价格）  */

}