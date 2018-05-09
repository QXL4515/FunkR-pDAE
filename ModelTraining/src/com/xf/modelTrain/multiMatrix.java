package com.xf.modelTrain;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xf.util.Util;

public class multiMatrix {
//矩阵相乘方法
	public static Double[][] multiMetrixAandB(Double metrixA[][] , Double metrixB[][]){
		Double result[][] = new Double[metrixA.length][metrixB[0].length];
		int x,i,j=0;
		Double tmp=0.0;
		for(i =0;i<metrixA.length;i++){
			for(j = 0;j<metrixB[0].length;j++){
				for( x=0;x<metrixB.length;x++) {
					tmp += metrixA[i][x] * metrixB[x][j];//矩阵AB中a_ij的值等于矩阵A的i行和矩阵B的j列的乘积之和
				} 
				 result[i][j] = tmp;
				 tmp = 0.0; //中间变量，每次使用后都得清零
			}
		}
		//打印计算后的矩阵
		System.out.println("\n"+"矩阵A*B的值为：");
		for( i=0;i<result.length;i++){	
			for(j=0;j<result[i].length;j++){
				System.out.print(result[i][j] + " ");
			}
			System.out.print("\n");
		}
		return result;
	}
	
	public static Double[][] processData(String filePath) {
		try {
			  System.out.println("获取矩阵数据开始....");
			  InputStream is = new FileInputStream(filePath);
			     XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			     // Read the Sheet
			     for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			         XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			         if (xssfSheet == null) {
			             continue;
			         }
			         Double metrixA[][] = new Double [xssfSheet.getLastRowNum()+1][];//NEW一个数组，保存矩阵A
			         // Read the Row
			         for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			             XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			             if (xssfRow != null) {
			            	 int cellNum = xssfRow.getPhysicalNumberOfCells();
			            	 metrixA[rowNum] = new Double [cellNum];
			            	 for(int j =0;j<cellNum;j++) {
			     				metrixA[rowNum][j] = Double.parseDouble(getValue(xssfRow.getCell(j)));
			     			}
			             }
			         }
			         //打印矩阵
			         System.out.println("从"+filePath+"中获取的矩阵的值为：");
			 		for(int i=0;i<metrixA.length;i++){	
			 			for(int j=0;j<metrixA[i].length;j++){
			 				System.out.print(metrixA[i][j] + " ");
			 			}
			 			System.out.print("\n");
			 		}
			 		return metrixA;
			     }
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
	
	public static void main(String args[]){
		try {
			System.out.println("数据处理开始........");
			Double metrixA[][] = processData("F:\\project_vactor.xlsx");
			Double metrixB[][] = processData("F:\\developer_vactor.xlsx");
			if(metrixA == null || metrixB == null) {
				System.out.println("获取数据失败！");
				return;
			}
			int Arow = metrixA.length;
			int Bcolumn = metrixB[0].length;
			if(Arow != Bcolumn) {
				JOptionPane.showMessageDialog(null,"矩阵A(m*n)和矩阵B(u*v)相乘需要满足 n==u!即A(m*n)-B(n*k)","温馨提示",JOptionPane.INFORMATION_MESSAGE);
			}
			Double result[][]  = multiMetrixAandB(metrixA,metrixB);
			
			File mainFile_excel = new File("f:/multiMatrix.xlsx");
			 OutputStream out = new FileOutputStream(mainFile_excel);
			 Util.exportExcel(result, out);
			 System.out.println("数据处理结束........");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	public static void main1(String args[]){
//		Scanner input = new Scanner(System.in);
//		System.out.print("请输入矩阵A的行数和列数：");
//		int row = input.nextInt();
//		int column = input.nextInt();
//		System.out.println("请输入"+row +"行" + column+"列的矩阵A：");
//		int metrixA[][] = new int [row][column];//NEW一个数组，保存矩阵A
//		for(int i =0;i<row;i++) {
//			for(int j =0;j<column;j++) {
//				metrixA[i][j] = input.nextInt();
//			}
//		}
//		System.out.print("请输入矩阵B的行数和列数：");
//		int rowB = input.nextInt();
//		if(rowB != column) {
//			JOptionPane.showMessageDialog(null,"矩阵A(m*n)和矩阵B(u*v)相乘需要满足 n==u!即A(m*n)-B(n*k)","温馨提示",JOptionPane.INFORMATION_MESSAGE);
//		}
//		int columnB = input.nextInt();
//		System.out.println("请输入"+rowB +"行" + columnB+"列的矩阵B：");
//		int metrixB[][] = new int [rowB][columnB];//NEW一个数组，保存矩阵B
//		for(int i =0;i<rowB;i++) {
//			for(int j =0;j<columnB;j++) {
//				metrixB[i][j] = input.nextInt();
//			}
//		}
//		multiMetrixAandB(metrixA,metrixB);
//	}
}
