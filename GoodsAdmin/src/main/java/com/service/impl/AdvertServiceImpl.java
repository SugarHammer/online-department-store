package com.service.impl;

import com.mapper.AdvertMapper;
import com.pojo.Advert;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertServiceImpl implements com.service.interfaces.IAdvertService{

    @Autowired
    private AdvertMapper advertMapper;

    private static Logger log = Logger.getLogger(AdvertServiceImpl.class);

    @Override
    public List<Advert> selectAllAdvert() {
        log.info("/selectAllAdvert start...");
        return advertMapper.selectAllAdvert();
    }

    @Override
    public void addAdvert(Advert advert) {
        advertMapper.addAdvert(advert);
        return;
    }

    //按id查找货物信息
    @Override
    public Advert selectAdvertById(int id) {

        return advertMapper.selectAdvertById(id);
    }

    //按name查找货物信息
    @Override
    public Advert selectAdvertByName(String adContent) {

        return advertMapper.selectAdvertByName(adContent);
    }

    //修改货物信息
    @Override
    public void updateAdvert(Advert advert) {
        advertMapper.updateAdvert(advert);
        return;
    }

    //根据id删除货物数据
    @Override
    public void deleteAdvertById( int aId) {
        advertMapper.deleteAdvertById(aId);
        return;
    }

    //根据name删除货物数据
    @Override
    public void deleteAdvertByName( String adContent) {
        advertMapper.deleteAdvertByName(adContent);
        return;
    }

    @Override
    //按keyword搜索货物信息
    public List<Advert> searchAdvert(String keyword){
        return  advertMapper.searchAdvert(keyword);
    }

}

