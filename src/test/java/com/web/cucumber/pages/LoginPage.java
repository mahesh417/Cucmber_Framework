package com.web.cucumber.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.web.cucumber.utility.Constants;
import com.web.cucumber.utility.TestBase;

public class LoginPage extends TestBase {

	@FindBy(xpath = "//input[@id='username']")
	WebElement userName;

	@FindBy(xpath = "//input[@id='password']")
	WebElement password;

	@FindBy(xpath = "//input[@id='Login']")
	WebElement loginButton;

	@FindBy(xpath = "//div[@class='navLinks']//span[@id='userNavLabel']")
	WebElement profileName;

	@FindBy(xpath = "//div[@class='navLinks']//a[contains(text(),'Logout')]")
	WebElement logoutButton;

	@FindBy(xpath = "//div[@class='feedmain']")
	WebElement homePageNewsCmpt;
	
	@FindBy(xpath = "//a[contains(@title,'Home Tab')]")
	WebElement accountHomeButton;

	static Logger logInOutLog = Logger.getLogger(LoginPage.class.getName());
	
	public LoginPage(WebDriver rdriver) {
		driver = rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	
		
	public void entercred(String username, String pswd) {
        userName.sendKeys(username);
        password.sendKeys(pswd);
        loginButton.click();
        waitForPageToLoad(driver); 
        pageRefresh(driver);
        
        waitforElement(driver, homePageNewsCmpt);
        if(!isClickable(driver, accountHomeButton))
        {
            pageRefresh(driver);
            waitinSec(3);
            waitforElement(driver, homePageNewsCmpt);
        }
        Assert.assertTrue(driver.getTitle().contains(Constants.TXTHOME));
        
        logInOutLog.info("Logged into the Salesforce applicaiton successfully");
        
    }

	public void logout() {
		pageRefresh(driver);
		waitforApexpageload();
		waitforElement(driver, profileName);
		profileName.click();
		waitforElement(driver, logoutButton);
		logoutButton.click();
		waitforElement(driver, loginButton);
		loginButton.isDisplayed();
		
		logInOutLog.info("Logged out the Salesforce applicaiton successfully");
	}
	
	
}
