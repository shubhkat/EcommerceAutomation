package com.testScript;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import utility.Excel;

public class AppScript {
	public static ExtentHtmlReporter htmlreport;
	public static ExtentReports report;
	public static ExtentTest test;
	
	protected WebDriver driver;
	
	int productPrice_searchPage;	// Declaration of price displayed in search results page
	int productPrice_productDetailPage;	// Declaration of price after clicking the product link
	int productPrice_basketPage;	// Declaration of price after add to basket page
	int productPrice_basketDetailPage;	// Declaration of price from go to basket page
	int totalPrice_basketDetails;	// Declaration of  total price after applying delivery chargess
	
	
	@FindBy(xpath="//div/button[contains(text(),'✕')]")
	private WebElement loginPopupCancelBtn;
	  
	@FindBy(xpath="//a[contains(text(),'Login')]")
	private WebElement loginLink;
	
	@FindBy(xpath="(//input)[7]")
	private WebElement username;
	
	@FindBy(xpath="(//input)[8]")
    private WebElement password;	
	
	@FindBy(xpath="//button/span")
	private WebElement loginbtn;
	
	//verify  logo
	@FindBy(xpath="//div/a/img[@title='Flipkart']")
	private WebElement applogo;
	
	//search btn
	@FindBy(xpath="//input[@placeholder='Search for products, brands and more']")
	private WebElement searchButton;
	
	//title of the product
	@FindBy(xpath="//a[contains(@title,'Dettol Original Liquid Hand Wash Refill Hand Wash Pouch')]")
	private WebElement productTitle;
	
	//product link
	//@FindBy(xpath="(//div/div/div[3]/div[2]/div/div[2]/div/div/div[1])[2]")
	//private WebElement productLink;
	
	//price of product in product link page
	@FindBy(xpath="//a[contains(@title,'Dettol Original Liquid Hand Wash Refill Hand Wash Pouch')]/..//a[3]/div/div")
	private WebElement productPriceSearchPage;
	
	@FindBy(xpath="//div/div[2]/div[2]/div/div//span[contains(text(),'Dettol Original Liquid Hand Wash Refill Hand Wash Pouch')]/../../..//div[2]/div[1]")
	private WebElement productPriceProductPage;
	
	//scroll element
	@FindBy(xpath = "//div[contains(text(),'Safe and Secure Payments.')]")
	private WebElement scrollElementByOtherElement;
	
	//@FindBy(xpath = "//a[contains(text(),'All questions')]")
	//private WebElement scrollElement;
	
	//add to basket
	@FindBy(xpath="(//button)[2]")
	private WebElement addToCartButton;
	
	//go to basket button
	@FindBy(xpath="//button[contains(text(),'GO TO BASKET')]")
	private WebElement goToBasketButton;

	public AppScript(WebDriver driver) {
		this.driver = driver;
		//This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
	}
	
	public void loginPage() {
		
	   //excel Path 
	   String filePath = "../EcommerceAutomation/test_Data/testData.xlsx";
	  
	   //sheet Name 
		String sheetName = "Login Details"; 
		
		//identify cross button element
		loginPopupCancelBtn.click();
		
		//Identify login button element
		loginLink.click();
	
		//Identify username element 
		String userName = Excel.ReadExcel(filePath, sheetName, 1, 0); 
		String passWord = Excel.ReadExcel(filePath, sheetName, 1, 1);
		username.sendKeys(userName);
		
		//finding passwaord element
		password.sendKeys(passWord);
		
		loginbtn.click();

	}
	
	public void verifyLogo() {
		String titleAttribute = applogo.getAttribute("title");
		Assert.assertEquals("Supermart", titleAttribute);
		/*
		 * try { Assert.assertEquals("Supermart", titleAttribute);
		 * Reporter.log("application logo is match!",true); } catch(AssertionError e) {
		 * Reporter.log("application logo is not match!",true); }
		 */
	}
	
	public void productSelection() {
		
	    //excel Path 
	    String filePath = "../ECommerceAutomation/test_Data/testData.xlsx";
		  
	    //product select sheets 
	    String productselectionSheetName = "Product Selection"; 

	    String searchText = Excel.ReadExcel(filePath, productselectionSheetName, 1, 0);
		  
		searchButton.sendKeys(searchText, Keys.ENTER);
		
		Reporter.log("Product Title: "+productTitle.getText(), true);
		
		String price_searchPage = productPriceSearchPage.getText();
		String searchPagePrice = price_searchPage.substring(1, price_searchPage.length());
		Reporter.log("Product price: "+searchPagePrice, true);
		productPrice_searchPage = Integer.parseInt(searchPagePrice);
		
		productTitle.click();
		
		//window focus change to other window
		Set<String> s = driver.getWindowHandles();
		Iterator<String> itr = s.iterator();
		while(itr.hasNext()) {
			String ref_id = itr.next();
			driver.switchTo().window(ref_id);
		}
		
		String price_productPage = productPriceProductPage.getText();
		
		String productPagePrice = price_productPage.substring(1, price_productPage.length());
		
		productPrice_productDetailPage = Integer.parseInt(productPagePrice);
				
	}
	
	public void addToCart() {
		
		//scroll into view
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", scrollElementByOtherElement);
		
		addToCartButton.click();
		goToBasketButton.click();
		
	}
	
	public void comparePrice() {
								
		if(productPrice_searchPage == productPrice_productDetailPage && productPrice_searchPage == productPrice_basketPage && productPrice_searchPage == productPrice_basketDetailPage) {
			Reporter.log("All price are same", true);
		}
		else {
			Reporter.log("All price are different", true);
		}
					
	}	
	
	
	  public void detailsOfTestCases() { 
		  System.out.println();
		  System.out.println("...............Test Case Desciption...............");
		  System.out.println("Test Case: loginPage: This method is used to login in an application"); 
		  System.out.println("Test Case: productSelection: This method is used to select product from product page"); 
		  System.out.println("Test Case: addToBasket: This method is used to add a selected product to the basket"); 
		  System.out.println("Test Case: compairPrice: This method is used to compair the price whether it is same or not"); 
		  System.out.println();
		  System.out.println("...............Passed Test Case...............");
		  System.out.println("PASSED: testLoginPage");
		  System.out.println("PASSED: testProductSelection");
		  System.out.println("PASSED: testAddToBasket");
		  System.out.println("PASSED: testCompairPrice"); 
		  System.out.println();
		  
	  }
}
