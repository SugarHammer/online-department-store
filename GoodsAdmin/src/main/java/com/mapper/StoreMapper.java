package com.mapper;

import com.pojo.Store;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StoreMapper {
    //查找所有商家信息
    List<Store> selectAllStore();
    //按id查找商家信息
    Store selectStoreById(@Param("id") int id);
    //按name查找商家信息
    Store selectStoreByName(@Param("name") String name);
    //添加商家信息
    void addStore(Store store);
    //修改商家信息
    void updateStore(Store store);
    //根据id删除商家数据
    void deleteStoreById(int id);
    //根据name删除商家数据
    void deleteStoreByName(String name);
    //按desc搜索商家信息
    List<Store> searchStore(@Param("keyword") String keyword);
}


