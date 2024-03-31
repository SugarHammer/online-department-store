package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart_Goods {

    private int gid;             /** 商品ID  */
    private int num;             /** 商品数量  */
    private String gname;        /** 商品名称  */
    private double gprice;       /** 商品价格  */
    private String gdesc;        /** 商品描述 */
    private String pthumbnail;   /** 商品图片  */
    private int cartId;          /** 购物车ID  */

}