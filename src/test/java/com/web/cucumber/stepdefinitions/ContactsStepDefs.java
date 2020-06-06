package com.web.cucumber.stepdefinitions;

import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.web.cucumber.framework.DriverManager;
import com.web.cucumber.framework.Util;
import com.web.cucumber.pages.ContactsPage;
import com.web.cucumber.utility.Constants;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;


public class ContactsStepDefs extends MasterStepDefs {

	public static final Logger CONTACTSTEPDEFLOG = Logger.getLogger(ContactsStepDefs.class);
	WebDriver contactDriver = DriverManager.getWebDriver();
	ContactsPage contactPage;
	Map<String, String> testdata = null;
	

	@Then("^user able to create a new contact for (.*)$")
	public void createContact(String loginUser) {
		contactPage = new ContactsPage(contactDriver);
		contactPage.contactsHomebtn();
		contactPage.contactsPageValidation();
		try {
			testdata = excelReader("Contact", "TestData");
		} catch (Exception e) {

			log(Constants.LOG_FILENOTFOUND + e);
		}
		contactPage.createNewContact(testdata,loginUser);
		currentScenario.embed(Util.takeScreenshot(contactDriver), Constants.IMAGE);
		contactPage.contactSave();
	}

	@And("^validate contact creation is successful$")
	public void validateContactCreateSuccessful() {
		contactPage = new ContactsPage(contactDriver);
		contactPage.contactCreationValidation();
		currentScenario.embed(Util.takeScreenshot(contactDriver), Constants.IMAGE);
	}

	@Then("^user able to search for a contact$")
	public void searchContact() {
		contactPage = new ContactsPage(contactDriver);
		contactPage.searchContact();
		currentScenario.embed(Util.takeScreenshot(contactDriver), Constants.IMAGE);
	}

	@And("^validate contact update is successful for (.*)$")
	public void updateContact(String businessUnit) {
		contactPage = new ContactsPage(contactDriver);
		contactPage.updateContact(businessUnit);
		contactPage.contactUpdateValidation();
		currentScenario.embed(Util.takeScreenshot(contactDriver), Constants.IMAGE);

	}

	@Then("^user able to search for an updated contact for (.*)$")
	public void searchUpdateContact(String businessUnit) {
		contactPage = new ContactsPage(contactDriver);
		contactPage.searchUpdatedContact(businessUnit);
	}

	@And("^delete contact$")
	public void deleteContact() {
		contactPage = new ContactsPage(contactDriver);
		contactPage.deleteContact();
		currentScenario.embed(Util.takeScreenshot(contactDriver), Constants.IMAGE);
	}

	@And("^validate contact deletion is successful$")
	public void validateDeleteContact() {
		contactPage = new ContactsPage(contactDriver);
		contactPage.contactDeletionValidation();
		currentScenario.embed(Util.takeScreenshot(contactDriver), Constants.IMAGE);
	}

	/*
	 * Note : As per the BDD functionality, We can Associate Multiple feature Steps
	 * to Single Step definition methods
	 * 
	 * @Param method remain call only once's based on the step tag
	 */

	// Account and Contact Association methods

	@Then("^user create Contact by selecting Account$")
	public void existingContact(String businessUnit) {
		log("Existing Contact process");
		contactPage = new ContactsPage(contactDriver);
		contactPage.contactsHomebtn();
		contactPage.contactsPageValidation();
		try {
			testdata = excelReader("Contact", "TestData");
		} catch (Exception e) {

			log(Constants.TESTDATAFILENOTFOUND + Constants.CONTACTMODULE + e);
		}
		contactPage.createNewContact(testdata,businessUnit);
		currentScenario.embed(Util.takeScreenshot(contactDriver), Constants.IMAGE);
		contactPage.contactSave();
	}


	@Then("^validate contact is mapped to the Account Successfully$")
	public void validatingAccountAndContactMapping() {
		log("Validating Account and Contact Mapping process");
		contactPage = new ContactsPage(contactDriver);
		contactPage.mappingContactAndAccountValidation();
		currentScenario.embed(Util.takeScreenshot(contactDriver), Constants.IMAGE);

	}
}
