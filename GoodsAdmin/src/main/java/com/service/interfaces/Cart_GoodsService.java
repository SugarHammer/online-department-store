package com.service.interfaces;

import com.pojo.Cart_Goods;

import java.util.List;

public interface Cart_GoodsService {

    List<Cart_Goods> queryGoodsByUserId(int userId);

    String  selectGnameBycartId(int cartId);
}
