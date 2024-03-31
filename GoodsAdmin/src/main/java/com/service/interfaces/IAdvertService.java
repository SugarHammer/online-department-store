package com.service.interfaces;

import com.pojo.Advert;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface IAdvertService {

    //查询所有商品列表
    List<Advert> selectAllAdvert();
    //添加货物
    public void addAdvert(Advert advert);
    //按id查找货物信息
    public Advert selectAdvertById(int id);

    //按name查找货物信息
    public Advert selectAdvertByName(String adContent);

    //修改货物信息
    public void updateAdvert(Advert advert);

    //根据id删除货物数据
    public void deleteAdvertById( int aId);

    //根据name删除货物数据
    public void deleteAdvertByName( String adContent);

    //按keyword搜索货物信息
    public List<Advert> searchAdvert(String keyword);
}
