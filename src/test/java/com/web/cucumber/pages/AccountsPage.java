package com.web.cucumber.pages;

import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.web.cucumber.utility.Constants;
import com.web.cucumber.utility.TestBase;

import cucumber.api.DataTable;

public class AccountsPage extends TestBase {

	static Logger accountslog = Logger.getLogger(AccountsPage.class.getName());
	Map<String, String> testdata = null;
	DataTable businessunit = null;
	static String runtimeAccountName = "";
	static String bu = "";
	String accountTitle = "";
	private static String product = "Product/ServiceInterest";

	@FindBy(xpath = "//a[contains(@href,'home') and @title='Accounts']")
	WebElement accountHomeButton;

	@FindBy(xpath = "//a[@title='New']")
	WebElement accountNewbutton;

	@FindBy(xpath = "//div[contains(@class,'changeRecordTypeRightColumn')]//span")
	WebElement typeofAccount; // Applicable only for Admin

	@FindBy(xpath = "(//span[contains(text(),'Next')]/parent::button)[2]")
	WebElement accountsPopupNext;

	@FindBy(xpath = "(//span[@class='slds-form-element__label'])[1]")
	WebElement accountSelection;

	@FindBy(xpath = "//span[@class=' label bBody' and contains(text(),'Next')]")
	WebElement accountRecordNext;

	@FindBy(xpath = "//span[contains(text(),'Account Name')]/parent::label//following-sibling::input")
	WebElement accountName;

	// X-paths for Maritime Specific Accounts

	@FindBy(xpath = "//*[contains(text(),'Account Number')]/parent::label//following-sibling::input")
	WebElement accountNumber;

	@FindBy(xpath = "//*[contains(text(),'National Account Code')]/parent::label//following-sibling::input")
	WebElement nationalAccountCode;

	@FindBy(xpath = "//*[contains(text(),'Sales Supporter')]/parent::label//following-sibling::div")
	WebElement salesSupporter;

	@FindBy(xpath = "(//*[contains(text(),'Sales Supporter')]/parent::label//following-sibling::div//a)[1]")
	WebElement salesSupportedValue;

	@FindBy(xpath = "//*[contains(text(),'Sales VP')]/parent::label//following-sibling::div")
	WebElement salesVPSectorHead;

	@FindBy(xpath = "(//*[contains(text(),'Sales VP')]/parent::label//following-sibling::div//a)[1]")
	WebElement salesVPSectorHeadvalue;

	String productserviceInterest = "//*[contains(text(),'Product/Service Interest')]/parent::div//div//span//span";

	@FindBy(xpath = "(//button[contains(@title,'Move selection to Chosen')])[3]")
	WebElement productServiceChoose;

	@FindBy(xpath = "//span[contains(text(),'Region')]/parent::span//following-sibling::div")
	WebElement region;

	String regionvalues = "//div[contains(@class,'uiMenuList')]//ul//a";

	@FindBy(xpath = "//a[@title='Maritime Merchant EMEA/Global Sales']")
	WebElement regionMaritimevalues;

	@FindBy(xpath = "//span[contains(text(),'Type')]/parent::span//following-sibling::div//a")
	WebElement type;

	@FindBy(xpath = "//a[@title='Channel Partner']")
	WebElement typeValues;

	// x-paths Web Elements for Enterprise specific

	@FindBy(xpath = "//*[contains(text(),'Full Legal Name')]//parent::label//following-sibling::input")
	WebElement fullLegalName;

	String relationShip = "//*[contains(text(),'Relationship')]/parent::div//div//span//span";

	@FindBy(xpath = "(//button[contains(@title,'Move selection to Chosen')])[1]")
	WebElement relationShipChoosen;

	@FindBy(xpath = "(//button[contains(@title,'Move selection to Chosen')])[2]")
	WebElement enterpriseProductChoosen;

	// Common WebElements across page level

	@FindBy(xpath = "//textarea[@placeholder='Street']")
	WebElement street;

	@FindBy(xpath = "//input[@placeholder='City']")
	WebElement city;

	@FindBy(xpath = "//input[@placeholder='Zip/Postal Code']")
	WebElement postalcode;

	@FindBy(xpath = "//input[@placeholder='State/Province']")
	WebElement state;

	@FindBy(xpath = "//input[@placeholder='Country']")
	WebElement country;

	@FindBy(xpath = "//button[@title='Save']")
	WebElement accountpopupSave;

	@FindBy(xpath = "//input[contains(@title, 'Search')]")
	WebElement globalSearch;

	@FindBy(xpath = "(//p[contains(text(),'Result')])[1]")
	WebElement resultKey;

	@FindBy(xpath = "(//*[contains(text(),'looks like a duplicate')])[2]")
	WebElement duplicateRecordsFinder;

	@FindBy(xpath = ".//div[contains(@class,'record-home')]//span[@title='Type'][text()='Type']")
	WebElement typeOfUser;

	@FindBy(xpath = ".//div[contains(@class,'record-home')]//span[@title='Account Owner'][text()='Account Owner']")
	WebElement accountOwner;

	// Web elements for Edit Accounts '

	@FindBy(xpath = "//a[@title='Edit']/parent::li")
	WebElement accountsEdit;

