package com.tests.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties{

	public static String readPropertyData(String key) throws IOException{
		
		  FileInputStream fis=new FileInputStream(new File(
		  "C:\\Users\\kanup\\eclipse-workspace\\TekarchApiExam\\src\\test\\resources\\data.properties"));
		  Properties ob=new Properties(); 
		  ob.load(fis); 
		  String value=ob.getProperty(key); 
		  return value;		 
	}

}
