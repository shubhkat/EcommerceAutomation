package com.testScript;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import utility.Screenshot;

public class Baseclass {
	public static ExtentHtmlReporter htmlreport;
	public static ExtentReports report;
	public static ExtentTest test;
	
    WebDriver driver;
	
    String firefox_browser;
    String chrome_browser;
    
    
    static Properties property_data = new Properties();
    
    static {
    	try {
			property_data.load(new FileInputStream("../EcommerceAutomation/test_Data/browserData.properties"));
			property_data.load(new FileInputStream("../EcommerceAutomation/test_Data/extentReportData.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	@Parameters("browsername")
	@BeforeClass
	public void preCondition(String browsername)
	{
		firefox_browser = property_data.getProperty("firefox_browser");
		chrome_browser = property_data.getProperty("chrome_browser");
		String flipkart_url = property_data.getProperty("flipkart_url");
		
		if(browsername.equalsIgnoreCase(firefox_browser))
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}
		else if(browsername.equalsIgnoreCase(chrome_browser)) {
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.get(flipkart_url);
	}
	
	@Parameters("browsername")
	@BeforeSuite
	public void setReport(String browsername)
	{	
		firefox_browser = property_data.getProperty("firefox_browser");
		chrome_browser = property_data.getProperty("chrome_browser");
		
		String report_path = property_data.getProperty("report_path");
		String document_title = property_data.getProperty("document_title");
		String report_name = property_data.getProperty("report_name");
		
		String tester_name = property_data.getProperty("tester_name");
		String os = property_data.getProperty("os");
		
		//String reportpath = "../ECommerceAutomation/extentReport/report.html";
		htmlreport = new ExtentHtmlReporter(report_path);
		htmlreport.config().setDocumentTitle(document_title);
		htmlreport.config().setReportName(report_name);
		htmlreport.config().setTheme(Theme.STANDARD);
		
		report = new ExtentReports();
		report.attachReporter(htmlreport);
		
		if(browsername.equalsIgnoreCase(firefox_browser))
		{
			report.setSystemInfo("Browsername", firefox_browser);
		}
		else if(browsername.equalsIgnoreCase(chrome_browser)) {
			report.setSystemInfo("Browsername", chrome_browser);
		}
		
		report.setSystemInfo("Tester", tester_name);
		report.setSystemInfo("OS", os);
	}
	
	@AfterClass
	public void postCondition() throws InterruptedException
	{
		Thread.sleep(5000);
		driver.quit();
	}
	
	//@AfterMethod(alwaysRun=true)
	@AfterMethod
	public void captureScreenshot(ITestResult result)
	{
		test = report.createTest(result.getName());
		if(result.getStatus()==ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel("Test Method Failed", ExtentColor.RED));
			
			  String temp = Screenshot.screenshot(driver, result.getName()); 
			  try {
				  test.addScreenCaptureFromPath(temp); 
				  Reporter.log("Able to capture Screenshot",true);
			  } 
			  catch (IOException e) { 
				  Reporter.log("Unble to capture Screenshot",true);
				  //e.printStackTrace(); 
			  }
			 
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel("Test Method Passed", ExtentColor.GREEN));
			/*
			 * String temp = Screenshot.screenshot(flipkart.driver, result.getName());
			 * test.addScreenCaptureFromPath(temp);
			 */
		}
		else {
			test.log(Status.SKIP, MarkupHelper.createLabel("Test Method Skiped", ExtentColor.GREY));
		}
		report.flush();
	}
	
}
