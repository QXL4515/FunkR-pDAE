package com.xf.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Util {
	public static void exportExcel(Double[][] content, OutputStream  os) {  
        // 创建工作薄  
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();  
        // 创建工作薄中的工作表  （sheet:一张表的简称  ）
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet0");
        
        //设置样式
        xssfSheet.setDefaultColumnWidth(256 * 2 * 8);//设置缺省列宽
        XSSFCellStyle cellStyle=xssfWorkbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_YELLOW.index);
        //设置指定列的列宽，256 * 50这种写法是因为width参数单位是单个字符的256分之一  
        xssfSheet.setColumnWidth(0, 256 * 2 * 15);//15个汉字
        xssfSheet.setColumnWidth(2, 256 * 2 * 9);
        xssfSheet.setColumnWidth(3, 256 * 2 * 9);
        xssfSheet.setColumnWidth(4, 256 * 2 * 25);
        xssfSheet.setColumnWidth(5, 256 * 2 * 10);
        
        // 创建第一行  （表头）
        XSSFRow row = xssfSheet.createRow(0);  
        // 创建单元格，设置表头 创建列  
        XSSFCell cell = null;  
  
        //获取所有的记录 有多少条记录就创建多少行  
        for (int i = 0; i < content.length; i++) {  
            row = xssfSheet.createRow(i);  
            //在有所有的记录基础之上，便利传入进来的表头,再创建N行  
            for (int j = 0; j< content[i].length;j++) {
                cell = row.createCell(j);  
                //把每一行的记录再次添加到表头下面 如果为空就为 "" 否则就为值  
                cell.setCellValue(content[i][j]);
                cell.setCellStyle(cellStyle);
            }  
        }  
        try {  
            xssfWorkbook.write(os);  
            os.flush();  
            os.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}
