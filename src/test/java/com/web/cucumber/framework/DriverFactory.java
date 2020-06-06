package com.web.cucumber.framework;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * DriverFactory which will create respective driver Object
 * 
 * @author Madhu
 */
public class DriverFactory {

	static Logger log = Logger.getLogger(DriverFactory.class);

	/**
	 * Function to return the object for WebDriver {@link WebDriver} object
	 * 
	 * @param testParameters
	 * 
	 * @return Instance of the {@link WebDriver} object
	 */
	public static WebDriver createWebDriverInstance(SeleniumTestParameters testParameters) {
		WebDriver driver = null;
		try {
			switch (testParameters.getExecutionMode()) {

			case LOCAL:
				driver = WebDriverFactory.getWebDriver(testParameters.getBrowser());
				break;
				
			default:
				log.error("Unhandled Execution Mode!");
			}
		} catch (Exception ex) {
			log.error("WebDriver Instance failed :: " + ex);
			log.error(ex.getMessage());
		}
		return driver;
	}
}