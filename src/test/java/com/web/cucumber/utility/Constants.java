package com.web.cucumber.utility;

public class Constants extends TestBase {
	
	// Paths
	public static final String LOG4JDIR = "C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/src/test/resources/log4j.properties";
	
	// Jenkins Configuration to generate Test Results of textfile format
	// public static final String TESTDATARESULTSDIR = "C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/RunTimeDataLogs/TestDataResults.txt";
	
	// Jenkins Configuration to generate Test Results of textfile formate	public static final String TESTDATARESULTSDIR = "C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/RunTimeDataLogs/TestDataResults.txt";
	public static final String TESTDATARESULTSDIR = "./target/RunTimeDataLogs/TestDataResults.txt";
	
	// Reporting Loggers
	public static final String IMAGE = "image/png";
	public static final String RUNNINGBUINFO = "Running the Bussiness unit as : ";
	public static final String LOG_FILENOTFOUND = "File not Found Exception :: ";
	public static final String LOG_WRITEOPERFAILED = "Unable to perform write Operation";
	public static final String TESTDATAFILENOTFOUND= "Testdata File not found for : ";
	
	
	// Element Loggers
	public static final String LOG_NOSUCHELEMENTFOUND = "No Such Element Found Exception Error Occured: ";
	public static final String LOG_UNEXPECTEDERROROCCURED = "Unable to find Expected Element in DOM : ";
		
	// BU Units
	public static final String LIGHTINGADMIN = "lightingAdmin";
	public static final String SALESOPSUSER = "salesOpsUser";
	
	// Modules 
	public static final String ACCOUNTSMODULE = "Accounts";
	public static final String CONTACTMODULE = "Contacts";
	
	// Common TestData Column Names from Excel sheet

	public static final String EMAIL = "Email";
	public static final String LASTNAME = "LastName";
	public static final String COMPANYNAME = "CompanyName";
	public static final String ACCOUNTNAME = "AccountName";
	public static final String REGION = "Region";
	public static final String STREET = "Street";
	public static final String CITY = "City";
	public static final String STATE = "State";
	public static final String ZIPCODE = "Zip/Postalcode";

	// Expected Web page titles
	public static final String EXPECTEDACCOUNTSPAGETITLE = "Recently Viewed | Accounts | Salesforce";
	public static final String EXPECTEDCONTACTSPAGETITLE = "Contacts: Home ~ Salesforce - Unlimited Edition";
	
	// Direct Texts
	public static final String TXTYES = "Yes";
	public static final String TXTNO = "No";
	public static final String TXTHOME = "Salesforce - Unlimited Edition";
	public static final String TXTACCOUNT = "ACCOUNT";
	public static final String TXTCONTACT = "CONTACT";
	public static final String NULL = "";
}
