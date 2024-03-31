package com.mapper;

import com.pojo.Advert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdvertMapper {
    //查找所有广告信息
    List<Advert> selectAllAdvert();
    //按id查找广告信息
    Advert selectAdvertById(@Param("aId") int aId);
    //按name查找广告信息
    Advert selectAdvertByName(@Param("adContent") String name);
    //添加广告信息
    void addAdvert(Advert advert);
    //修改广告信息
    void updateAdvert(Advert advert);
    //根据id删除广告数据
    void deleteAdvertById(int aId);
    //根据name删除广告数据
    void deleteAdvertByName(String adContent);
    //按keyword搜索广告信息
    List<Advert> searchAdvert(@Param("keyword") String keyword);
}


