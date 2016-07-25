package com.crm.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.bcel.generic.DDIV;
import org.apache.commons.collections.bag.SynchronizedBag;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;


class getAccountsThread extends Thread{
	String urlString="";
	HSSFWorkbook oldBook=null;
	List<String> listsList=null;
	boolean isLast=false;
	WebClient wc=null;
	WebDriver driver =null;
	int threadId=1;
	String str1="没有查询到符合条件的客户。";
	String str2="通过联系人查询客户信息，返回数据超过100，请缩小查询范";
	String fileName="";
	String newFileName="";
	HSSFWorkbook newBook=null;
	String username="";
	String password="";
	int threadCount=1;
	Position position=null;

	BlockingQueue<String[]> queue;
	public  getAccountsThread (String url,HSSFWorkbook oldBook,HSSFWorkbook newBook,boolean isLast,int threadId,String fileName,String newFileName,String username,String password,Position position
			,BlockingQueue<String[]> queue){
		this.urlString=url;
		this.fileName=fileName;
		this.oldBook=oldBook;
		this.isLast=isLast;
		this.threadId=threadId;
		this.username=username;
		this.password=password;
		this.position=position;
		this.newBook=newBook;
		this.newFileName=newFileName;
		this.queue=queue;

		this.getChromeWebClient();

	}
    public void run(){
    	
    	try {
    		//getData();
    		search();
		} catch (Exception e) {
			e.printStackTrace();
		}
        }
    
