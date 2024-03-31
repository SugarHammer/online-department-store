package com.controller;


import com.pojo.Goods;
import com.service.interfaces.IExcelService;
import com.utils.JsonResponse;
import com.utils.PoiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private IExcelService excelService;

    SimpleDateFormat sdf=new SimpleDateFormat("/yyyy/MM/dd/");


    @PostMapping("/import")
    public JsonResponse importData(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        System.out.println("接收到的文件名为: "+file.getName());
        List<Goods> goods=PoiUtils.parseFile2List(file);

        for (Goods good : goods) {
            System.out.println(good);
        }

        //根据上传的信息文件，批量添加数据
        if (excelService.addBatchGoods(goods)==1){
            return new JsonResponse(JsonResponse.OK,null , "批量导入成功！");
        }else {
            return new JsonResponse(JsonResponse.AUTH_ERR,null , "批量导入失败！");
        }
    }


    @GetMapping("/export")
    //ResponseEntity里面装了所有响应的数据
    public ResponseEntity<byte[]> exportExcel() throws IOException {
        return PoiUtils.exportGoodsExcel(excelService.getAllGoodss());
    }


}

