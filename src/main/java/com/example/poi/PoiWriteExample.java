package com.example.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;

public class PoiWriteExample {
    public static void main(String[] args) {

        FileOutputStream out = null;

        try {
            //ワークブックの生成
            HSSFWorkbook workbook = new HSSFWorkbook();

            //シートの生成
            HSSFSheet sheet1 = workbook.createSheet("シート１");

            //行の生成
            HSSFRow row1 = sheet1.createRow(0);    //１行目
            HSSFRow row2 = sheet1.createRow(1);    //２行目

            //セルの生成
            HSSFCell cell_a1 = row1.createCell(0);    //A1セル
            HSSFCell cell_b1 = row1.createCell(1);    //B1セル
            HSSFCell cell_a2 = row2.createCell(0);    //A2セル
            HSSFCell cell_b2 = row2.createCell(1);    //B2セル

            //セルに値をセット
            cell_a1.setCellValue("A列1行");
            cell_b1.setCellValue("B列1行");
            cell_a2.setCellValue("A列2行");
            cell_b2.setCellValue("B列2行");


            //Excelを生成する
            System.out.println("出力-Start");

            out = new FileOutputStream("C:￥￥sample.xls");
            workbook.write(out);
            out.close();

            System.out.println("出力-End");

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
