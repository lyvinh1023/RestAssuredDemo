package tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class ExampleSOAPRequests {

	@BeforeTest
	public void setUp() {
		baseURI = "http://www.dneonline.com";
	}
	
	@Test
	public void testAddTwoNumbers() throws IOException {
		File file = new File("./src/test/resources/Add.xml");
		
		if (file.exists()) {
			System.out.println("file exist.................");
		}
		
		FileInputStream fileInputStream = new FileInputStream(file);
		String requestBody = IOUtils.toString(fileInputStream, "UTF-8");
		
		given().
			contentType("text/xml").
			accept(ContentType.XML).
			body(requestBody).
		when().
			post("/calculator.asmx").
		then().
			log().all().
			statusCode(200).
			body("//*:AddResult.text()", equalTo("5"));
	}
	
	@Test
	public void testMultiplyTwoNumbers() throws IOException {
		String requestBody = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
				+ "    <Body>\r\n"
				+ "        <Multiply xmlns=\"http://tempuri.org/\">\r\n"
				+ "            <intA>7</intA>\r\n"
				+ "            <intB>4</intB>\r\n"
				+ "        </Multiply>\r\n"
				+ "    </Body>\r\n"
				+ "</Envelope>";
		
		given().
			contentType("text/xml").
			accept(ContentType.XML).
			body(requestBody).
		when().
			post("/calculator.asmx").
		then().
			log().all().
			statusCode(200).
			body("//*:MultiplyResult.text()", equalTo("28"));
	}
}
