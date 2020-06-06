package com.web.cucumber.framework;

/**
 * Enumeration to represent the mode of execution
 * 
 * @author Inmarsat
 */
public enum ExecutionMode {
	/**
	 * Execute API
	 */
	API,
	/**
	 * Execute on the local machine
	 */
	LOCAL,

	/**
	 * Execute on a selenium grid
	 */
	GRID,
	TESTOBJECT,
	/**
	 * Execute on a Fastest for Cross browser testing
	 */
}