package com.service.impl;

import com.mapper.Cart_GoodsMapper;
import com.pojo.Cart_Goods;
import com.service.interfaces.Cart_GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Cart_GoodsServiceImpl implements Cart_GoodsService {

    @Autowired
    Cart_GoodsMapper cart_goodsMapper;

    @Override
    public List<Cart_Goods> queryGoodsByUserId(int userId){

       return cart_goodsMapper.queryGoodsByUserId(userId);

    }

    @Override
    public String selectGnameBycartId(int cartId){
        return cart_goodsMapper.selectGnameBycartId(cartId);
    }


}