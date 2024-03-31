package com.service.interfaces;

import com.pojo.Store;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface IStoreService {

    //查询所有商家列表
    List<Store> selectAllStore();
    //添加商家
    public void addStore(Store store);
    //按id查找商家信息
    public Store selectStoreById(int id);

    //按name查找商家信息
    public Store selectStoreByName(String name);

    //修改商家信息
    public void updateStore(Store store);

    //根据id删除商家数据
    public void deleteStoreById( int id);

    //根据name删除商家数据
    public void deleteStoreByName( String name);

    //按descp搜索商家信息
    public List<Store> searchStore(String s);
}
