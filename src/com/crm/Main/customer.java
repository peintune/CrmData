package com.crm.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class customer  implements Runnable {
	 private BlockingQueue<String[]> queue;
	 String fileName="";
	public customer(BlockingQueue<String[]> queue,String fileName) {
		this.queue = queue;
		this.fileName=fileName;
	}
	@Override
	 public void run() {
	  try {
		  while(true){
	   String[] data = queue.take();
	   
		FileInputStream sd=	new FileInputStream(
				fileName);
		HSSFWorkbook hwbnn = new HSSFWorkbook(sd);
		HSSFSheet sheet = hwbnn.getSheet("tomiyo");
		HSSFSheet sheet2 = hwbnn.getSheet("end");
		int lastRow=sheet.getLastRowNum();
		HSSFRow row=null;
		String content=data[0];
		String sss=data[1];
		String position=data[2];
		String pp="";
		try{
			pp=sheet2.getRow(0).getCell(0).getStringCellValue();
		}catch(Exception e){
			
		}
		if(null==pp||pp==""){
			sheet2.createRow(0).createCell(0).setCellValue(position);
		}else{
			sheet2.getRow(0).getCell(0).setCellValue(position);
		}
		 row = sheet.createRow(lastRow+1);
		 String[] contents=null;
	    	if(content.contains(":")){
	    		contents=content.split(":");
	    	}
		 
	    	HSSFCell cell=null;
	    	HSSFCell cell2=null;
	    	HSSFCell cell3=null;
	    	
	    	if(null!=contents){
	            cell = row.createCell(0);
	            cell.setCellValue(contents[0].toString());
	            cell2 = row.createCell(1);
	            cell2.setCellValue(contents[1].toString());
	            cell3 = row.createCell(2);
	            cell3.setCellValue(sss);
	       	}else{
	               cell = row.createCell(0); 
	               cell.setCellValue(content);
	               cell3 = row.createCell(2);
	               cell3.setCellValue(sss);
	       	}
	    	
	    	FileOutputStream out = new FileOutputStream(fileName);   
	    	hwbnn.write(out);
	    
	    	out.close();
	    	hwbnn.close();
	   
	    	int random=(int)((Math.random())*40);
	    	if(random==5){
	    		FileInputStream	  fileInputStream=null;
	    		FileOutputStream	fileOutputStream=null;
	    	
	    		try{
	    		File f=new File(fileName);
	    			  fileInputStream = new FileInputStream(f);
	    		File newff=new File("result"+File.separator+"back.xls");
	    		if(newff.exists()){
	    			newff.delete();
	    		}
	    			fileOutputStream = new FileOutputStream(new File("result"+File.separator+"back.xls"));
	    		
	    		 byte[] bufferArray = new byte[1024*1024]; 
	             int length; 
	             while ((length = fileInputStream.read(bufferArray)) != -1) { 
	              fileOutputStream.write(bufferArray, 0, length); 
	             }
	    		 } catch (IOException e) {
	    	            e.printStackTrace();  
	    	        } finally {
	    	         try {
	    	          if(fileInputStream != null){
	    	           fileInputStream.close();
	    	          }
	    	          if(fileOutputStream != null){
	    	           fileOutputStream.close();
	    	          }
	    	   } catch (IOException e) {
	    	    e.printStackTrace();
	    	   }
	    	}
	    		
		  }
	  	 
		  }
	  }catch(Exception e){}
	}
}

