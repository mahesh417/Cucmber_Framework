package com.web.cucumber.stepdefinitions;

import java.util.Properties;
import org.apache.log4j.Logger;

import com.web.cucumber.framework.DriverFactory;
import com.web.cucumber.framework.SeleniumTestParameters;
import com.web.cucumber.utility.TestBase;

import cucumber.api.Scenario;

public abstract class MasterStepDefs extends TestBase{

	 Logger log = Logger.getLogger(DriverFactory.class);

	protected static Scenario currentScenario;
	protected static SeleniumTestParameters currentTestParameters;
	protected static Properties properties;

	/**
	 * Function Applicable to Pause the Script, Generic Application
	 * 
	 * @param How_Long_To_Pause
	 */
	protected void pauseScript(int howLongToPause) {
		// convert to seconds
		howLongToPause = howLongToPause * 1000;

		try {
			Thread.sleep(howLongToPause);
		} catch (final InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}