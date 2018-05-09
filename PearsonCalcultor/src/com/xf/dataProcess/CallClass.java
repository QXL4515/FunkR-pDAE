package com.xf.dataProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xf.util.DenominatorCalculate;
import com.xf.util.NumeratorCalculate;
import com.xf.util.Util;

/**
 * 计算开发者相关系数
 * @author xf
 *
 */
public class CallClass {
	
	public static void ClassCall(){
		double CORR = 0.0;
		List<String> xList = new ArrayList<String>();;
		List<String> yList = new ArrayList<String>();
		
		try {
			  System.out.println("获取数据开始.......");
			  InputStream is = new FileInputStream("F:\\dset_2.xlsx");
			     XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			     // Read the Sheet
			     for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			         XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			         if (xssfSheet == null) {
			             continue;
			         }
			         Map<String,String> rowContent = new HashMap<String,String>();
			         List<String> followId = new ArrayList<String>();
			         // Read the Row
			         for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			             XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			             if (xssfRow != null) {
			            	 int cellNum = xssfRow.getPhysicalNumberOfCells();
			            	 String str="";
			            	 for (int i = 1; i < cellNum; i++) {
			            		 str += xssfRow.getCell(i)+",";
							 }
			            	 str = str.substring(0,str.lastIndexOf(","));
			            	 rowContent.put(getValue(xssfRow.getCell(0)),str);
			            	 followId.add(getValue(xssfRow.getCell(0)));
			             }
			         }
			         Map<String, String> outMap = new HashMap<String, String>();
			         for (int i = 0; i < followId.size(); i++) {
			        	 if(i == followId.size()-1) {
			        		 break;
			        	 }
			        	 String[] xStr = rowContent.get(followId.get(i)).split(",");
			        	 for (int j = 0; j < xStr.length; j++) {
			        		 xList.add(xStr[j]);
						 }
			        	 for (int j = i+1; j < followId.size(); j++) {
			        		 String[] yStr = rowContent.get(followId.get(j)).split(",");
				        	 for (int m = 0; m < yStr.length; m++) {
				        		 yList.add(yStr[m]);
							 }
				        	 NumeratorCalculate nc = new NumeratorCalculate(xList,yList);
					 		 double numerator = nc.calcuteNumerator();
					 		 DenominatorCalculate dc = new DenominatorCalculate();
					 		 double denominator = dc.calculateDenominator(xList, yList);
					 		 CORR = numerator/denominator;
					 		 outMap.put(followId.get(i)+"_"+followId.get(j), CORR+"");
						 }
					 }
			         File mainFile_excel = new File("f:/dset_3.xlsx");
					 OutputStream out = new FileOutputStream(mainFile_excel);
					 Util.exportExcel2(followId,followId,outMap, out);
					 System.out.println("数据处理结束........");
			     }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 private static String getValue(XSSFCell xssfRow) {
	      if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
	          return String.valueOf(xssfRow.getBooleanCellValue());
	      } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
	          return String.valueOf(xssfRow.getNumericCellValue());
	      } else {
	          return String.valueOf(xssfRow.getStringCellValue());
	      }
	  }
	 
	 public static void main(String[] str) {
		 ClassCall();
	 }
}
