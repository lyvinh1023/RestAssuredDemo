package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ExampleRESTRequests {
	@BeforeTest
	public void setUp() {
		baseURI = "http://localhost:3000/";
	}
	
	@Test(priority = 1)
	public void testGetAllUsers() {
		given().
		when().
			get("/users").
		then().
			log().all().
			statusCode(200).
			assertThat().body(matchesJsonSchemaInClasspath("all_users_schema.json"));
	}
	
	@Test(priority = 2)
	public void testGetUser() {
		given().
		when().
			get("/users/1").
		then().
			log().all().
			statusCode(200).
			assertThat().body(matchesJsonSchemaInClasspath("user_schema.json")).
			body("id", equalTo(1));
	}
	
	@Test(priority = 3)
	public void testPostUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Will Smith");
		map.put("job", "Actor");
		JSONObject payload = new JSONObject(map);
		
		given().
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(payload.toJSONString()).
		when().
			post("/users").
		then().
			log().all().
			statusCode(201).
			body("$", hasKey("id")).
			body("name", equalTo("Will Smith")).
			body("job", equalTo("Actor"));
	}
	
	@Test(priority = 4)
	public void testPutUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Bin Lai");
		map.put("job", "Tester");
		JSONObject payload = new JSONObject(map);
		
		given().
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(payload.toJSONString()).
		when().
			put("/users/2").
		then().
			log().all().
			statusCode(200).
			body("id", equalTo(2)).
			body("name", equalTo("Bin Lai")).
			body("job", equalTo("Tester"));
	}
	
	@Test(priority = 5)
	public void testPatchUser() {
		JSONObject payload = new JSONObject();
		payload.put("job", "QA");
		
		given().
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(payload.toJSONString()).
		when().
			patch("/users/3").
		then().
			log().all().
			statusCode(200).
			body("id", equalTo(3)).
			body("job", equalTo("QA"));
	}
	
	@Test(priority = 6)
	public void testDeleteUser() {	
		given().
		when().
			delete("/users/4").
		then().
			log().all().
			statusCode(200);
	}
}
