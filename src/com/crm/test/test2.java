package com.crm.test;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test2 tt=new test2();
//		tt.add("result"+File.separator+"汕头公司",new BigInteger("440500000143390"));
//		tt.add("result"+File.separator+"潮阳",new BigInteger("440582600203541"));
//		tt.add("result"+File.separator+"金平",new BigInteger("440508600220440"));
//		tt.add("result"+File.separator+"龙湖",new BigInteger("440507600196000"));
//
//		tt.add("result"+File.separator+"澄海",new BigInteger("440583600353952"));
//
//		tt.add("result"+File.separator+"濠江",new BigInteger("440506600051508"));
//
//		tt.add("result"+File.separator+"揭阳",new BigInteger("445200000041852"));
//
//		tt.add("result"+File.separator+"榕城",new BigInteger("445202600151095"));
//
//		
//		tt.add("result"+File.separator+"蓝城",new BigInteger("445203600104006"));
//		tt.add("result"+File.separator+"空港",new BigInteger("445211600021005"));
//		tt.add("result"+File.separator+"揭东区",new BigInteger("445221600161000"));

	}

	   public void add2(String fileName,BigInteger  number){
	        try{

               int[][] juzhen = new int[10][10];
	         FileWriter fileWriter=new FileWriter(fileName);
	                   for (int i = 0; i < 300000; i++) {
	            fileWriter.write(number.add(new BigInteger(i+""))+"\r\n");
	             }
	           fileWriter.flush();
	           fileWriter.close();
	        }catch(Exception e){
	            e.printStackTrace();
	        }

	    }
	
	public void add(String fileName,BigInteger  number){
		try{
		 FileWriter fileWriter=new FileWriter(fileName);
		  		   for (int i = 0; i < 300000; i++) {
		    fileWriter.write(number.add(new BigInteger(i+""))+"\r\n");
		     }
		   fileWriter.flush();
		   fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
