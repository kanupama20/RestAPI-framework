package com.tests.test;

import java.io.IOException;
import java.lang.reflect.Method;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.tests.constants.EndPoints;
import com.tests.helper.ReadProperties;
import com.tests.helper.UserServiceHelper;
import com.tests.utility.GenerateReports;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class TekarchApiExamScript extends UserServiceHelper{
	
	@BeforeTest

	public static void setupBeforeTest() {
		report = GenerateReports.getInstance();
		report.startExtentReport();

	}
	
	@BeforeMethod
	public static void setup(Method m) {
		
		RestAssured.baseURI = get_base_uri();
		report.startSingleTestReport(m.getName());
	}
	
	@Test
	public static void TC_01_get_all_Employee() {
		
		Response res = RestAssured.given()
		.when()
		.get(EndPoints.GET_ALL_EMPLOYEE);		
		validate_get_all_employee(res);
		report.startSingleTestReport("TC_01_get_all_Employee");
		report.logTestInfo("TC01 pass.");
	}

	@Test
	public static void TC_02_create_employee() {
		
		Response res = null;
		try {
			res = RestAssured.given()
					
					.body(ReadProperties.readPropertyData("create_user"))
					.contentType(ContentType.JSON)
					.when()
					.post(EndPoints.CREATE_EMPLOYEE);
		} catch (IOException e) {
				e.printStackTrace();
		}
		validate_create_employee(res);
		report.logTestInfo("TC02 pass.");
	}
		
	@Test
	public static void TC_03_delete_employee() {		
		
		int created_id = get_Created_id();
		String endpoint = EndPoints.DELETE_EMPLOYEE	+ Integer.toString(created_id);
		System.out.println("End point = "+endpoint);
		
		  Response res = RestAssured.given() .when() .delete(endpoint);
		  validate_delete_employee(res, created_id);
		  report.logTestInfo("TC03 pass.");
	}
	
	@Test
	public static void TC_04_delete_nonExisting_employee(){
		
		 Response res = RestAssured.given()
				 .when()
				 .delete(EndPoints.DELETE_NONEXISTING_EMPLOYEE);
		 
		 validate_delete_nonexisting_employee(res);
		 report.logTestInfo("TC04 pass.");	
	}
	
	@Test
	public static void TC_05_get_single_Employee() {
		
		Response res = RestAssured.given()
				.when()
				.get(EndPoints.GET_SINGLE_EMPLOYEE);
		
		validate_single_employee(res);
		report.logTestInfo("TC05 pass.");
	}
	
	@AfterTest
	public static void teardown() {
		report.endReport();
		}
	
	
}
