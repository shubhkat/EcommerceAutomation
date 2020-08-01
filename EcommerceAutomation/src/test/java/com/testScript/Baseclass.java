package com.testScript;

import java.io.IOException;
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
	
	@Parameters("browsername")
	@BeforeClass
	public void preCondition(String browsername)
	{
		if(browsername.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}
		else if(browsername.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://www.flipkart.com/");
	}
	
	@BeforeSuite
	public void setReport()
	{
		String reportpath = "../ECommerceAutomation/extentReport/report.html";
		htmlreport = new ExtentHtmlReporter(reportpath);
		htmlreport.config().setDocumentTitle("Ecommerce Automation testing report");
		htmlreport.config().setReportName("Automation testing");
		htmlreport.config().setTheme(Theme.STANDARD);
		
		report = new ExtentReports();
		report.attachReporter(htmlreport);
		report.setSystemInfo("Browsername", "Firefox");
		report.setSystemInfo("Tester", "Test Engineer");
		report.setSystemInfo("OS", "windows-10");
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
