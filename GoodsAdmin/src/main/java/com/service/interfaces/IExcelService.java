package com.service.interfaces;

import com.pojo.Goods;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IExcelService {
    //导入数据
    public Integer addBatchGoods(List<Goods> goods);
    //导出数据
    public ResponseEntity<byte[]> exportExcel() throws IOException;
    //查询所有商品列表
    public List<Goods> getAllGoodss();

}
