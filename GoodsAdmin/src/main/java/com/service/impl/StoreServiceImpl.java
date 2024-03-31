package com.service.impl;

import com.mapper.StoreMapper;
import com.pojo.Store;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements com.service.interfaces.IStoreService{

    @Autowired
    private StoreMapper storeMapper;

    private static Logger log = Logger.getLogger(StoreServiceImpl.class);

    @Override
    public List<Store> selectAllStore() {
        log.info("/selectAllStore start...");
        return storeMapper.selectAllStore();
    }

    @Override
    public void addStore(Store store) {
        storeMapper.addStore(store);
        return;
    }

    //按id查找商家信息
    @Override
    public Store selectStoreById(int id) {

        return storeMapper.selectStoreById(id);
    }

    //按name查找商家信息
    @Override
    public Store selectStoreByName(String name) {

        return storeMapper.selectStoreByName(name);
    }

    //修改商家信息
    @Override
    public void updateStore(Store store) {
        storeMapper.updateStore(store);
        return;
    }

    //根据id删除商家数据
    @Override
    public void deleteStoreById( int id) {
        storeMapper.deleteStoreById(id);
        return;
    }

    //根据name删除商家数据
    @Override
    public void deleteStoreByName( String name) {
        storeMapper.deleteStoreByName(name);
        return;
    }

    @Override
    public List<Store> searchStore(String keyword) {
        return storeMapper.searchStore(keyword);
    }


}

