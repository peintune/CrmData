package com.crm.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String fileName="script"+File.separator+"tomiyo.xls";
		for(int i=0;i<200;i++){
    	int randome=(int)((Math.random())*3);
    	System.out.println(randome);
		}

//		
//		HSSFWorkbook wb = new HSSFWorkbook();
//    	wb.createSheet("tomiyo");
//    	FileOutputStream fileOut;
//		try {
//			fileOut = new FileOutputStream(fileName);
//	    	wb.write(fileOut);  
//	    	
//	    	wb.close();
//	    	fileOut.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   

	}

}
