package com.xf.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Util {

	public static void exportExcel(List<String> headers,List<List<String>> list, OutputStream  os) {  
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
        int count = 0;
        //创建表头  
        for (int i = 0; i < headers.size(); i++) {  
            //创建传入进来的表头的个数  
            cell = row.createCell(i+1);  
            //表头的值就是传入进来的值  
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(cellStyle);
  
        }  
        
        // 得到所有记录 行：列  
        List<String> record = null;  
  
        if (list != null) {  
            //获取所有的记录 有多少条记录就创建多少行  
            for (int i = 0; i < list.size(); i++) {  
                row = xssfSheet.createRow(++count);  
                // 得到所有的行 一个record就代表 一行  
                record = list.get(i);  
                //在有所有的记录基础之上，便利传入进来的表头,再创建N行  
                for (int j = 0; j< record.size();j++) {
                    cell = row.createCell(j);  
                    //把每一行的记录再次添加到表头下面 如果为空就为 "" 否则就为值  
                    cell.setCellValue(record.get(j)==null?"":record.get(j).toString());
                    cell.setCellStyle(cellStyle);
                }  
            }  
        }
        try {  
        	// FileOutputStream fileOutputStreane = new FileOutputStream(file);
            xssfWorkbook.write(os);  
            os.flush();  
            os.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
	
	
	public static void exportExcel2(Set<String> headers,Set<String> lie,Map<String, String> content, OutputStream  os) {  
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
        int count = 0;
        //创建表头 
        int i = 0;
        for( Iterator   it = headers.iterator();  it.hasNext(); )   {   
        	 //创建传入进来的表头的个数  
            cell = row.createCell(i+1);  
            //表头的值就是传入进来的值  
            cell.setCellValue(it.next().toString());
            cell.setCellStyle(cellStyle);
            i++;
        }   
        // 得到所有记录 行：列  
        List<String> record = null;  
  
        if (lie != null) {  
            //获取所有的记录 有多少条记录就创建多少行  
        	for( Iterator   it = lie.iterator();  it.hasNext(); )   {  
        		 row = xssfSheet.createRow(++count); 
        		 int j = 0;
        		 String followId = it.next().toString();
        		 for( Iterator   it2 = headers.iterator();  it2.hasNext(); )   {  
        			 cell = row.createCell(j);  
        			 if(j == 0) {
        				 cell.setCellValue(followId);
        			 }else {
        				 String contentCell = content.get(followId+"_"+it2.next().toString());
            			 if(contentCell == null) {
            				 contentCell = "0";
            			 }
            			 cell.setCellValue(contentCell);
        			 }
        			 cell.setCellStyle(cellStyle);
        			 j++;
        		 }
        	}
        }
        try {  
        	// FileOutputStream fileOutputStreane = new FileOutputStream(file);
            xssfWorkbook.write(os);  
            os.flush();  
            os.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
	public static void exportExcel2(List<String> headers,List<String> lie,Map<String, String> content, OutputStream  os) {  
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
        int count = 0;
        //创建表头 
        int i = 0;
        for( Iterator   it = headers.iterator();  it.hasNext(); )   {   
        	 //创建传入进来的表头的个数  
            cell = row.createCell(i+1);  
            //表头的值就是传入进来的值  
            cell.setCellValue(it.next().toString());
            cell.setCellStyle(cellStyle);
            i++;
        }   
        // 得到所有记录 行：列  
        List<String> record = null;  
  
        if (lie != null) {  
            //获取所有的记录 有多少条记录就创建多少行  
        	for( Iterator   it = lie.iterator();  it.hasNext(); )   {  
        		 row = xssfSheet.createRow(++count); 
        		 int j = 0;
        		 String followId = it.next().toString();
        		 for( Iterator   it2 = headers.iterator();  it2.hasNext(); )   {  
        			 cell = row.createCell(j);  
        			 if(j == 0) {
        				 cell.setCellValue(followId);
        			 }else {
        				 String contentCell = content.get(followId+"_"+it2.next().toString());
            			 if(contentCell == null) {
            				 contentCell = "0";
            			 }
            			 cell.setCellValue(contentCell);
        			 }
        			 cell.setCellStyle(cellStyle);
        			 j++;
        		 }
        	}
        }
        try {  
        	// FileOutputStream fileOutputStreane = new FileOutputStream(file);
            xssfWorkbook.write(os);  
            os.flush();  
            os.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
	 public static Integer[][] getArray2(List<List<Integer>> list){  
		 	Integer[][] ps = new Integer[list.size()][];  
	        for (int i = 0; i < list.size(); i++) {  
	            ps[i] = list.get(i).toArray(new Integer[list.get(i).size()]);  
	        }  
	        return ps;  
	    }  
	 
	 public static Object[][] getObjectArray2(List<List<Object>> list){  
		 Object[][] ps = new Object[list.size()][];  
	        for (int i = 0; i < list.size(); i++) {  
	            ps[i] = list.get(i).toArray(new Object[list.get(i).size()]);  
	        }  
	        return ps;  
	    }  
	 
	 
	 /**
	  * 获取Excel2003-2007版本内容
	  * @author fangcv
	  * @throws IOException
	  */
	 public List<Record> readXls(String path) throws IOException {
	     InputStream is = new FileInputStream(path);
	     HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
	     Record record = null;
	     List<Record> list = new ArrayList<Record>();
	     // Read the Sheet
	     for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
	         HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
	         if (hssfSheet == null) {
	             continue;
	         }
	         // Read the Row
	         for (int rowNum = 3; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
	             HSSFRow hssfRow = hssfSheet.getRow(rowNum);
	             if (hssfRow != null) {
	                 HSSFCell tickNo = hssfRow.getCell(0);
	                 HSSFCell name = hssfRow.getCell(1);
	                 HSSFCell age = hssfRow.getCell(2);
	                 HSSFCell score = hssfRow.getCell(3);
	                 list.add(record);
	             }
	         }
	     }
	     return list;
	 }
	 
	 /**
	  * 获取Excel2010版本内容
	  * @author fangcv
	  * @throws IOException
	  */
	 public List<Record> readXlsx(String path) throws IOException {
	     InputStream is = new FileInputStream(path);
	     XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
	     Record record = null;
	     List<Record> list = new ArrayList<Record>();
	     // Read the Sheet
	     for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
	         XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
	         if (xssfSheet == null) {
	             continue;
	         }
	         // Read the Row
	         for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
	             XSSFRow xssfRow = xssfSheet.getRow(rowNum);
	             if (xssfRow != null) {
	                 XSSFCell tickNo = xssfRow.getCell(0);
	                 XSSFCell name = xssfRow.getCell(1);
	                 XSSFCell age = xssfRow.getCell(2);
	                 XSSFCell score = xssfRow.getCell(3);
	                 list.add(record);
	             }
	         }
	     }
	     return list;
	 }
	 
	 /**
	  * 获取Excel2010版本单元格内容
	  * @author fangcv
	  * @param xssfRow
	  * @return
	  */
	 private String getValue(XSSFCell xssfRow) {
         if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
             return String.valueOf(xssfRow.getBooleanCellValue());
         } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
             return String.valueOf(xssfRow.getNumericCellValue());
         } else {
             return String.valueOf(xssfRow.getStringCellValue());
         }
     }
	 
	 /**
	  * 获取Excel2003-2007版本单元格内容
	  * @author fangcv
	  * @param hssfCell
	  * @return
	  */
	 private String getValue(HSSFCell hssfCell) {
         if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
             return String.valueOf(hssfCell.getBooleanCellValue());
         } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
             return String.valueOf(hssfCell.getNumericCellValue());
         } else {
             return String.valueOf(hssfCell.getStringCellValue());
         }
     }
}
