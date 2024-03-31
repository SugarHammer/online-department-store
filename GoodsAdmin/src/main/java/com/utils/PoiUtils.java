package com.utils;

import com.pojo.Goods;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PoiUtils {

    //这是把数据导出到本地保存为Excel文件的方法
    public static ResponseEntity<byte[]> exportGoodsExcel(List<Goods> allGoodss) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();//创建一个Excel文件

        //创建Excel文档属性，必不可少。少了的话，getDocumentSummaryInformation()方法就会返回null
        workbook.createInformationProperties();
        DocumentSummaryInformation info = workbook.getDocumentSummaryInformation();
        info.setCompany("GUET.");//设置公司信息
        info.setManager("zhaoke");//设置管理者
        info.setCategory("商品列表");//设置文件名

        //设置文件中的日期格式
        HSSFCellStyle datecellStyle = workbook.createCellStyle();
        datecellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));//这个文件的日期格式和平时的不一样

        //创建表单
        HSSFSheet sheet = workbook.createSheet("拼夕夕商城-商品信息表");
        HSSFRow r0 = sheet.createRow(0);//创建第一行
        HSSFCell c0 = r0.createCell(0);// 创建列
        HSSFCell c1 = r0.createCell(1);// 创建列
        HSSFCell c2 = r0.createCell(2);// 创建列
        HSSFCell c3 = r0.createCell(3);// 创建列
        HSSFCell c4 = r0.createCell(4);// 创建列

        c0.setCellValue("商品id");
        c1.setCellValue("商品名称");
        c2.setCellValue("商品价格");
        c3.setCellValue("商品图片");
        c4.setCellValue("商品描述");

        for (int i = 0; i < allGoodss.size(); i++) {
            Goods goods=allGoodss.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            HSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(goods.getGid());
            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(goods.getGname());
            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(goods.getGprice());
            HSSFCell cell3 = row.createCell(3);
            cell3.setCellStyle(datecellStyle);//让日期格式数据正确显示
            cell3.setCellValue(goods.getPthumbnail());
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(goods.getGdesc());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",
                new String("商品信息表.xls".getBytes("UTF-8"),"iso-8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        workbook.write(baos);

        ResponseEntity<byte[]> entity = new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.CREATED);

        return entity;
    }

    //这是解析上传的Excel文件为对象集合，从而批量添加数据的方法
    public static List<Goods> parseFile2List(MultipartFile file) throws IOException {
        List<Goods> Goodss=new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();//获取表单所有的行
        for (int i = 1; i < physicalNumberOfRows; i++) {
            XSSFRow row = sheet.getRow(i);
            Goods goods=new Goods();

            XSSFCell c0 = row.getCell(0);
            c0.setCellType(CellType.STRING);
            goods.setGname(c0.getStringCellValue());

            XSSFCell c1 = row.getCell(1);
            c1.setCellType(CellType.STRING);
            goods.setGprice(Double.parseDouble( c1.getStringCellValue() ));

            XSSFCell c2 = row.getCell(2);
            c2.setCellType(CellType.STRING);
            goods.setPthumbnail(c2.getStringCellValue());

            XSSFCell c3 = row.getCell(3);
            c3.setCellType(CellType.STRING);
            goods.setGdesc(c3.getStringCellValue());

            Goodss.add(goods);
        }

        return Goodss;
    }
}
