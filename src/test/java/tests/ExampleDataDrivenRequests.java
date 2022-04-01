package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;


public class ExampleDataDrivenRequests {
	@BeforeTest
	public void setUp() {
		baseURI = "http://localhost:3000/";
	}
	
	@DataProvider(name = "DataForPost")
	public Object[][] dataForPost() {
		return new Object[][] {
			{"Jonh Terry", "Coach"},
			{"Donal Trump", "Doctor"}
		};
	}
	
	@Test(priority = 1, dataProvider = "DataForPost")
	public void testPostUser(String name, String job) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("job", job);
		JSONObject payload = new JSONObject(map);
		
		given().
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(payload.toJSONString()).
		when().
			post("/users").
		then().
			log().all().
			statusCode(201);
	}
	
	@Parameters({"userId"})
	@Test(priority = 2)
	public void testGetUser(int userId) {
		System.out.println("User ID getting from testng.xml: " + userId);
		given().
		when().
			get("/users/" + userId).
		then().
			log().all().
			statusCode(200).
			assertThat().body(matchesJsonSchemaInClasspath("user_schema.json")).
			body("id", equalTo(userId));
	}
}
