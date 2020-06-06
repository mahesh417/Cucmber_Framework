package com.web.cucumber.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import cucumber.api.testng.AbstractTestNGCucumberTests;

/*
Contributors : MadhuSekhar & siva Arivalagan
Email : madhu.boya@qualitestgroup.com & sivasurya.prasand@qualitestgroup.com
Functionality : Reusable methods across execution level
Date : 27-may-2019 to 4-sep-2019
*/

public abstract class TestBase extends AbstractTestNGCucumberTests {

	protected WebDriver driver;
	Logger logger = Logger.getLogger(this.getClass());
	private String log4jpath = Constants.LOG4JDIR;
	String textfile = Constants.TESTDATARESULTSDIR;

	/**
	 * Method : To initiate Logs
	 */
	public void initiateLogs() {
		PropertyConfigurator.configure(log4jpath);
	}

	/**
	 * Function to verify whether the specified text file exists Returns only true
	 * or false, if False, it will create fresh text file in mentioned location
	 * 
	 */
	public void initializeTestData() {

		File file = new File(textfile);
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					log("TestDataResults.txt already exists and will be overridden");
				}
			} catch (IOException e) {
				log(Constants.LOG_FILENOTFOUND + e);
			}
			log("Text File Created");
		} else {
			log("Text File already Exists");
		}

	}

	/**
	 * Method to Capture runtime data
	 */
	public void captureRunTimeTestData(String module, String businessunit, String data) {
		try (FileWriter fw = new FileWriter(textfile, true); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write("Module: " + module + " | BU: " + businessunit + " | " + data);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			log(Constants.LOG_FILENOTFOUND + e);
			log(Constants.LOG_WRITEOPERFAILED);
		}
	}

	/**
	 * Method to Print text in ".src//..//TestDataResults.txt"
	 */
	public void commentInTextFile(String text) {
		try (FileWriter fw = new FileWriter(textfile, true); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(text);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			log(Constants.LOG_FILENOTFOUND + e);
			log(Constants.LOG_WRITEOPERFAILED);
		}
	}

	/**
	 * Line separator used in TextFile updater of ".src//..//TestDataResults.txt" Results section of Reporting after Test Execution
	 */
	public void lineSeparator() {

		try (FileWriter fw = new FileWriter(textfile, true); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(
					"*******************************************************************************************************************************");
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			log(Constants.LOG_FILENOTFOUND + e);
			log(Constants.LOG_WRITEOPERFAILED);
		}
	}

	/**
	 * Function to verify whether the specified text file exists Returns only true
	 * or false, if true, it will delete the text file in mentioned location
	 */
	public void deleteTextFile() {
		File file = new File(textfile);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * Method : Wait until the element get visible under the page
	 */
	public void waitforElement(WebDriver driver, WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	/**
	 * Function to * * * Check whether the specified WebElement "exists in the DOM
	 * Level irrespective of it whether it is visible or not" - Returns only true or
	 * false * * * By default returns true if "WebElement is available at DOM Level
	 * irrespective of it whether it is visible or not" * * * You need to keep
	 * Asserts in script accordingly to handle true and false Scenario
	 */
	public boolean isElementPresent(WebDriver driver, WebElement element) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 4);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Function to * * * is Element Present on the page or not * * * By default
	 * returns true if "One or More Object of the same type found" * * * You need to
	 * keep Asserts in script accordingly to handle true and false Scenario
	 */
	public boolean isElementPresent(WebDriver driver, By by) {
		int size = driver.findElements(by).size();

		if (size != 0) {
			log("One or more element found with the Object: Return True");
			return true;
		} else {
			log("One or more element not found with the Object: Return False");
			return false;
		}
	}

	/**
	 * Function to Click an element eventhough it is not visible on the page for clicking
	 * example: Even though the web page is not properly loaded, this jsClick method will help to click any WebElement hidden in the DOM of unproper loaded page content
	 * keep Asserts in script accordingly to handle true and false Scenario
	 */
	public void jsClick(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

	}

	/**
	 * Method : Wait until the text get visible under the page
	 */
	public void waitForTextToAppear(WebDriver driver, String textToAppear, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
	}

	/**
	 * Method : Wait until the element turns into click able mode under the page
	 */
	public void waitUntillElementClickable(WebDriver driver, WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		wait.until(ExpectedConditions.invisibilityOf(element));

	}

	/**
	 * Function to * * * Verify whether the specified WebElement is "Click-able" -
	 * Returns only true or false * * * By default returns true if "WebElement
	 * Click-able" * * * You need to keep Asserts in script accordingly to handle
	 * true and false Scenario
	 */
	public boolean isClickable(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method : it will wait until the desired element get invisible (until the
	 * element vanish)
	 */
	public void waitUntillElementInvisible(WebDriver driver, WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOf(element));

	}

	/**
	 * Method : Page get Refresh
	 */
	public void pageRefresh(WebDriver driver) {
		driver.navigate().refresh();
		waitForPageToLoad(driver);
		waitinSec(3);

		scrollDown(driver, 1000);
		waitinSec(3);
		scrollUp(driver, 1000);
	}

	/**
	 * Method : Navigation back from original page
	 */
	public void pageNavigateBack(WebDriver driver) {
		driver.navigate().back();
		waitForPageToLoad(driver);
		waitinSec(3);

		scrollDown(driver, 1000);
		waitinSec(3);
		scrollUp(driver, 1000);
	}

	/**
	 * @category Statement will wait to execute until page to load is complete
	 */
	public void waitForPageToLoad(WebDriver driver) {
		try {
			Object result = ((JavascriptExecutor) driver).executeScript("return document['readyState']");
			while (!result.toString().equalsIgnoreCase("complete")) {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				result = ((JavascriptExecutor) driver).executeScript("return document['readyState']");
			}
		} catch (Exception e) {
			log("unable to Load the DOM Elements " + e);
		}
	}

	/**
	 * Method : Navigation forward from original page
	 */
	public void pageNavigateForword(WebDriver driver) {
		driver.navigate().forward();
	}

	/**
	 * Method : To Handle the drop downs, method is to select the value from drop
	 * down list through value reference
	 */
	public void selectDropDownbyValue(WebElement element, String value) {
		WebElement mySelectElement = element;
		Select sel = new Select(mySelectElement);
		sel.selectByValue(value);
	}

	/**
	 * Method : Navigation forward from original page
	 */
	/*
	 * Method : To Handle the drop downs, method is to select the value from drop
	 * down list through index reference
	 */

	public void selectDropDownbyIndex(WebElement element, int index) {
		WebElement mySelectElement = element;
		Select sel = new Select(mySelectElement);
		sel.selectByIndex(index);
	}

	/**
	 * Method : To generate Random String value
	 */
	public String randomString(int count) {
		return RandomStringUtils.randomAlphabetic(count);

	}

	/**
	 * Method : To generate Random integer value
	 */
	public String randomInteger(int count) {
		return RandomStringUtils.randomNumeric(count);

	}

	/**
	 * Method : To generate Random Alpha Numeric value
	 */
	public String randomAlphaNumeric(int count) {
		return RandomStringUtils.randomAlphanumeric(count);

	}

	public String randomAlphaNumericUpperCase(int count) {
		return RandomStringUtils.randomAlphanumeric(count).toUpperCase();

	}

	/**
	 * Method : To generate system current date
	 */

	public String machineTimeStamp() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("_yyyy/MM/dd_HHmmss");
		LocalDateTime now = LocalDateTime.now();
		String todaydate = dtf.format(now);
		log("Capture Time is :" + todaydate);
		return todaydate;
	}

	/**
	 * Method : Method is to wait until the frame get load
	 */
	public WebElement waituntilframeload(WebDriver driver, WebElement element, long timeOutInSeconds,
			String frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		return element;

	}

	/**
	 * Method : Handling the scrolling feature, Method will do scroll until the
	 * element visible
	 */

	public void scrollToElement(WebDriver driver, WebElement elemet) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elemet);

	}

	/**
	 * Method : Handling the scrolling feature, Method will do scroll down the page
	 * 
	 */
	public void scrollDown(WebDriver driver, int down) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight," + down + ")");

	}

	/**
	 * Method : Handling the scrolling feature, Method will do scroll up the page
	 * 
	 */
	public void scrollUp(WebDriver driver, int up) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,-" + up + ")");

	}

	/**
	 * Method : Handling the scrolling feature, Method will do scroll left to the
	 * page
	 */
	public void scrollLeft(WebDriver driver, int left) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(-" + left + ",0)", "");
	}

	/**
	 * Method : Handling the scrolling feature, Method will do scroll right to the
	 * page *
	 */
	public void scrollRight(WebDriver driver, int right) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(" + right + ",0)", "");

	}

	/**
	 * Method : Method to double click the desired element
	 */
	public void doubleclick(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).doubleClick().perform();
	}

	/**
	 * Method : Mouse hover to an element
	 */
	public void mouseHoverToElement(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	/**
	 * @category Click an element using mouse actions
	 *            
	 */
	public void mouseClick(WebDriver driver, WebElement element) {
		if ((driver != null) && (element != null)) {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
			action.moveToElement(element).click().build().perform();
		}
	}

	/**
	 * Method : Handling the frame feature, Method to switch the frame based on
	 * element mapping
	 */
	public void switchtoframeByWebElement(WebDriver driver, WebElement element) {
		driver.switchTo().frame(element);
	}

	/**
	 * Method : Handling the frame feature, Method to switch the frame based on
	 * index/value reference
	 */
	public void iframeswitch(WebDriver driver, int i) {

		driver.switchTo().frame(i);
	}

	/**
	 * Method : To switch back original reference index/value reference
	 */
	public void switchtoDefault() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Method : To wait for 4 secs
	 */
	public void waitforApexpageload() {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			log("Page Exception : Unable to load page properly : " + e);
		}
	}

	/**
	 * Method : To wait for user provided wait time
	 */
	public void waitinSec(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			log("Page Exception : Wait time : " + e);
		}
	}

	/*
	 * Method : Open link in a new tab
	 */
	public void openLinkInNewTab(WebElement element) {
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
		element.sendKeys(selectLinkOpeninNewTab);
	}

	/**
	 * Method : Open link in a new tab
	 */
	public void log(String info) {
		logger.info(info);
	}

	/**
	 * Method : To read Excel data for BDD Framework
	 */
	public Map<String, String> excelReader(String moduleName, String sheetname) throws Exception {

		String excelpath = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData\\TestData.xlsx";
		String filePath = excelpath;
		String expectedDataTCName = moduleName;

		File srcsan = new File(filePath);
		FileInputStream fisan = new FileInputStream(srcsan);
		XSSFWorkbook wbsan = new XSSFWorkbook(fisan);
		XSSFSheet sheetsan1 = wbsan.getSheet(sheetname); // get the data based on sheet name
		// XSSFSheet sheetsan1 = wbsan.getSheetAt(0); //index no can also be used

		int totalRow = sheetsan1.getLastRowNum() + 1;
		int totalColumn = sheetsan1.getRow(0).getLastCellNum();

		String[][] arr = new String[totalColumn][2];

		if (totalRow != 0) {

			for (int j = 0; j < totalColumn; j++) {
				String cellVal = sheetsan1.getRow(0).getCell(j).getStringCellValue();
				arr[j][0] = cellVal;
			}
		} else {
			log("In Testdata.xls - Rows not found");
		}

		for (int i = 0; i < totalRow; i++) {
			String value = sheetsan1.getRow(i).getCell(0).getStringCellValue();
			if (value.equals(expectedDataTCName)) {
				for (int j = 0; j < totalColumn; j++) {
					String cellVal = sheetsan1.getRow(i).getCell(j).getStringCellValue();
					arr[j][1] = cellVal;
				}
			}
		}

		HashMap<String, String> testDataValue = new HashMap<String, String>();
		for (int i = 0; i <= totalColumn - 1; i++) {
			testDataValue.put(arr[i][0], arr[i][1]);
		}

		wbsan.close();
		return testDataValue;

	}

	/**
	 * Function to * * * Select the specified WebElement * * * This is applicable
	 * for the DOM Object which contains 'li' tag * * * You need to pass the
	 * drop-down value
	 */
	public void selectDropDrownListFromulList(WebDriver driver, WebElement element, String value) {
		try {
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			WebElement mySelectElement = element;

			if (mySelectElement != null) {
				List<WebElement> options = mySelectElement.findElements(By.tagName("li"));

				for (WebElement option : options) {
					if (option.getText().equals(value)) {
						option.click(); // click the desired option
						break;
					}
				}
			}

		} catch (NoSuchElementException e) {
			log(Constants.LOG_NOSUCHELEMENTFOUND + e);
			Assert.assertTrue(false, Constants.LOG_NOSUCHELEMENTFOUND);
		} catch (Exception e) {
			log(Constants.LOG_UNEXPECTEDERROROCCURED + e);
			Assert.assertTrue(false, Constants.LOG_UNEXPECTEDERROROCCURED);
		}
	}

	/**
	 * Function to * * * Select the specified WebElement * * * This is applicable
	 * for the DOM Object which contains 'a' tag * * * You need to pass the
	 * drop-down value
	 */
	public void selectValueFromaTag(WebDriver driver, WebElement element, String value) {
		try {
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			WebElement mySelectElement = element;

			if (mySelectElement != null) {
				List<WebElement> options = mySelectElement.findElements(By.tagName("a"));
				for (WebElement option : options) {

					if (option.getText().equals(value)) {
						option.click(); // click the desired option
						break;
					}
				}
			}
		} catch (NoSuchElementException e) {
			log(Constants.LOG_NOSUCHELEMENTFOUND + e);
			Assert.assertTrue(false, Constants.LOG_NOSUCHELEMENTFOUND);
		} catch (Exception e) {
			log(Constants.LOG_UNEXPECTEDERROROCCURED + e);
			Assert.assertTrue(false, Constants.LOG_UNEXPECTEDERROROCCURED);
		}

	}

	/**
	 * Function to * * * Select the specified WebElement from List * * * You need to
	 * pass the drop-down value
	 */
	public void selectValueFromListofElements(WebDriver driver, String xpath, String value) {

		try {
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

			List<WebElement> ele = driver.findElements(By.xpath(xpath));
			for (WebElement elementvalue : ele) {
				if (elementvalue.getText().contains(value)) {
					elementvalue.click();
					break;
				}
			}

		} catch (NoSuchElementException e) {
			log(Constants.LOG_NOSUCHELEMENTFOUND + e);
			Assert.assertTrue(false, Constants.LOG_NOSUCHELEMENTFOUND);
		} catch (Exception e) {
			log(Constants.LOG_UNEXPECTEDERROROCCURED + e);
			Assert.assertTrue(false, Constants.LOG_UNEXPECTEDERROROCCURED);
		}

	}

	/**
	 * Function to * * * find excat WebElement in a Table and click it
	 */
	public void findExactElementToclickonTable(WebDriver driver, String valuetoClick, String valueRelatedRowsXpath,
			String textToFind) {
		int count = driver.findElements(By.xpath(valuetoClick)).size();
		for (int i = 1; i <= count; i++) {
			List<WebElement> ele = driver.findElements(By.xpath(valueRelatedRowsXpath));
			for (WebElement elementvalue : ele) {
				if (elementvalue.getText().contains(textToFind)) {
					String temp = valuetoClick + "[" + i + "]";
					driver.findElement(By.xpath(temp)).click();
					break;
				}
			}

		}
	}

	/**
	 * Function to * * * Check whether the specified WebElement is "not Displayed" -
	 * Returns only true or false * * * By default returns true if "Element Not
	 * Displayed" * * * You need to keep Asserts in script accordingly to handle
	 * true and false Scenario
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean waitUntilElementNotDisplayed(WebDriver driver, final WebElement webElement) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		ExpectedCondition elementIsDisplayed = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver arg0) {
				try {
					webElement.isDisplayed();
					return false;
				} catch (NoSuchElementException e) {
					return true;
				} catch (StaleElementReferenceException f) {
					log("Element not found as expected");
					return true;
				}
			}
		};
		wait.until(elementIsDisplayed);
		return true;
	}

	/**
	 * Function to * * * Check whether the specified WebElement is "Displayed" -
	 * Returns only true or false * * * By default returns true if "WebElement
	 * Displayed" * * * You need to keep Asserts in script accordingly to handle
	 * true and false Scenario
	 */
	public boolean isDisplayed(final WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Function select the Calender date in Future
	 */
	public String calanderHandlerPickFuture(int days) {

		LocalDate date = LocalDate.now().plusDays(days);

		int month = date.getMonthValue();
		int year = date.getYear();
		int day = date.getDayOfMonth();
		return (month + "/" + day + "/" + year);

	}

	/**
	 * Function select the Calender date in Past
	 */
	public String calanderHandlerPickPast(int days) {

		LocalDate date = LocalDate.now().minusDays(days);
		String pastformat;

		int month = date.getMonthValue();
		int year = date.getYear();
		int day = date.getDayOfMonth();

		if (month < 10) {
			pastformat = year + "/" + "0" + month + "/" + day;
		} else {
			pastformat = year + "/" + month + "/" + day;
		}
		return pastformat;

	}

	/**
	 * Function select the Calender date in Past of DDMMYYYY Format
	 */
	public String calanderHandlerPickPastDDMMYYY(int days) {

		LocalDate date = LocalDate.now().minusDays(days);
		String pastformat;

		int month = date.getMonthValue();
		int year = date.getYear();
		int day = date.getDayOfMonth();

		if (month < 10) {
			pastformat = day + "/" + "0" + month + "/" + year;
		} else {
			pastformat = day + "/" + month + "/" + year;
		}
		return pastformat;

	}

	/**
	 * Function select the Calender date in Future of DDMMYYYY Format
	 */
	public String calanderHandlerPickFutureDDMMYYY(int days) {

		LocalDate date = LocalDate.now().plusDays(days);

		int month = date.getMonthValue();
		int year = date.getYear();
		int day = date.getDayOfMonth();
		return (day + "/" + month + "/" + year);
	}

	/**
	 * Function to * * * Check whether the specified WebElement is "Become Stale" -
	 * Returns only true or false * * * By default returns true if "Element has
	 * become Stale" * * * You need to keep Asserts in script accordingly to handle
	 * true and false Scenario
	 */
	public boolean staleElementHandlerToClick(WebElement element) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 5) {
			try {
				element.click();
				result = true;
				break;
			} catch (Exception e) {
				logger.info("Element has become stale");
			}
			attempts++;
		}
		return result;
	}
}
