package com.crm.Main;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;

public class SearchPage {
	WebDriver webDriver;
	@FindBy(how=How.XPATH,using="//input[1]")
	private WebElement compayInput;
	
	@FindBy(how=How.XPATH,using="//input")
	private List<WebElement> quHaoInput;
	
	@FindBy(how=How.XPATH,using="//input[@type='button']")
	private List<WebElement> searchBt;
	
	@FindBy(how=How.XPATH,using="//tbody[@class='shy-grid-body']")
	private List<WebElement> bodysList;
	
	@FindBy(how=How.XPATH,using="//input[@id='textcheckcode']")
	private List<WebElement> yanzhengmainput;
	
	private String  searchText;
	
	public String company="";

//	private 
	
    public SearchPage(WebDriver webDriver){
    	this.webDriver=webDriver;
    	PageFactory.initElements(webDriver, this);
    }
	
    public void setSearchString(String searchString){
    	this.searchText=searchString;
    }
    
	
    
    public int Search2(String searchString){
    	int randome=(int)((Math.random())*6);
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(randome);
    	return randome;
    	
    }
   
    protected boolean isTextExist(String content) {
		List<WebElement> el = new ArrayList<WebElement>();
		try {
			//Thread.sleep(1000);
			el = webDriver.findElements(By.xpath("//*[contains(.,\"" + content+ "\")]"));
		} catch (Exception e) {
			el = webDriver.findElements(By.xpath("//input[@value=\"" + content+ "\"]"));
			if (el.size() > 0 && null != el) {
				return true;
			}

			return false;
		}
		if (el.size() == 0 || null == el) {
			el = webDriver.findElements(By.xpath("//input[@value=\"" + content+ "\"]"));
			if (el.size() > 0 && null != el) {
				return true;
			}
			return false;
		}
		return true;
	}

