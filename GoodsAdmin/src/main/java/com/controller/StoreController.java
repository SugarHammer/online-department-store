package com.controller;

import com.pojo.Store;
import com.service.interfaces.IStoreService;
import com.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private IStoreService storeService;

    //查询所有商家列表
    @GetMapping("/selectAllStore")
    public JsonResponse selectAllStore(){
        return new JsonResponse(JsonResponse.OK, storeService.selectAllStore(), null);
    }

    //按id查找商家信息
    @GetMapping("/selectStoreById")
    public JsonResponse selectStoreById(int id){
        return new JsonResponse(JsonResponse.OK, storeService.selectStoreById(id), null);
    }

    //按name查找商家信息
    @GetMapping("/selectStoreByName")
    public JsonResponse selectStoreByName(String name){
        return new JsonResponse(JsonResponse.OK, storeService.selectStoreByName(name), null);
    }

    //添加商家
    @GetMapping("/addStore")
    public JsonResponse addStore(Integer id,String name,String descp,String url, Integer byGoods){

        Store store = new Store(0,name,descp,url, byGoods);
        System.out.println("商家: "+store);
        storeService.addStore(store);
        //返回添加好的数据
        return new JsonResponse(JsonResponse.OK, storeService.selectStoreByName(name), null);
    }

    //修改商家信息
    @GetMapping("/updateStore")
    public void updateStore(Integer id,String name,String descp,String url, Integer byGoods){
        System.out.println("res:"+id+name+descp+url);
        Store store = new Store(id,name,descp,url, byGoods);
        System.out.println("商家："+store);
        storeService.updateStore(store);
        //返回添加好的数据

    }

    //根据id删除商家数据
    @GetMapping("/deleteStoreById")
    public void deleteStoreById(int id){
        System.out.println("删除id为:"+id);
        storeService.deleteStoreById(id);

    }

    //根据name删除商家数据
    @GetMapping("/deleteStoreByName")
    public JsonResponse deleteStoreByName(String name){
        storeService.deleteStoreByName(name);
        //返回添加好的数据
        System.out.println("ok");
        return new JsonResponse(JsonResponse.OK, null, null);
    }

    //按keyword查询商家列表
    @GetMapping("/searchStore")
    public JsonResponse searchStore(String keyword){
        System.out.println("关键字为:" +keyword);
        return new JsonResponse(JsonResponse.OK, storeService.searchStore(keyword), null);
    }

}

