package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Orders")
public class Orders {

    private int orderId;
    private int userId;
    private String cartList;
    private String orderTime;
    private String orderName;
    private int status;
    private double amount;


}