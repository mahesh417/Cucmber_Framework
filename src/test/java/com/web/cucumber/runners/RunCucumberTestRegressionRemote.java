package com.web.cucumber.runners;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import com.web.cucumber.framework.Settings;
import com.web.cucumber.framework.TimeStamp;
import com.web.cucumber.framework.Util;
import com.web.cucumber.utility.TestBase;

import cucumber.api.CucumberOptions;


@ExtendedCucumberOptions(jsonReport = "C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion/cucumber.json", jsonUsageReport = "C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion/cucumber-usage.json", outputFolder = "C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion", detailedReport = true, detailedAggregatedReport = true, overviewReport = true, usageReport = true)

/**
 * Please notice that com.web.cucumber.stepDefinations.CukeHooks class is in
 * the same package as the steps definitions. It has two methods that are
 * executed before or after scenario. I'm using to take a screenshot if scenario
 * fails.
 */
/* Feature file is modified by Chiranjeevi*/
@CucumberOptions(features = "C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/src/test/resources/features", glue = { "com.web.cucumber.stepdefinitions" }, tags = {
		"@MaritimeCampaignCreation" }, monochrome = true, plugin = { "pretty", "pretty:C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion/pretty.txt",
				"html:C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion", "json:C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion/cucumber.json",
				"junit:C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion/cucumber-junitreport.xml",
				"com.cucumber.listener.ExtentCucumberFormatter:" })

public class RunCucumberTestRegressionRemote extends TestBase {

	static String resultFolder;
	Properties properties = Settings.getInstance();

	@BeforeSuite
	private void beforeSuite() {
          initiateLogs();
		if ((Boolean.parseBoolean(properties.getProperty("SaveReports")))) {
			resultFolder = TimeStamp.getInstance();
		}
	}

	@BeforeClass
	private void beforeClass() {

		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath("C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/ExtentReport/InmarsatTestExecutionReport.html");
		new File("./target/ExtentReport/screenshots").mkdir();
	}

	@AfterClass
	private void afterClass() {
		Properties prop = Settings.getInstance();
		Reporter.loadXMLConfig(new File("C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/src/test/resources/extent-config.xml"));
		Reporter.setSystemInfo("Project Name", prop.getProperty("ProjectName"));
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("os", System.getProperty("os.name"));
	}

	@AfterTest
	private void afterTest() {
		generateCustomReports();
	}

	@AfterSuite()
	private void afterSuite() {
		if ((Boolean.parseBoolean(properties.getProperty("SaveReports")))) {
			copyReportsFolder();
		}
	}

	private void generateCustomReports() {

		CucumberResultsOverview results1 = new CucumberResultsOverview();
		results1.setOutputDirectory("C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion");
		results1.setOutputName("cucumber-results");
		results1.setSourceFile("C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion/cucumber.json");
		try {
			results1.executeFeaturesOverviewReport();
		} catch (Exception e) {
			log("Custom Reports - OverView report genaration failed, Error :: " + e);
		}

		CucumberDetailedResults detailedResults = new CucumberDetailedResults();
		detailedResults.setOutputDirectory("C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion");
		detailedResults.setOutputName("cucumber-results");
		detailedResults.setSourceFile("C:/Jenkins/workspace/SFDC/AT-SFDC-Current Sprint/SFDC_Cucumber/target/cucumber-report/Regresssion/cucumber.json");
		detailedResults.setScreenShotLocation("./Regresssion");
		try {
			detailedResults.executeDetailedResultsReport(false, false);
		} catch (Exception e) {

			log("Custom Reports - Detailed report genaration failed, Error :: " + e);
		}

	}

	private void copyReportsFolder() {

		File sourceCucumber = new File(Util.getTargetPath());
		File sourceExtent = new File(Util.getTargetExtentReportPath());
		File destination = new File(resultFolder);
		try {
			FileUtils.copyDirectory(sourceCucumber, destination);
			FileUtils.copyDirectory(sourceExtent, destination);

		} catch (IOException e) {
			log("Custom Reports - Fileutils failed due to error :: " + e);	
		}

	}

}
