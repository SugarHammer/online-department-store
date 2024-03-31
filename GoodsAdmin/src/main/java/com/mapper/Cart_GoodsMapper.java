package com.mapper;

import com.pojo.Cart_Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface Cart_GoodsMapper {

    /**
     * 根据userId查询购物车信息
     */
    List<Cart_Goods> queryGoodsByUserId(int userId);

    /**
     * 根据cartId查询商品名称
     */
    String selectGnameBycartId(int cartId);
}
