package com.tests.helper;

import org.testng.ITestResult;
import org.testng.ITestContext ;		
import org.testng.ITestListener ;		
	

import com.tests.utility.GenerateReports;

public class ListnerTest implements ITestListener{
	
	GenerateReports report = GenerateReports.getInstance();

	@Override
	public void onTestStart(ITestResult result) {
		
		report.logTestInfo("Test execution started");
	}
}
