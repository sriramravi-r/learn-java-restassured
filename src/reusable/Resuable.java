package reusable;

import io.restassured.path.json.JsonPath;

public class Resuable {
	public static JsonPath rawToJson(String json) {
		JsonPath jsonPath=new JsonPath(json);
		return jsonPath;
	}
}
