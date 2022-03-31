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
		baseURI = "https://reqres.in";
	}
	
	@Test
	public void testGetAllUsers() {
		given().
		when().
			get("/api/users").
		then().
			log().all().
			statusCode(200).
			assertThat().body(matchesJsonSchemaInClasspath("all_users_schema.json")).
			body("data.id", hasItems(1,2,3,4,5,6));
	}
	
	@Test
	public void testGetUser() {
		given().
		when().
			get("/api/users/1").
		then().
			log().all().
			statusCode(200).
			assertThat().body(matchesJsonSchemaInClasspath("user_schema.json")).
			body("data.id", equalTo(1));
		}
	
	@Test
	public void testPostUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Vinh Ly");
		map.put("job", "Automation Leader");
		JSONObject payload = new JSONObject(map);
		
		given().
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(payload.toJSONString()).
		when().
			post("/api/users").
		then().
			log().all().
			statusCode(201).
			body("$", hasKey("id")).
			body("name", equalTo("Vinh Ly")).
			body("job", equalTo("Automation Leader"));
	}
	
	@Test
	public void testPutUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Boss");
		map.put("job", "PM");
		JSONObject payload = new JSONObject(map);
		
		given().
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(payload.toJSONString()).
		when().
			put("/api/users/2").
		then().
			log().all().
			statusCode(200).
			body("$", hasKey("updatedAt")).
			body("name", equalTo("Boss")).
			body("job", equalTo("PM"));
		}
	
	@Test
	public void testPatchUser() {
		JSONObject payload = new JSONObject();
		payload.put("name", "Tom");
		
		given().
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(payload.toJSONString()).
		when().
			patch("/api/users/3").
		then().
			log().all().
			statusCode(200).
			body("$", hasKey("updatedAt")).
			body("name", equalTo("Tom"));
		}
	
	@Test
	public void testDeleteUser() {	
		given().
		when().
			delete("/api/users/4").
		then().
			log().all().
			statusCode(204);
	}
}
