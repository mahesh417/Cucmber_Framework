package com.web.cucumber.framework;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.web.cucumber.utility.TestBase;
import cucumber.api.Scenario;

public class TestHarness extends TestBase {

	static Logger log = Logger.getLogger(TestHarness.class);

	/**
	 * Constructor to initialize the {@link TestHarness} object
	 */
	public TestHarness() {
		log.info("Starting test execution");
	}

	public void invokeDriver(Scenario scenario, SeleniumTestParameters currentTestParameters) {
		WebDriver driver;
		log.info("Running the Scenario : " + scenario.getName() + " in " + currentTestParameters.getExecutionMode());

		switch (currentTestParameters.getExecutionMode()) {

		case API:
			break;
		case LOCAL:
		case GRID:
			driver = DriverFactory.createWebDriverInstance(currentTestParameters);
			DriverManager.setWebDriver(driver);
			break;
		default:
			break;

		}
	}

	public void closeRespestiveDriver(SeleniumTestParameters testParameters, Scenario scenario) {

		if (!testParameters.isAPIExecution()) {
			WebDriver driver = DriverManager.getWebDriver();
			if (driver != null) {
				if(scenario.isFailed())
				{
					scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");					
					scenario.write("Above scenario failed");
					commentInTextFile("Status ::  Failed");
				}
				else
				{
					scenario.write("Scenario passed");
					commentInTextFile("Status ::  Passed");
				}
				driver.quit();
			}
		}

	}
}
