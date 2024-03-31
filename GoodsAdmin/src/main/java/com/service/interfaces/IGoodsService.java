package com.service.interfaces;

import com.pojo.Goods;
import com.pojo.GoodsDTO;

import java.util.List;

public interface IGoodsService {
    //查询所有商品列表
    List<Goods> selectAllGoods();
    //添加货物
    public void addGoods(Goods goods);
    //按id查找货物信息
    public Goods selectGoodsById(int id);

    //按name查找货物信息
    public Goods selectGoodsByName(String name);

    //修改货物信息
    public void updateGoods(Goods goods);

    //根据id删除货物数据
    public void deleteGoodsById( int gid);

    //根据name删除货物数据
    public void deleteGoodsByName( String gname);

    //按keyword搜索货物信息
    public List<Goods> searchGoods(String keyword);

    //根据商品id查询商家
    GoodsDTO selectGoodsByIdWithStore(int id);


}
