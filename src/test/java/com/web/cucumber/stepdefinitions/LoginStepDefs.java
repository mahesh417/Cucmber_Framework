package com.web.cucumber.stepdefinitions;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import cucumber.api.java.en.When;

import com.web.cucumber.framework.DriverManager;
import com.web.cucumber.framework.Settings;
import com.web.cucumber.framework.Util;
import com.web.cucumber.pages.*;
import com.web.cucumber.utility.Constants;

public class LoginStepDefs extends MasterStepDefs {

	private static Properties properties = Settings.getInstance();
	public static final String LIGHTNINGADMINUSERNAME = properties.getProperty("lightingAdmin_username");
	public static final String LIGHTNINGADMINPASSWORD = properties.getProperty("lightingAdmin_password");
	public static final String SALESOPSUSERNAME 	  = properties.getProperty("salesOps_username");
	public static final String SALESOPSPASSWORD		  = properties.getProperty("lightingAdmin_password");
	
	WebDriver loginDriver = DriverManager.getWebDriver();

	@When("^user able to login$")
	public void userLogin() {
		String scenarioName = currentScenario.getName();
		commentInTextFile("Scenario :: " + scenarioName);

		LoginPage login = new LoginPage(loginDriver);
		if (scenarioName.contains(Constants.LIGHTINGADMIN)) {
			login.entercred(LIGHTNINGADMINUSERNAME, LIGHTNINGADMINPASSWORD);
		}

		else if (scenarioName.contains(Constants.SALESOPSUSER)) {
			login.entercred(SALESOPSUSERNAME, SALESOPSPASSWORD);
		}
		
		log.info("Logged into the Salesforce applicaiton as successfully");
		currentScenario.embed(Util.takeScreenshot(loginDriver), Constants.IMAGE);
	}

	@When("^logout the Application$")
	public void iLogout() {
		LoginPage login = new LoginPage(loginDriver);
		login.logout();
		log.info("Logged out the Salesforce applicaiton as successfully");
		currentScenario.embed(Util.takeScreenshot(loginDriver), "image/png");
		
	}
}
