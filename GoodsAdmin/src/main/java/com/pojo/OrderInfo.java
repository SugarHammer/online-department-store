package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("OrderInfo")
public class OrderInfo {

    public Integer orderId;
    public String orderTime;
    public List<OrderDetail> odList;

}