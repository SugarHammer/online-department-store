package com.service.impl;


import com.mapper.GoodsMapper;
import com.pojo.Goods;
import com.pojo.GoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements com.service.interfaces.IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    private static Logger log = Logger.getLogger(GoodsServiceImpl.class);

    @Override
    public List<Goods> selectAllGoods() {
        log.info("/selectAllGoods start...");
        return goodsMapper.selectAllGoods();
    }

    @Override
    public void addGoods(Goods goods) {
        goodsMapper.addGoods(goods);
        return;
    }

    //按id查找货物信息
    @Override
    public Goods selectGoodsById(int id) {

        return goodsMapper.selectGoodsById(id);
    }

    //按name查找货物信息
    @Override
    public Goods selectGoodsByName(String name) {

        return goodsMapper.selectGoodsByName(name);
    }

    //修改货物信息
    @Override
    public void updateGoods(Goods goods) {
        goodsMapper.updateGoods(goods);
        return;
    }

    //根据id删除货物数据
    @Override
    public void deleteGoodsById( int gid) {
        goodsMapper.deleteGoodsById(gid);
        return;
    }

    //根据name删除货物数据
    @Override
    public void deleteGoodsByName( String gname) {
        goodsMapper.deleteGoodsByName(gname);
        return;
    }

    //按keyword搜索货物信息
    @Override
    public List<Goods> searchGoods(String keyword){
        return  goodsMapper.searchGoods(keyword);
    }

    //根据商品id查询商家
    @Override
    public GoodsDTO selectGoodsByIdWithStore(int id) {
        return goodsMapper.selectGoodsByIdWithStore(id);
    }

}
