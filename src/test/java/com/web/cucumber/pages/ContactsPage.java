package com.web.cucumber.pages;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.web.cucumber.utility.Constants;
import com.web.cucumber.utility.TestBase;

/*
 * Author : Chiranjeevi
 * Functionality : New Contact Creation
 * Date : 12-06-2019
 * 
 */

public class ContactsPage extends TestBase {
	static Logger contactLog = Logger.getLogger(ContactsPage.class);
	static String contactRandomName = "";
	static String editContactName = "";

	@FindBy(xpath = "//a[contains(@title,'Contacts Tab')]")
	WebElement btnContactsHomeButton;

	@FindBy(xpath = "//input[@title='New' and @type='button']")
	WebElement btnContactsNew;

	@FindBy(xpath = "(//input[@title='Save'])[1]")
	WebElement btnSave;

	@FindBy(xpath = "//*[contains(text(),'Last Name')]/parent::td//following-sibling::td//input")
	WebElement lastName;

	@FindBy(xpath = "//*[contains(text(),'First Name')]/parent::td//following-sibling::td//input")
	WebElement firstName;

	@FindBy(xpath = "//*[text()='Email']/parent::td//following-sibling::td//input")
	WebElement email;

	@FindBy(xpath = "//*[text()='Lead Source']/parent::td//following-sibling::td//select")
	WebElement leadSource;
	
	@FindBy(xpath = "//*[text()='Lead Source']/parent::td//following-sibling::td//select/option[2]")
	WebElement selectLeadSource;

	String department = "//div[contains(text(),'Department')]//following-sibling::div//li//span//span";

	@FindBy(xpath = "//button[@title='Move selection to Chosen']")
	WebElement departmentSelectionArrow;

	@FindBy(xpath = "(//div[@onmouseover]//strong/parent::div)[1]")
	WebElement accountNameSelection;

	@FindBy(xpath = "//*[text()='Account Name']/parent::td//following-sibling::td//span[@class='lookupInput']//input")
	WebElement accountName;

	@FindBy(xpath = "(//input[@title='Save'])[1]")
	WebElement saveButton;

	@FindBy(xpath = "//input[@placeholder='Search Contacts and more...']")
	WebElement contactSearch;

	@FindBy(xpath = "//div[@role='group']/ul//li[4]")
	WebElement editQuickActionButton;

	@FindBy(xpath = "//a[@title='Edit']")
	WebElement editButton;

	@FindBy(xpath = "//ul//li//a[@title='Delete']")
	WebElement deleteButton;

	@FindBy(xpath = "//button[@title='Delete']/span[text()='Delete']")
	WebElement deleteConfirmButton;

	@FindBy(xpath = "//img[@title='Contact']/parent::div/h2[@class='pageDescription']")
	WebElement contactImg;
	
	@FindBy(xpath = "//img[@title='Contact']/parent::div/h2[@class='pageDescription']")
	WebElement contactTitle;

	@FindBy(xpath = "//div[contains(@class,'NoResults')]")
	WebElement noResultsPage;

	@FindBy(xpath = "//span[contains(text(),'EU Citizen/Resident (GDPR)')]")
	WebElement gDPRLabel;

	@FindBy(xpath = "//span[contains(text(),'EU Citizen/Resident (GDPR)')]/following::a[text()='--None--']")
	WebElement gDPR;

	@FindBy(xpath = "//*[@class='select-options']/ul//li/a[text()='No']")
	WebElement gDPRSelect;

	// WebElements for Contacts and Accounts Mapping/Adding

	@FindBy(xpath = "//span[contains(text(),'Account Name')]//following-sibling::div//a")
	WebElement mappedAccountName;

