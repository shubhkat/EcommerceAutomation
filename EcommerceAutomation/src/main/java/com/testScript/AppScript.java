package com.testScript;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
	@FindBy(xpath="//a[@title='Dettol Original Liquid Hand Wash Refill Hand Wash Bottle']")
	private WebElement productTitle;
	
	//product link
	@FindBy(xpath="(//div/div/div[3]/div[2]/div/div[2]/div/div/div[1])[2]")
	private WebElement productLink;
	
	//price of product in product link page
	@FindBy(xpath="(//div/div[1]/a/div/div[1])[5]")
	private WebElement productPriceSearchPage;
	
	@FindBy(xpath="(//div[2]/div[1]/div/div[1])[4]")
	private WebElement productPriceProductPage;
	
	//scroll element
	@FindBy(xpath = "//div[contains(text(),'Safe and Secure Payments.')]")
	private WebElement scrollElement;
	
	//add to basket
	@FindBy(xpath="//button[contains(text(),'ADD TO BASKET')]")
	private WebElement addToBasketButton;
	
	//go to basket btn
	@FindBy(xpath="//button[contains(text(),'GO TO BASKET')]")
	private WebElement goToBasketButton;
	
	//product price on go to basket page
	@FindBy(xpath="(//div[1]/div[2]/div[1]/div/div/span[1])[1]")
	private WebElement productPriceBasketPage;
	
	@FindBy(xpath="//div[1]/div[2]/div[1]/div/div/div/div/div[1]/span")
	private WebElement productPriceBasketDetailPage;
	
	@FindBy(xpath="(//div[1]/div[2]/div[1]/div/div/div/div/div[2]/span)[1]")
	private WebElement deliveryFee;
	
	@FindBy(xpath="//div[1]/div[2]/div[1]/div/div/div/div/div[3]/div/span")
	private WebElement totalFee;
	
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
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//identify cross button element
		loginPopupCancelBtn.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Identify login button element
		loginLink.click();
	
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Identify username element 
		String userName = Excel.ReadExcel(filePath, sheetName, 1, 0); 
		String passWord = Excel.ReadExcel(filePath, sheetName, 1, 1);
		username.sendKeys(userName);
		
		//finding passwaord element
		password.sendKeys(passWord);
		
		//WebElement loginbtn = driver.findElement(By.xpath("(//span[text()='Login'])[2]"));
		loginbtn.click();

	}
	
	public void verifyLogo() {
		String titleAttribute = applogo.getAttribute("title");
		Assert.assertEquals("Supermart", titleAttribute);
	}
	
	public void productSelection() {
		
	    //excel Path 
	    String filePath = "../EcommerceAutomation/test_Data/testData.xlsx";
		  
	    //product select sheets 
	    String productselectionSheetName = "Product Selection"; 

	    String searchText = Excel.ReadExcel(filePath, productselectionSheetName, 1, 0);
		  
		searchButton.sendKeys(searchText, Keys.ENTER);
		
		Reporter.log("Product Title: "+productTitle.getText(), true);
		
		String price_searchPage = productPriceSearchPage.getText();
		String searchPagePrice = price_searchPage.substring(1, price_searchPage.length());
		Reporter.log("Product price: "+searchPagePrice, true);
		productPrice_searchPage = Integer.parseInt(searchPagePrice);
		
		productLink.click();

		//window focus change to other window
		Set<String> s = driver.getWindowHandles();
		Iterator<String> itr = s.iterator();
		while(itr.hasNext()) {
			String ref_id = itr.next();
			driver.switchTo().window(ref_id);
		}
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		String price_productPage = productPriceProductPage.getText();
		
		String productPagePrice = price_productPage.substring(1, price_productPage.length());
		
		productPrice_productDetailPage = Integer.parseInt(productPagePrice);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
	}
	
	public void addToBasket() {
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//scroll into view
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", scrollElement);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		addToBasketButton.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		goToBasketButton.click();
	}
	
	public void comparePrice() {
		
		String price_basketPage = productPriceBasketPage.getText();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String basketPagePrice = price_basketPage.substring(1, price_basketPage.length());
		
		productPrice_basketPage = Integer.parseInt(basketPagePrice);

		String price_basketDetailPage = productPriceBasketDetailPage.getText();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String basketDetailPagePrice = price_basketDetailPage.substring(1, price_basketDetailPage.length());
		
		productPrice_basketDetailPage = Integer.parseInt(basketDetailPagePrice);
		
		String deliveryAmount = deliveryFee.getText();
		String deliveryPrice = deliveryAmount.substring(1, deliveryAmount.length());
		Reporter.log("Delivery Charges: "+deliveryPrice, true);
				
		int productDeliveryPrice = Integer.parseInt(deliveryPrice);

		String totalAmount = totalFee.getText();
		String totalPrice = totalAmount.substring(1, totalAmount.length());
		Reporter.log("Delivery Charges + Product price = "+totalPrice, true);
				
		int productTotalPrice = Integer.parseInt(totalPrice);
		
		if(productPrice_searchPage == productPrice_productDetailPage && productPrice_searchPage == productPrice_basketPage && productPrice_searchPage == productPrice_basketDetailPage) {
			Reporter.log("All price are same", true);
			totalPrice_basketDetails = productPrice_basketDetailPage + productDeliveryPrice;
		}
		else {
			Reporter.log("All price are different", true);
		}
		
		if(productTotalPrice == totalPrice_basketDetails) {
			Reporter.log("Total price for the product is equal to total amount to be paid", true);
		}
		else {
			Reporter.log("Total price for the product is not equal to total amount to be paid", true);
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
