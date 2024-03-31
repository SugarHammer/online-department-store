package com.service.impl;

import com.mapper.GoodsMapper;
import com.pojo.Goods;
import com.utils.PoiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ExcelServiceImpl implements com.service.interfaces.IExcelService{

    @Autowired
    GoodsMapper goodsMapper;

    //导入数据
    @Transactional
    public Integer addBatchGoods(List<Goods> goods) {
//        for (Goods item : goods) {
//            item.setCreatedate(new Date());
//        }
        return goodsMapper.addBatchGoods(goods);
    }

    //导出数据
    @GetMapping("/export")
    //ResponseEntity里面装了所有响应的数据
    public ResponseEntity<byte[]> exportExcel() throws IOException {
        return PoiUtils.exportGoodsExcel(this.getAllGoodss());
    }


    public List<Goods> getAllGoodss() {
        return goodsMapper.selectAllGoods();
    }


}

