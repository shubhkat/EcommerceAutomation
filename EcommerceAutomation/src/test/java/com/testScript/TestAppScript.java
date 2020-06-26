
package com.testScript;
 
import org.testng.annotations.Test;

  public class TestAppScript extends Baseclass {
  
	  @Test(groups = "testMethods", description =
	  "this method is used to login in an application") 
	  public void testLoginPage() { 
		  AppScript appScript = new AppScript(driver); 
		  appScript.loginPage(); 
	  }
	  
	  @Test(dependsOnGroups = "testMethods", priority = 1, description =
	  "this method is used to verify logo of application") 
	  public void testVerifyLogo() { 
		  AppScript appScript = new AppScript(driver); 
		  appScript.verifyLogo(); 
	  }
	  
	  @Test(dependsOnGroups = "testMethods", priority = 2, description =
	  "this method is used to select product from product page") 
	  public void testProductSelection() { 
		  AppScript appScript = new AppScript(driver);  
		  appScript.productSelection(); 
	  }
	  
	  @Test(dependsOnMethods = "testProductSelection", description =
	  "this method is used to add a selected product to the basket") public void
	  testAddToBasket() throws InterruptedException{ 
		  AppScript appScript = new AppScript(driver); 
		  appScript.addToBasket(); 
		  
	  }
	  
	  @Test(dependsOnMethods = "testAddToBasket", description =
	  "this method is used to compare the price whether it is same or not") 
	  public void testComparePrice() { 
		  AppScript appScript = new AppScript(driver); 
		  appScript.comparePrice(); 
	  }
	  
	  @Test(dependsOnMethods = {"testLoginPage", "testProductSelection",
	  "testAddToBasket", "testComparePrice"}) 
	  public void detailsoftestcases() {
		  AppScript appScript = new AppScript(driver); 
		  appScript.detailsOfTestCases(); 
	  } 
	  
  }
	 