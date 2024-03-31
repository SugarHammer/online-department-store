package com.controller;

import com.pojo.Advert;
import com.service.interfaces.IAdvertService;
import com.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private IAdvertService advertService;

    //查询所有广告列表
    @GetMapping("/selectAllAdvert")
    public JsonResponse selectAllAdvert(){
        return new JsonResponse(JsonResponse.OK, advertService.selectAllAdvert(), null);
    }

    //按id查找广告信息
    @GetMapping("/selectAdvertById")
    public JsonResponse selectAdvertById(int id){
        return new JsonResponse(JsonResponse.OK, advertService.selectAdvertById(id), null);
    }

    //按name查找广告信息
    @GetMapping("/selectAdvertByName")
    public JsonResponse selectAdvertByName(String adContent){
        return new JsonResponse(JsonResponse.OK, advertService.selectAdvertByName(adContent), null);
    }

    //添加广告
    @GetMapping("/addAdvert")
    public JsonResponse addAdvert(Integer aid,String adContent,String adPicUrl){
        Advert advert = new Advert(0,adContent,adPicUrl);
        advertService.addAdvert(advert);
        //返回添加好的数据
        return new JsonResponse(JsonResponse.OK, advertService.selectAdvertById(aid), null);
    }

    //修改广告信息
    @GetMapping("/updateAdvert")
    public void updateAdvert(Integer aid,String adContent,String adPicUrl){
        Advert advert = new Advert(aid,adContent,adPicUrl);
        advertService.updateAdvert(advert);
        //返回添加好的数据

    }

    //根据id删除广告数据
    @GetMapping("/deleteAdvertById")
    public void deleteAdvertById(int aid){
        advertService.deleteAdvertById(aid);

    }

    //根据name删除广告数据
    @GetMapping("/deleteAdvertByName")
    public JsonResponse deleteAdvertByName(String adContent){
        advertService.deleteAdvertByName(adContent);
        //返回添加好的数据
        System.out.println("ok");
        return new JsonResponse(JsonResponse.OK, null, null);
    }

    //按keyword查询广告列表
    @GetMapping("/searchAdvert")
    public JsonResponse searchGoods(String keyword){
        return new JsonResponse(JsonResponse.OK, advertService.searchAdvert(keyword), null);
    }

}

