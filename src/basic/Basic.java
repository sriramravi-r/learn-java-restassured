package basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payload.Payload;
import reusable.Resuable;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class Basic {

    public static void main(String[] args) {
    	
    	String place_id="";
    	String address="70 Summer walk, USA";
        // Set base URI for RestAssured
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        
        // case: add place -> update place -> get place and assert the place is updated
        
        // Send POST request to add a place
        String response=given() //.log().all() // it will print all the input log
            .queryParam("key", "qaclick123")
            .header("Content-Type", "application/json") // in given it is a header
            .body(Payload.AddPlace())
        .when()
            .post("maps/api/place/add/json")
        .then() //.log().all()// it will print all the output log
            .assertThat()
            .statusCode(200)
            .body("scope", equalTo("APP")) //body assertion
            .header("Server","Apache/2.4.52 (Ubuntu)") // in then it is a assertion
            .extract().response().asString();
        JsonPath jsonPath=Resuable.rawToJson(response);// new JsonPath(response); // jsonPath help to convert string to json
        place_id=jsonPath.getString("place_id"); // get string will return value for the key
//        System.out.println(place_id);
        
        // Send PUT request to update a place
        given().log().all()
        	.queryParam("key", "qaclick123")
        	.header("Content_Type","application/json")
        	.body(Payload.UpdatePlace(place_id,address))
        .when()
        	.put("maps/api/place/update/json")
        .then().log().all()
        	.assertThat()
        	.statusCode(200)
        	.body("msg",equalTo("Address successfully updated"));
        
        // Send GET request to asset place updated
        given().log().all()
        	.queryParam("key", "qaclick123")
        	.queryParam("place_id",place_id)
        .when()
        	.get("maps/api/place/get/json")
        .then().log().all()
        	.assertThat()
        	.statusCode(200)
        	.body("address",equalTo(address));
//        	.extract().response().asString();
        
    }
}
