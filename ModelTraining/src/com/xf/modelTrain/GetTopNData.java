package com.xf.modelTrain;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GetTopNData {

	public static List<Double> getTopN(String index, int getN) {
		 Map<String, List<Double>> map = new HashMap<String, List<Double>>();
		try {
			  System.out.println("数据处理开始.......");
			  InputStream is = new FileInputStream("f:/multiMatrix.xlsx");
			     XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			     // Read the Sheet
			     for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			         XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			         if (xssfSheet == null) {
			             continue;
			         }
			         
			         // Read the Row
			         for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			             XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			             if (xssfRow != null) {
			            	 int cellNum = xssfRow.getPhysicalNumberOfCells();
			                List<Double> doubleList = new ArrayList<Double>();
			                for (int i = 0; i < cellNum; i++) {
			                	 doubleList.add(Double.parseDouble(getValue(xssfRow.getCell(i))));
							}
			                Collections.sort(doubleList, new Comparator<Double>() {  
			                	  
			                    @Override  
			                    public int compare(Double o1, Double o2) {  
			                        if(o1>o2) {
			                        	return -1;
			                        }else if(o1<o2) {
			                        	return  1;
			                        }else {
			                        	return 0;
			                        }
			                    }  
			                });  
			                map.put(rowNum+"", doubleList);
			             }
			         }
			     }
			if(getN >  map.size()) {
				return null;
			}
			if(!map.containsKey(index)) {
				return null;
			}
			List<Double> allTopNList = map.get(index+"");
			List<Double> subTopNList = allTopNList.subList(0, getN);
			return subTopNList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		 Scanner input = new Scanner(System.in);
		 System.out.print("请输入开发者id:");
		 String row = input.nextLine();
		 System.out.print("请输入项目推荐个数:");
		 int topN = input.nextInt();
		 List<Double> topNList = getTopN(row, topN);
		 if(topNList == null) {
			 System.out.println("数据获取失败！");
			 return;
		 }
		 System.out.println("获得TopN推荐评分如下:");
		 for (int i = 0; i < topNList.size(); i++) {
			System.out.println(topNList.get(i));
		}
	 }
}
