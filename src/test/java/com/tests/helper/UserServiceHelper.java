package com.tests.helper;

import java.io.IOException;

import com.tests.constants.EndPoints;
import com.tests.model.Create_Employee_Response_POJO;
import com.tests.model.Data;
import com.tests.model.Get_Eployee_Response_POJO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import com.tests.utility.GenerateReports;

public class UserServiceHelper extends ReusableMethods{

	//public static GenerateReports report = null;
	//public static Logger Logger = LogManager.getLogger(UserServiceHelper.class);
	
	public static String get_base_uri() {
		
		String base_uri = EndPoints.BASE_URI;
		return base_uri;		
	}
	
	public static void validate_get_all_employee(Response res) {
		validate_status_code(res, 200);
		res.prettyPrint();
		res.then().body("status",Matchers.equalTo("success"));
		
		Get_Eployee_Response_POJO res1 = res.as(Get_Eployee_Response_POJO.class);
		String status = res1.getStatus();
		Assert.assertEquals(status, "success");
		System.out.println("Test case 1 pass");
		//report.logTestInfo("All employee reports displayed");
	}
	
	public static void validate_create_employee(Response res) {
		validate_status_code(res, 200);
		res.prettyPrint();
		res.then().body("status",Matchers.equalTo("success"));
		
		Create_Employee_Response_POJO res1 = res.as(Create_Employee_Response_POJO.class);
		Data data = res1.getData();
		String name = data.getName();
		String salary = data.getSalary();
		String age = data.getAge();
		int created_id = data.getId();
		System.out.println("Create employee id = "+created_id);
		System.out.println("Name of the created employee = "+ name);
		Assert.assertEquals(data.getName(), "test");
		Assert.assertEquals(salary, "123");
		Assert.assertEquals(age, "23");	
		//report.logTestInfo("employee created");
	}
	
	public static int  get_Created_id() {
		Response res= null;
		try {
			res = RestAssured.given()
					.body(ReadProperties.readPropertyData("create_user"))
					.contentType(ContentType.JSON)
					.when()
					.post("create");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		validate_status_code(res, 200);
		res.prettyPrint();
		Create_Employee_Response_POJO res1 = res.as(Create_Employee_Response_POJO.class);
		Data data = res1.getData();
		int created_id = data.getId();
		System.out.println("Created id = "+ created_id);
		//report.logTestInfo("returning employee id");
		return created_id;
	}
	
	public static void validate_delete_employee(Response res, int created_id){
		
		validate_status_code(res, 200);
		res.prettyPrint();
		res.then().body("status",Matchers.equalTo("success"));
		  
		String id = res.jsonPath().get("data"); int id1 = Integer.parseInt(id);
		  
		Assert.assertEquals(id1, created_id); 
		String msg = res.jsonPath().get("message"); 
		System.out.println("Message = "+ msg);
		//report.logTestInfo("Deleted an employee data.");
	}
	
	
	public static void validate_delete_nonexisting_employee(Response res) {
		
		validate_status_code(res, 400);
		  res.prettyPrint();
		  res.then().body("status",Matchers.equalTo("error"));
		  String msg = res.jsonPath().get("message");
		  System.out.println("Deleting non existing employee message = "+msg);	
		  //report.logTestInfo("showing correct message for deleting nonexistaning employee.");
	}
	
	public static void validate_single_employee(Response res) {
		
		validate_status_code(res, 200);
		res.prettyPrint();
		res.then().contentType(ContentType.JSON);
		String name = res.jsonPath().get("data.employee_name");
		int age = res.jsonPath().getInt("data.employee_age");
		int salary = res.jsonPath().get("data.employee_salary");
		
		Assert.assertEquals(name, "Garrett Winters");
		Assert.assertEquals(age, 63);
		Assert.assertEquals(salary, 170750);		
		//report.logTestInfo("correct information of selected employee displayed.");
	}
	
}
