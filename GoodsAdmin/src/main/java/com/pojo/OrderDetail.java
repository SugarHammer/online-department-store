package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("OrderDetail")
public class OrderDetail {
    public Integer cartId;
    public Integer goodsId;
    public String goodsName;
    public Integer nums;
    public double dealPrice;
    public String imgSrc;
    public double goodsPrice;
}
