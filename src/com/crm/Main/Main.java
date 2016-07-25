package com.crm.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Main {

	// HSSFSheet sheet =null;
	static String rootPath = "";
	static ExecutorService exec = null;
	static List<String> userpass = null;
	static List<HSSFWorkbook> lists=null;
	static int size=0;
	static LinkedBlockingQueue<String[]> queue = new LinkedBlockingQueue<String[]>(10);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		java.util.Date nowdate = new java.util.Date();
		String myString = "2015-11-30";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date d;
		try {
			d = sdf.parse(myString);
			boolean flag = d.before(nowdate);
			if (flag) {
				System.out.println("警告！！试用期已过………………程序将退出！！！");
				
				System.exit(0);
			} else {

			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String fileName = "script" + File.separator + "keys.xls";
		Main main = new Main();
		String configfile = "config" + File.separator + "config.txt";
		
		String newFileName = "result" + File.separator + "result.xls";

		userpass = readTxtFile(configfile);
		size=userpass.size();
		exec = Executors.newFixedThreadPool(size);
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(new customer(queue,newFileName));
		// while(true){
		int threadCount = ((ThreadPoolExecutor) exec).getActiveCount();
		System.out
				.println("######################################################################################## ");
		System.out
				.println("################# 开始查询，如果想在查询过程中查看结果，#################################");
		System.out
				.println("################# 请查看result\\back.xls  ############################ ");
		System.out
				.println("################# 请注意，不要打开 sript\\keys.xls和result\\result.xls，否则会出错 ############################ ");
		System.out
				.println("######################################################################################## ");
	
		if (threadCount > 0) {
			try {
				// System.out.println("###################################################");
				Thread.sleep(18000);
				// continue;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// exec = Executors.newFixedThreadPool(userpass.size());

		try {
			// File newFileName=generateFile(fileName);
		
			// File oldFile=new File(fileName);
			File newFile = new File(newFileName);
			if (newFile.exists()) {
				// boolean isSuccess= oldFile.renameTo(newFileName);
			
			FileInputStream sd=	new FileInputStream(
						newFileName);
				HSSFWorkbook hwbnn = new HSSFWorkbook(sd);
				if (hwbnn.getNumberOfSheets() > 1) {
					HSSFSheet dfdfd = hwbnn.getSheetAt(1);
					String isEnd = "";
					try {
						isEnd = dfdfd.getRow(0).getCell(0).getStringCellValue();
					} catch (Exception e) {

					}
					if (isEnd.equals("end")) {
						newFile.delete();
						HSSFWorkbook wb = new HSSFWorkbook();
						wb.createSheet("tomiyo");
						wb.createSheet("end");
						FileOutputStream fileOut = new FileOutputStream(
								newFileName);
						wb.write(fileOut);
						wb.close();
						fileOut.close();
					}
				} else {
					hwbnn.createSheet("tomiyo");
					hwbnn.createSheet("end");
					FileOutputStream fileOut = new FileOutputStream(newFileName);
					hwbnn.write(fileOut);
					hwbnn.close();
					fileOut.close();
				}

			} else {
				HSSFWorkbook wb = new HSSFWorkbook();
				wb.createSheet("tomiyo");
				wb.createSheet("end");
				wb.getSheet("tomiyo").setColumnWidth((short)0,(short)9000);   	
				wb.getSheet("tomiyo").setColumnWidth((short)2,(short)1000);   	

				FileOutputStream fileOut = new FileOutputStream(newFileName);
				wb.write(fileOut);
				wb.close();
				fileOut.close();
			}
		
			
			 lists = main.getDocument(fileName, newFileName);
			// List<HSSFWorkbook> lists =main.getDocument2(fileName);
			int positionn = 0;
			try {
				HSSFWorkbook book = lists.get(1);
				if (book.getNumberOfSheets() > 1) {
					HSSFSheet dfd = book.getSheetAt(1);
					HSSFRow r = dfd.getRow(0);
					HSSFCell fd = r.getCell(0);
					String value = fd.getStringCellValue();
					if (null != value) {
						positionn = Integer.parseInt(value);
					}else{
						positionn =0;
					}
				} else {
					positionn = 0;				
					
				}
			} catch (Exception e) {

			}
			Position position = new Position();
			position.setPosition(positionn);
			position.setCur(positionn);
			try{
				// lists = main.getDocument(fileName, newFileName);
			main.updateData(lists.get(0), lists.get(1), fileName, newFileName,
					position);
			}catch(Exception e){
				
			}finally{
				//lists.get(0).close();
				//lists.get(1).close();
			}
			Thread.sleep(3000);

		} catch (Exception e) {
			e.printStackTrace();
		}

		while (true) {
			int threadCount2 = ((ThreadPoolExecutor) exec).getActiveCount();
			if (threadCount2 > 0) {

				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				
				System.out
						.println("######################################################################################## ");
				System.out.println("##################  查询完毕！请 查看  result"
						+ File.separator
						+ "result.xls ############################ ");
				System.out
						.println("######################################################################################## ");

				break;
			}

		}
		// }
		// main.updateData();
	}

	public static File generateFile(String oldFileName) {
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String date = df.format(d);
		String[] files = oldFileName.split("\\.");
		String newFile = files[0] + date + "." + files[1];
		return new File(newFile);
	}

	public static List<String> readTxtFile(String filePath) {
		List<String> accounts = new ArrayList<String>();
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt.length() > 5) {
						System.out.println("###############添加一个测试账号：  "
								+ lineTxt + "####################");
						accounts.add(lineTxt);
					}
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");

			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return accounts;
	}

	public void updateData(HSSFWorkbook oldBook, HSSFWorkbook newBook,
			String fileName, String newFileName, Position position) {

		String urlString = "https://crm.alibaba-inc.com/noah/presale/work/allCustomer.vm";

		try {		
			for (int i = 1; i <= size; i++) {
				String[] userpaaa = userpass.get(i - 1).split(":");
				getAccountsThread instance = new getAccountsThread(urlString,
						oldBook, newBook, false, i, fileName, newFileName,
						userpaaa[0], userpaaa[1], position,queue);
				exec.execute(instance);
				Thread.sleep(5000);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (exec != null) {
				exec.shutdown();
			}
		}

	}

	public List<HSSFWorkbook> getDocument(String fileName, String newFileName) {
		HSSFWorkbook hwb = null;
		HSSFWorkbook newHwb = null;
		File file = new File(fileName);
		File newFile = new File(newFileName);
	
		List<HSSFWorkbook> lists = new ArrayList<HSSFWorkbook>();
		InputStream is;
		InputStream newIs;
		try {
			
			is = new FileInputStream(file);
			newIs = new FileInputStream(newFile);

			hwb = new HSSFWorkbook(is);

			newHwb = new HSSFWorkbook(newIs);
			// sheet = hwb.getSheetAt(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// int rowsNumber = sheet.getLastRowNum();
		lists.add(hwb);
		lists.add(newHwb);
		return lists;
	}



}
