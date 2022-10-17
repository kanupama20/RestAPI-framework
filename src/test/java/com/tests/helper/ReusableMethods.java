package com.tests.helper;

import io.restassured.response.Response;

public class ReusableMethods extends ReadProperties{

	public static void validate_status_code(Response res, int code){
		res.then().statusCode(code);
	}
	
}
