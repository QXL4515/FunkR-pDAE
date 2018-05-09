package com.xf.dataProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xf.util.Util;

/**
 * 开发者数据处理
 * @author xf
 *
 */
public class DeveloperDataProcess {

	public static void processData() {
		try {
			  System.out.println("数据处理开始.......");
			  InputStream is = new FileInputStream("F:\\d1.xlsx");
			     XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			     // Read the Sheet
			     for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			         XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			         if (xssfSheet == null) {
			             continue;
			         }
			         Map<String, String> map = new HashMap<String, String>();
			         
			         Set<String>  reposet=new HashSet<String>();
			         Set<String> followSet =new HashSet<String>();
			         // Read the Row
			         for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			             XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			             if (xssfRow != null) {
			                 XSSFCell followId = xssfRow.getCell(0);
			                 XSSFCell repo_id = xssfRow.getCell(1);
			                 XSSFCell rating = xssfRow.getCell(2);
			                 followSet.add(getValue(followId));
			                 reposet.add(getValue(repo_id));
			                 map.put(getValue(followId)+"_"+repo_id, "1");
			             }
			         }
			         File mainFile_excel = new File("f:/dset_2.xlsx");
					 OutputStream out = new FileOutputStream(mainFile_excel);
					 Util.exportExcel2(reposet,followSet,map, out);
					 System.out.println("数据处理结束........");
			     }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 /**
	  * 获取Excel2010版本单元格内容
	  * @author xf
	  * @param xssfRow
	  * @return
	  */
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
		 processData();
	 }
}
