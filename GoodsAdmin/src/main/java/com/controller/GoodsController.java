package com.controller;


import com.pojo.Goods;
import com.service.interfaces.IGoodsService;
import com.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    //查询所有商品列表
    @GetMapping("/selectAllGoods")
    public JsonResponse selectAllGoods(){
        return new JsonResponse(JsonResponse.OK, goodsService.selectAllGoods(), null);
    }

    //按id查找货物信息
    @GetMapping("/selectGoodsById")
    public JsonResponse selectGoodsById(int id){
        return new JsonResponse(JsonResponse.OK, goodsService.selectGoodsById(id), null);
    }

    //按name查找货物信息
    @GetMapping("/selectGoodsByName")
    public JsonResponse selectGoodsByName(String name){
        return new JsonResponse(JsonResponse.OK, goodsService.selectGoodsByName(name), null);
    }

    //添加货物
    @GetMapping("/addGoods")
    public JsonResponse addGoods(String name, double price, String desc,String img){
        Goods goods = new Goods(0,name,price,desc,img);
        goodsService.addGoods(goods);
        //返回添加好的数据
        return new JsonResponse(JsonResponse.OK, goodsService.selectGoodsByName(name), null);
    }

    //修改货物信息
    @GetMapping("/updateGoods")
    public JsonResponse updateGoods(int id,String name, double price, String desc,String img){
        Goods goods = new Goods(id,name,price,desc,img);
        goodsService.updateGoods(goods);
        //返回添加好的数据
        return new JsonResponse(JsonResponse.OK, goodsService.selectGoodsByName(name), null);
    }

    //根据id删除货物数据
    @GetMapping("/deleteGoodsById")
    public JsonResponse deleteGoodsById(int id){
        goodsService.deleteGoodsById(id);
        //返回添加好的数据
        return new JsonResponse(JsonResponse.OK, null, null);
    }

    //根据name删除货物数据
    @GetMapping("/deleteGoodsByName")
    public JsonResponse deleteGoodsByName(String name){
        goodsService.deleteGoodsByName(name);
        //返回添加好的数据
        return new JsonResponse(JsonResponse.OK, null, null);
    }

    //按keyword查询商品列表
    @GetMapping("/searchGoods")
    public JsonResponse searchGoods(String keyword){
        return new JsonResponse(JsonResponse.OK, goodsService.searchGoods(keyword), null);
    }

    //根据商品id查询商家
    @GetMapping("/selectGoodsByIdWithStore")
    public JsonResponse selectGoodsByIdWithStore(int id) {
        return new JsonResponse(JsonResponse.OK, goodsService.selectGoodsByIdWithStore(id),null);
    }

}