	public ContactsPage(WebDriver rdriver) {
		driver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	public void contactsHomebtn() {
		contactLog.info("Click on 'Contacts' tab");
		jsClick(driver, btnContactsHomeButton);
		contactLog.info("Clicked on 'Contacts' tab successfully");
	}

	public void contactsPageValidation() {
		contactLog.info("Validating Contact Page has started......");
		waitforElement(driver, btnContactsNew);
		waitforApexpageload();
		String actualContactPageTitle = driver.getTitle();
		contactLog.info("Comparing the Actual Page Title with Expected Page Title");
		assertEquals(actualContactPageTitle, Constants.EXPECTEDCONTACTSPAGETITLE);
		contactLog.info("Expected Title:" + Constants.EXPECTEDCONTACTSPAGETITLE + ";" + "Actual Title:" + actualContactPageTitle);
		contactLog.info("Validating Contact Page has completed");
	}

	public void createNewContact(Map<String, String> testdata, String loginUser) {
		btnContactsNew.click();
		waitforElement(driver, btnSave);

		// Entering LastName:
		String contactName = testdata.get(Constants.LASTNAME);
		contactRandomName = contactName + machineTimeStamp();
		contactLog.info("Entering data to all the mandatory fields");

		waitforElement(driver, lastName);
		lastName.sendKeys(contactRandomName);
		waitinSec(2);
		contactLog.info("New Contact name is: " + contactRandomName);

		// Entering Email ID:
		waitforElement(driver, email);
		email.sendKeys(testdata.get(Constants.EMAIL));

		// Selecting Lead Source
		waitforElement(driver, leadSource);
		selectDropDownbyIndex(leadSource, 2);
		/*
		mouseClick(driver, leadSource);
		waitforElement(driver,selectLeadSource);
		mouseClick(driver, selectLeadSource);
		waitforApexpageload();
*/
		// Selecting Account Name
		waitforElement(driver, accountName);
		accountName.sendKeys(testdata.get(Constants.ACCOUNTNAME));
		waitinSec(3);
		jsClick(driver, accountNameSelection);

		contactLog.info("Data to all the mandatory fields entered successfully");
		waitforApexpageload();
		
		captureRunTimeTestData(Constants.CONTACTMODULE, loginUser, " New Contact Name : " + contactRandomName);
	}

	public void contactCreationValidation() {

		contactLog.info("Contact Validation process started");
		waitforApexpageload();
		waitforElement(driver, contactImg);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		contactImg.click();
		
		assertTrue(contactRandomName.equalsIgnoreCase(contactTitle.getText()));
		contactLog.info(contactRandomName + " is created successfully");

		if (contactRandomName.equalsIgnoreCase(contactTitle.getText())) {
			contactLog.info(contactRandomName + " is created successfully");
		} else {
			contactLog.info("Contact is not created");
			Assert.assertTrue(false);
		}
	}

	public void searchContact() {
		contactLog.info("Contact Search process has started...");
		jsClick(driver, btnContactsHomeButton);
		waitforElement(driver, contactSearch);
		contactSearch.sendKeys(contactRandomName);
		waitinSec(3);
		contactSearch.sendKeys(Keys.ENTER);

		waitforApexpageload();

		if (!driver.findElement(By.linkText(contactRandomName)).isDisplayed()) {
			contactLog.info("Contact does not exisit");
		} else {
			driver.findElement(By.linkText(contactRandomName)).click();
			contactLog.info(contactRandomName + " does exist");
		}
	}

	public void updateContact(String businessUnit) {
		editContactName = "Edit_" + contactRandomName;
		contactLog.info("Update Contact process has started ...");
		waitforElement(driver, editQuickActionButton);
		editQuickActionButton.click();
		waitforElement(driver, editButton);
		editButton.click();
		contactLog.info("Edit Contact window has opened");
		waitforElement(driver, lastName);
		lastName.clear();
		contactLog.info("Updating the Contact Name");
		lastName.sendKeys(editContactName);
		contactLog.info("Edited Contact Name:" + editContactName);
		firstName.sendKeys("FirstNameEdit");
		waitforElement(driver, btnSave);
		waitforApexpageload();
		contactLog.info("Click on 'Save' button");
		jsClick(driver, btnSave);
		contactLog.info("Update Contact process has completed ...");
		waitforApexpageload();
		
		captureRunTimeTestData(Constants.CONTACTMODULE, businessUnit, " Updated Contact Name : " + editContactName);
	}

	public void searchUpdatedContact(String businessUnit) {
		contactLog.info("Updated Contact Search process has started...");
		jsClick(driver, btnContactsHomeButton);
		waitforElement(driver, contactSearch);
		contactSearch.sendKeys(editContactName);
		waitinSec(3);
		contactSearch.sendKeys(Keys.ENTER);

		waitforApexpageload();

		if (!driver.findElement(By.linkText(editContactName)).isDisplayed()) {
			contactLog.info("Contact Creation : " + editContactName + "  does not exist");
		} else {
			driver.findElement(By.linkText(editContactName)).click();
			contactLog.info("Contact Creation : " + editContactName + "  does exist");
		}
		
		captureRunTimeTestData(Constants.CONTACTMODULE, businessUnit, " Contact Name to be Deleted : " + editContactName);
	}

	public void contactUpdateValidation() {
		contactLog.info("Contact Update Validation process started....");
		assertNotEquals(contactRandomName, editContactName);
		contactLog.info("Contact Name (Before Update):" + contactRandomName);
		contactLog.info("Contact Name (After Update):" + editContactName);
	}

	public void contactSave() {
		waitforElement(driver, btnSave);
		contactLog.info("Click on 'Save' button");
		jsClick(driver, btnSave);
		contactLog.info("Save button clicked");
		waitforApexpageload();
	}

	public void deleteContact() {

		contactLog.info("Contact Deletion process started....");

		waitforElement(driver, editQuickActionButton);
		editQuickActionButton.click();
		waitforElement(driver, deleteButton);
		deleteButton.click();
		waitforElement(driver, deleteConfirmButton);
		deleteConfirmButton.click();
		contactLog.info(editContactName + "is successfully deleted");
	}

	public void contactDeletionValidation() {

		contactLog.info("Contact deletion validation process has started...");
		contactLog.info("Searching the deleted Contact");

		waitforApexpageload();
/*		waitforElement(driver, globalSearch);

		globalSearch.click();
		globalSearch.clear();
		globalSearch.sendKeys(editContactName);
		globalSearch.sendKeys(Keys.ENTER);*/
		waitforApexpageload();
		waitforElement(driver, noResultsPage);

		assertTrue(noResultsPage.isDisplayed());
		log("Campaign : " + editContactName + " deleted Successfully");
	}

	// Contacts and Account Mapping Validation
	public void mappingContactAndAccountValidation() {

		waitforElement(driver, mappedAccountName);
		String mappedAccount = mappedAccountName.getText();
		log("Contact :  " + contactRandomName + " Mapped to Account :  " + mappedAccount);
		captureRunTimeTestData("Assocate Contact & Account", "", contactRandomName+ " -- " +mappedAccount );

	}

}