	@FindBy(xpath = "//div[contains(@class,'header__title')]//span[@class='custom-truncate uiOutputText']")
	WebElement accountTitleName;

	// WebElements for Account Deletion

	@FindBy(xpath = "//a[contains(@title,'Show')]//lightning-primitive-icon")
	WebElement showbtn;

	@FindBy(xpath = "//a[@title='Delete']")
	WebElement deletaccount;

	@FindBy(xpath = "//span[contains(text(),'Delete')]")
	WebElement deletaccountpopup;

	@FindBy(xpath = "//div[contains(@class,'NoResults')]")
	WebElement noResultsPage;

	public AccountsPage(WebDriver rdriver) {
		driver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	public void accountHomebtn() {
		waitforElement(driver, accountHomeButton);
		jsClick(driver, accountHomeButton);
		log("Accounts Home button clicked");
	}

	public void accountsPageValidations() {

		log("Accounts page validation initiated");
		waitForPageToLoad(driver);
		waitforElement(driver, accountNewbutton);
		String actualAccountsPageTittle = driver.getTitle();
		Assert.assertTrue(actualAccountsPageTittle.contains(Constants.EXPECTEDACCOUNTSPAGETITLE));
		log("Expected Title :" + Constants.EXPECTEDACCOUNTSPAGETITLE + " ::  " + "Actual Title"
				+ Constants.EXPECTEDACCOUNTSPAGETITLE);
	}

	public void newAccountCreation(Map<String, String> testdata, String businessunit) {
		waitforElement(driver, accountNewbutton);
		accountNewbutton.click();
		log("Accounts new button clicked");
		waitforApexpageload();

		waitforElement(driver, accountName);
		bu = businessunit;

		String account = testdata.get(Constants.ACCOUNTNAME);
		String randomAccount = account + businessunit + machineTimeStamp();
		accountName.sendKeys(randomAccount);
		runtimeAccountName = randomAccount;
		log("Account name passed to : " + randomAccount);

		captureRunTimeTestData(Constants.ACCOUNTSMODULE, businessunit, " Created Accountname : " + randomAccount);

		waitforElement(driver, type);
		scrollToElement(driver, type);
		type.click();
		waitforElement(driver, typeValues);
		typeValues.click();
		
		scrollToElement(driver, street);
		street.sendKeys(testdata.get(Constants.STREET));
		city.sendKeys(testdata.get(Constants.CITY));
		state.sendKeys(testdata.get(Constants.STATE));
		postalcode.sendKeys(testdata.get(Constants.ZIPCODE) + randomInteger(4));
		country.sendKeys(testdata.get("Country"));

	}

	public void maritimeAccountCreation(Map<String, String> testdata) {
		waitforElement(driver, accountNumber);
		String randaccountnumber = randomInteger(10);
		accountNumber.sendKeys(randaccountnumber);
		log("MariTime Account -- Account number : " + randaccountnumber);

		String randNationalAccountNumber = randomInteger(10);
		nationalAccountCode.sendKeys(randNationalAccountNumber);
		log("MariTime Account -- National Account Code  : " + randNationalAccountNumber);

		salesSupporter.click();
		waitforElement(driver, salesSupportedValue);
		salesSupportedValue.click();
		log("Sales Supporter had been selected to : Sales Supporter ");

		salesVPSectorHead.click();
		waitforElement(driver, salesVPSectorHeadvalue);
		salesVPSectorHeadvalue.click();
		log("salesVPSectorHeadvalue value has been selected");

		scrollToElement(driver, region);
		region.click();
		scrollToElement(driver, relationShipChoosen);
		selectValueFromListofElements(driver, relationShip, "CAP");
		relationShipChoosen.click();
		log("Maritime Relationship value has been selected");

		waitforElement(driver, productServiceChoose);
		scrollToElement(driver, productServiceChoose);
		selectValueFromListofElements(driver, productserviceInterest, testdata.get(product));
		productServiceChoose.click();
		log("Maritime product selected to : " + testdata.get(product));

	}

	public void save() {
		waitforElement(driver, accountpopupSave);
		accountpopupSave.click();
		waitforApexpageload();

		if (!isElementPresent(driver, duplicateRecordsFinder)) {
			log("New City and Country related Acocunt has been created/updated");
		} else {
			log("Accounts is not Duplicate, Creating/updating one more account with different Test data under Same country and City");
			waitforElement(driver, accountpopupSave);
			accountpopupSave.click();
		}
		log("Account had been created/update Successful");
		waitforElement(driver, accountTitleName);
	}

	public void accountsCreationValidation() {

		// Switching the control to Account Detail page by clicking the Text - Account
		// Title
		waitforElement(driver, accountTitleName);
		mouseClick(driver, accountTitleName);

		accountTitle = accountTitleName.getText();
		Assert.assertTrue(accountTitle.contains(runtimeAccountName));
		log("Expected Title :" + runtimeAccountName + " ::  " + "Actual Title" + accountTitle);

	}

	public void clickSearchedAccount() {

		waitforElement(driver, globalSearch);

		driver.findElement(By.linkText(runtimeAccountName)).click();
		waitForPageToLoad(driver);
		pageRefresh(driver);
		waitforElement(driver, typeOfUser);
		waitforElement(driver, accountOwner);
	}
}
