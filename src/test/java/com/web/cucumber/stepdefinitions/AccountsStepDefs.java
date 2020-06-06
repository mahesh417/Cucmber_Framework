package com.web.cucumber.stepdefinitions;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.web.cucumber.framework.DriverManager;
import com.web.cucumber.framework.Util;
import com.web.cucumber.pages.AccountsPage;
import com.web.cucumber.utility.Constants;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountsStepDefs extends MasterStepDefs {

	static Logger accountsstepdef = Logger.getLogger(AccountsStepDefs.class);
	AccountsPage accounts;
	WebDriver accountDriver = DriverManager.getWebDriver();
	Map<String, String> testdata = null;
	DataTable table = null;
	private String bussinessUnit = "";

	public void accountsHooks() {
		accounts = new AccountsPage(accountDriver);
		try {
			testdata = excelReader("Accounts", "TestData");
		} catch (Exception e) {
			log(Constants.TESTDATAFILENOTFOUND + Constants.ACCOUNTSMODULE + e);
		}

	}

	@When("^user created a new Account under Accounts page$")
	public void creationOfNewAccount(DataTable table) {

		accountsHooks();
		accounts.accountHomebtn();
		accounts.accountsPageValidations();
		List<List<String>> data = table.raw();
		String tabledata = data.get(1).get(0);
		bussinessUnit = tabledata;
		log("User is able to select the record type '" + bussinessUnit + "'");
		accounts.newAccountCreation(testdata, bussinessUnit);
		currentScenario.embed(Util.takeScreenshot(accountDriver), Constants.IMAGE);

	}

	@Then("^user receives Account creation success message$")
	public void successMSG() {
		accountsHooks();
		accounts.save();
		log("Account has been saved successful");
		currentScenario.embed(Util.takeScreenshot(accountDriver), Constants.IMAGE);
	}

	@When("^user searching the same created Account$")
	public void searchingAccount() {
		log(" Searching the Account");
	}

	@Then("^as result, created account should displayed in Results page$")
	public void accountValidation() {
		log("Account Validation process initiated");
		accountsHooks();
		accounts.accountsCreationValidation();
		currentScenario.embed(Util.takeScreenshot(accountDriver), Constants.IMAGE);
	}
}