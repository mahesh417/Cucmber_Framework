package com.web.cucumber.stepdefinitions;

/*
 * Author : 
 * Functinality : Launch URL
 *  
 */

import static org.testng.Assert.assertTrue;
//import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import com.web.cucumber.framework.DriverManager;
import com.web.cucumber.framework.Util;
import com.web.cucumber.utility.Constants;

import cucumber.api.java.en.Given;

public class GeneralStepDefs extends MasterStepDefs {

	WebDriver driver = DriverManager.getWebDriver();
	// int width = 1044;
    // int height = 784;
	// Dimension dimension;
	
	// Comment :: Added Dimension to eliminate screen Resolution 
	
	@Given("^user launch the salesforce application$")
	public void launchSalesforceApplication() {
		log.info("Salesforce Application is about to Launch");
		driver.get(properties.getProperty("ApplicationUrl"));
		log.info("Salesforce Application sucessfully Launched");
		//dimension = new Dimension(width, height);
		driver.manage().window().maximize();
		log.info("Validate the Page Title");
		assertTrue(driver.getTitle().contains("Login"));
		log.info("Page Title is successfully displayed and validated");
		
		currentScenario.embed(Util.takeScreenshot(driver),Constants.IMAGE);
		lineSeparator();
	}
}