    public void getWebClient(){
    	try{
    	wc=new WebClient(BrowserVersion.CHROME);
    	System.setProperty("webdriver.chrome.driver", "libs\\chromedriver.exe");
    	WebDriver driver = new ChromeDriver();   	
    	driver.get(urlString);
    	driver.findElement(By.id("loginAccountInput")).sendKeys("wb-huzeying");
    	driver.findElement(By.id("loginPassword")).sendKeys("GJSYhzy899");
    	driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[5]/button")).click();
        wc.getCookieManager().setCookiesEnabled(true);
        wc.getOptions().setJavaScriptEnabled(true);
        wc.getOptions().setCssEnabled(false);
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setPrintContentOnFailingStatusCode(false);
        wc.setAjaxController(new NicelyResynchronizingAjaxController());
        wc.getOptions().setTimeout(3000);
        wc.waitForBackgroundJavaScript(1000);
        wc.getOptions().setUseInsecureSSL(true);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
    	}catch(Exception e){
    		
    	}
    }
    
    
    public void getChromeWebClient(){
    	try{
    		//System.setProperty("webdriver.chrome.driver","/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
    	System.setProperty("webdriver.chrome.driver", "libs"+File.separator+"chromedriver.exe");
    		

    		//ChromeDriverService service = new ChromeDriverService.Builder() .usingDriverExecutable(new File("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome")).usingAnyFreePort().build();
    		//service.start();
    		driver = new ChromeDriver();
    		//driver.get("http://www.baidu.com");
    	//driver = new ChromeDriver();   	
    	driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);  
    	//driver.get("https://crm.alibaba-inc.com/noah/presale/work/allCustomer.vm");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public void search(){
    	
    	HSSFSheet sheet =oldBook.getSheetAt(0);

    	///HSSFSheet newsheet =newBook.getSheetAt(0);
    	int rowNum=sheet.getLastRowNum();
    	driver.get(urlString);
    	if(null!=driver.findElement(By.id("loginAccountInput"))){
    	driver.findElement(By.id("loginAccountInput")).sendKeys(username);
    	driver.findElement(By.id("loginPassword")).sendKeys(password);
    	driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[5]/button")).click();
    	try {
			Thread.sleep(7500);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	}//kun
    	
    	boolean isfound=false;//kun
    	//boolean isfound=true;
    	int count=1;
    	while(!isfound){
    		try{
    			Thread.sleep(1000);
    		   List<WebElement> inputs = driver.findElements(By.tagName("input"));
    	        WebElement compayNmae = inputs.get(0);
    	        if(compayNmae.isDisplayed())isfound=true;
    	        if(count++==20)break;
    		}catch(Exception e){
    			
    		}
    	}
    	SearchPage searchPage=null;
    	//SearchPage searchPage=new SearchPage(null);

    	
    	List<String> lis1=new ArrayList<String>();
    	lis1.add("汕头市潮南区峡山");
    	lis1.add("汕头市潮南区陈店");
    	lis1.add("汕头市潮南区司马浦");
    	lis1.add("汕头市潮南区胪岗");
    	lis1.add("汕头市潮南区两英");
    	lis1.add("汕头市潮南区仙城");
    	lis1.add("汕头市潮南区红场");
    	lis1.add("汕头市潮南区雷岭");
    	lis1.add("汕头市潮南区陇田");
    	lis1.add("汕头市潮南区成田"); 	
    	lis1.add("汕头市潮南区井都");

    	
    	List<String> lis2=new ArrayList<String>();
    	lis2.add("汕头市潮阳区文光");
    	lis2.add("汕头市潮阳区城南");
    	lis2.add("汕头市潮阳区城南");
    	lis2.add("汕头市潮阳区金浦");
    	lis2.add("汕头市潮阳区海门");
    	lis2.add("汕头市潮阳区河溪");
    	lis2.add("汕头市潮阳区和平");
    	lis2.add("汕头市潮阳区西胪");
    	lis2.add("汕头市潮阳区关埠");
    	lis2.add("汕头市潮阳区金灶");
    	lis2.add("汕头市潮阳区谷饶");
    	lis2.add("汕头市潮阳区贵屿");
    	lis2.add("汕头市潮阳区铜盂");

    	
    	List<String> lis3=new ArrayList<String>();
    	lis2.add("汕头市澄海区");
    
    	
//    	//int maxRow=rowNum/2;//kun
//    	int maxRow=rowNum/threadCount;
//    	int begin=(threadId-1)*maxRow;
//    	int end=threadId*maxRow;
//    	if(begin!=0){
//    		begin=begin+1;
//    	}
//    	if(threadId==threadCount){
//    		end=rowNum;
//    	}
       // for(int i=begin;i<=end;i++){
    	for(int i=0;i<rowNum;i++){
    		
            int rowPosition=0;

    	    synchronized( position){
    	        rowPosition=position.getCur();
    	        position.setCur(position.getCur()+1);
    	        }
    	   // System.out.println("******************** "+position.getCur());
    	    if(rowPosition>=rowNum)
    	    	{
    	    	break;
    	    	}
    	    	
    	    
        searchPage=new SearchPage(driver);//kun        
        
        String search="";
        HSSFRow onerow =null;
        HSSFCell cell=null;
        HSSFCell cell2=null;

        try{
         onerow = sheet.getRow(rowPosition);
         cell = onerow.getCell(0);
         cell2=onerow.getCell(1);
        }catch(Exception e){
        	continue;
        }
       if(null==cell.getStringCellValue()&&cell.getStringCellValue().equals("")){
    	   continue;
       }
        String value=	cell.getStringCellValue().replace("有限公司", "");
        value=    value.replace("\n", "").replace("\t", "").replace("\r", "");
        value=value.trim();
        if(value.endsWith("厂")){
        	value=value.substring(0, value.length()-1);
        }
        if(value.endsWith("商行")){
        	value=value.substring(0, value.length()-2);
        }
        
        if(value.endsWith("商店")){
        	value=value.substring(0, value.length()-2);
        }
        if(value.endsWith("店")){
        	value=value.substring(0, value.length()-1);
        }
        if(value.endsWith("经营部")){
        	value=value.substring(0, value.length()-3);
        }
        if(value.endsWith("部")){
        	value=value.substring(0, value.length()-1);
        }
        if(value.endsWith("行")){
        	value=value.substring(0, value.length()-1);
        }
        if(value.endsWith("场")){
        	value=value.substring(0, value.length()-1);
        }
        
        if(value.contains("汕头市潮南区")){
        	for(String s:lis1){
        		value=value.replace(s,"");
        	}

        }
        
        if(value.contains("汕头市潮阳区")){
         	for(String s:lis2){
        		value=value.replace(s,"");
        	}
        }
        
        if(value.contains("汕头市澄海区")){
         	for(String s:lis3){
        		value=value.replace(s,"");
        	}
        }
        
        if(value.contains("区")){  	
        	String[] sss=value.split("区");
        	if(sss.length==2){
        			value=sss[1];	
        	}    	
        }
        if(value.contains("市")){  	
        	String[] sss=value.split("市");
        	if(sss.length==2){
        			value=sss[1];	
        	}   
        }
        	search=value;
        	if(null!=cell2){
        	if(null!=cell2.getStringCellValue()&&cell2.getStringCellValue()!=""){
        		search=search+":"+cell2.getStringCellValue();
        	}
        	}
        	//System.out.println(search);
        	int status=0;
        	try{
    	 status=searchPage.Search(search);
        	}catch(Exception e){
        		driver.navigate().refresh(); 
           	 status=searchPage.Search(search);
        	}
    //	int status=searchPage.Search2(search);

    	synchronized(newBook){

    	if(search.contains(":"))
    	{
    		
    	  //  addRow(cell.getStringCellValue().trim(),"",rowPosition+"");
    	    		
    		switch(status){
        	case 1:
        		//addRow(newsheet,cell.getStringCellValue().trim(),"##仓库中##");
        		break;
        	case 2:
        		addRow(searchPage.getCompanyName()+":"+search.split(":")[1],"15 天注册",rowPosition+"");
        		break;
        	case 3:
        		//addRow(newsheet,cell.getStringCellValue().trim(),"##仓库中##");
        		addRow(searchPage.getCompanyName()+":"+search.split(":")[1],"15 天注册",rowPosition+"");
        		break;
        	case 4:
        		//addRow(newsheet,cell.getStringCellValue().trim(),"**公海中**");
        		break;
        	case 5:
        		//addRow(newsheet,cell.getStringCellValue().trim(),"**公海中**");
        		addRow(searchPage.getCompanyName()+":"+search.split(":")[1],"15 天注册",rowPosition+"");
        		break;
        	case 6:
        		//addRow(,cell.getStringCellValue().trim(),"到单或续签");
        		break;
        	case 7:
        		addRow(searchPage.getCompanyName()+":"+search.split(":")[1],"15 天注册",rowPosition+"");
        		break;
        	default:
            		addRow(cell.getStringCellValue().trim()+":"+search.split(":")[1],"无符合",rowPosition+"");
            		break;
        	case 0:
        		addRow(cell.getStringCellValue().trim()+":"+search.split(":")[1],"无符合",rowPosition+"");
        		break;
            }
    	
    	}else{
    		
    		switch(status){
        	case 1:
        		addRow(cell.getStringCellValue().trim(),"##仓库中##",rowPosition+"");
        		break;
        	case 2:
        		addRow(searchPage.getCompanyName(),"15 天注册",rowPosition+"");
        		break;
        	case 3:
        		addRow(cell.getStringCellValue().trim(),"##仓库中##",rowPosition+"");
        		addRow(searchPage.getCompanyName(),"15 天注册",rowPosition+"");
        		break;
        	case 4:
        		addRow(cell.getStringCellValue().trim(),"**公海中**",rowPosition+"");
        		break;
        	case 5:
        		addRow(cell.getStringCellValue().trim(),"**公海中**",rowPosition+"");
        		addRow(searchPage.getCompanyName(),"15 天注册",rowPosition+"");
        		break;
        	case 0:
        		addRow(cell.getStringCellValue().trim(),"无符合",rowPosition+"");
        		break;
        	case 7:
        		addRow(cell.getStringCellValue().trim(),"到单或续签",rowPosition+"");
        		addRow(searchPage.getCompanyName(),"15 天注册",rowPosition+"");
        		break;
        	case 6:
        		addRow(cell.getStringCellValue().trim(),"到单或续签",rowPosition+"");
        		break;
        	default:
        		addRow(cell.getStringCellValue().trim(),"无符合",rowPosition+"");
        		break;
            }		
    	}
    	//
    	}
        }
      driver.close();
       driver.quit();
    }
    
 
    public void close(HSSFWorkbook book,String fileName){

    		 	
    	try {
	    	FileOutputStream out = new FileOutputStream(fileName);  
	    	
	    	if(null==out){
	    		
	    	}
	    	if(book==null)	System.out.println("ddddddfdfdfddddddddd");
	    	book.write(out);
	    
	    	out.flush();
	    	out.close();
	    	//Thread.sleep(1000);
		//	book.close();


		} catch ( Exception e) {
			try {
				Thread.sleep(2000);
				System.out.println("22222");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			close(book,fileName);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	}
    
    public void close3(HSSFWorkbook book,String fileName){
    	synchronized(book){
    		 	
    	try {
	    	FileOutputStream out = new FileOutputStream(fileName);  
	    	Thread.sleep(1000);
	    	book.write(out);
	    	out.close();
			book.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    	}
    public void setValue(HSSFSheet sheet,HSSFRow row,int column,String value){
    	if(null!=row.getCell(1)){
    		
    	}else{
    		
    	}
    	// cell = row.createCell(1);

    	
    }
  // public void needDelete
    
    public void addRow(String content,String ss,String position){
    	
    	
    	String[] data={content,ss,position};
    	try {
			queue.put(data);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
    public void addRow2(HSSFSheet sheet,String content,String ss){
    	int lastRow=sheet.getLastRowNum();
		HSSFRow row=null;
		 row = sheet.createRow(lastRow+1);
//
//    	if(lastRow==0)
//    	{
//    		 row = sheet.createRow(lastRow);
//
//    	}else{
//    		 row = sheet.createRow(lastRow+1);
//    	}
    	
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
         cell3.setCellValue(ss);
    	}else{
            cell = row.createCell(0); 
            cell.setCellValue(content);
            cell3 = row.createCell(2);
            cell3.setCellValue(ss);
    	}


    }
    public void setAccountNum(int num){
    	this.threadCount=num;
    }
    public static void removeRow(HSSFSheet sheet,int rowIndex){
    	int lastRowNum=sheet.getLastRowNum();
    	if(rowIndex>=0){
    	//将行号位rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行
    	sheet.shiftRows(rowIndex+1,lastRowNum,-1);
    	}
    	if(rowIndex==lastRowNum){
    	HSSFRow removingRow=sheet.getRow(rowIndex);
    	if(removingRow!=null){
    	sheet.removeRow(removingRow);
    	}
    	}
    	}
}

