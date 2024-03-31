package com.mapper;


import com.pojo.Goods;
import com.pojo.GoodsDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GoodsMapper {
    //查找所有货物信息
    List<Goods> selectAllGoods();
    //按id查找货物信息
    Goods selectGoodsById(@Param("gid") int gid);
    //按name查找货物信息
    Goods selectGoodsByName(@Param("gname") String name);
    //添加货物信息
    void addGoods(Goods goods);
    //修改货物信息
    void updateGoods(Goods goods);
    //根据id删除货物数据
    void deleteGoodsById(int gid);
    //根据name删除货物数据
    void deleteGoodsByName(String gname);
    //按keyword搜索货物信息
    List<Goods> searchGoods(@Param("keyword") String keyword);

    //批量添加数据
    Integer addBatchGoods(@Param("goods") List<Goods> goods);
    //根据商品id查询商家
    GoodsDTO selectGoodsByIdWithStore(@Param("gid") int gid);
}


