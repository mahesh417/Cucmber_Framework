package com.web.cucumber.stepdefinitions;

import java.io.IOException;
import java.util.Properties;

import com.cucumber.listener.Reporter;
import com.web.cucumber.framework.DriverManager;
import com.web.cucumber.framework.Settings;
import com.web.cucumber.framework.TestHarness;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class CukeHooks extends MasterStepDefs {

	static Properties propertiesFile = Settings.getInstance();
	private TestHarness testHarness;

	@Before
	public void setUp(Scenario scenario) {
		

		testHarness = new TestHarness();
		Reporter.addScenarioLog(scenario.getName());
		currentScenario = scenario;
		properties = propertiesFile;
		pauseScript(2);
		currentTestParameters = DriverManager.getTestParameters();
		currentTestParameters.setScenarioName(scenario.getName());
		testHarness.invokeDriver(scenario, currentTestParameters);
	}

	@After
	public void tearDown(Scenario scenario) throws IOException {
		testHarness.closeRespestiveDriver(currentTestParameters, scenario);
	}

}