    public int Search(String searchString ){
    	
    	
    	System.out.println("####################### 开始搜索  "+searchString+"  ###########################");
    		int returns=0;
    		this.searchText=searchString;
        	//get data
    		//compayInput.get(0).sendKeys("ddd");
        	compayInput.clear();
        	quHaoInput.get(3).clear();
        	//compayInput.sendKeys("莹内衣");  
        	if(searchText.contains(":")){
            	compayInput.sendKeys(searchText.split(":")[0]);  	

                quHaoInput.get(3).sendKeys(searchText.split(":")[1]);
        	}else{
            	compayInput.sendKeys(searchText);  	

        	}
        	
            // quHaoInput.sendKeys("123");
        	//JavascriptExecutor jse = (JavascriptExecutor)webDriver;  
        	//jse.executeScript("resetSearchFormContact()");
            searchBt.get(0).click();

            /**
             * get data
             */
        	
            
        	try{
        		Thread.sleep(2500);
        	}catch (Exception e) {
				// TODO: handle exception
			}
        	
        //	boolean yanzhengma=isTextExist("验证码");
        	if(null!=yanzhengmainput){
        		while(yanzhengmainput.size()>0){
    				try {
    					System.out.println("!!!!!!!!!!!!! 有验证码，快去手动输入!!!!!!!!!!!");
						Thread.sleep(5000);
						if(null==yanzhengmainput)break;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

        	}
        	}
//        		if(yanzhengma){
//        			while(yanzhengma){
//        				try {
//        					System.out.println("!!!!!!!!!!!!! 有验证码，快去手动输入!!!!!!!!!!!");
//							Thread.sleep(5000);
//				        	 yanzhengma=isTextExist("验证码");
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//        			}
//        			//return 3;
//        	}
           List<WebElement> contentTrElements=null;

           try{
            contentTrElements= bodysList.get(0).findElements(By.xpath("tr"));
           }catch (Exception e) {
        	   searchBt.get(0).click();
        	   try {
				Thread.sleep(4000);
				contentTrElements= bodysList.get(0).findElements(By.xpath("tr"));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				searchBt.get(0).click();
				try {
					Thread.sleep(6000);				
					contentTrElements= bodysList.get(0).findElements(By.xpath("tr"));
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					webDriver.navigate().refresh(); 
			      	compayInput.clear();
		        	quHaoInput.get(3).clear();
		        	//compayInput.sendKeys("莹内衣");  
		        	if(searchText.contains(":")){
		            	compayInput.sendKeys(searchText.split(":")[0]);  	

		                quHaoInput.get(3).sendKeys(searchText.split(":")[1]);
		        	}else{
		            	compayInput.sendKeys(searchText);  	

		        	}
		        	 searchBt.get(0).click();
		        		try{
		            		Thread.sleep(4000);
		            	}catch (Exception e233) {
		    				// TODO: handle exception
		    			}
		        	   	if(null!=yanzhengmainput){
		            		while(yanzhengmainput.size()>0){
		        				try {
		        					System.out.println("!!!!!!!!!!!!! 有验证码，快去手动输入!!!!!!!!!!!");
		    						Thread.sleep(5000);
		    						if(null==yanzhengmainput)break;
		    					} catch (InterruptedException essss) {
		    						// TODO Auto-generated catch block
		    						e.printStackTrace();
		    					}

		            	}
		            	}
				}	
				e1.printStackTrace();
			}
			// TODO: handle exception
		}

           
            if(contentTrElements.size()<1){
	//do something
            	}else{
            		if(!searchText.contains(":")){
            		boolean isCangku=false;
            		for(int m=0;m<contentTrElements.size()-1;m++){
            			WebElement onerecord=	contentTrElements.get(m);
            			List<WebElement> tdsElements=	onerecord.findElements(By.xpath("td"));
            			if(tdsElements.size()<10)continue;
            			String saleString=tdsElements.get(7).getText().toString();
            			String daodanString=tdsElements.get(8).getText().toString().replace("\r", "").replace("\n", "").replace("\t", "").trim();
            			//String stateString=tdsElements.get(9).toString();
            			String stateString=tdsElements.get(9).getText().toString().replace("\r", "").replace("\n", "").replace("\t", "").trim();
            			
            		
            			if(saleString.contains("续签")||daodanString.contains("已到单")){
            				//removeRow(sheet, i);
            				returns=6;
            				
            				System.out.println("************ 已到单或续签："+searchText);
            				System.out.println("");

            				break;
            			}
            			if(stateString.contains("仓库中")){
            				isCangku=true;
            			}
            		
            			if(m==contentTrElements.size()-2){
            				if(isCangku){
                				System.out.println("************ 仓库中："+searchText);
                				System.out.println("");
                				returns =1;
                			}else{
            				System.out.println("************ 所有公海： "+searchText);
            				System.out.println("");
            				returns=4;
                			}
            			}
            		}
            	}
                     
            		
            	}

        	int size=	bodysList.size();
        		WebElement testt=	bodysList.get(size-2);
 
        		List<WebElement> fdaasElements= testt.findElements(By.xpath("tr"));
        		   if(fdaasElements.size()>0){
     
        		 for(WebElement esss:fdaasElements){
        		 List<WebElement> fdaasddddElements=esss.findElements(By.xpath("td"));
        		   String  loginIdString=fdaasddddElements.get(0).getText().toString();
        		   String  gongshi=fdaasddddElements.get(1).findElements(By.xpath("div")).get(0).findElements(By.xpath("div")).get(0).getText().toString();
        		   String  shenfen=fdaasddddElements.get(2).getText().toString();
        		   String  city=fdaasddddElements.get(3).getText().toString();
        		   String  registertime=fdaasddddElements.get(4).getText().toString();
        		   String  people=fdaasddddElements.get(5).getText().toString();
        		   
        		   System.out.println("15 天注册："+loginIdString+"###"+gongshi+"###"+shenfen+"###"+city+"###"+registertime+"###"+people);
        		   System.out.println("");

        		   this.setCompanyName(gongshi);
        		   if(returns==1){
        			   return 3;
        		   }else if(returns==4){
        			   return 5;
        		   }else if(returns==6){
        			   return 7;
        		   }
        		   return 2;	  
        		 }
        		   }else{
        			   if(returns==1){
        				   return 1;
        			   }else if(returns ==4){
        				   return 4;
        			   }else if(returns==6){
        				   return 6;
        			   }
        		   }
        		   return 0;
        	   }
           

    
    
    	public void setCompanyName(String company){
    		this.company=company;
    	}
        public String getCompanyName(){
        	return this.company;
        }
        
        
        }

