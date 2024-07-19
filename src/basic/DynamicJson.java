package basic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import payload.Payload;
import reusable.Resuable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class DynamicJson {
    @Test(dataProvider = "addBookData")
    public void addBook(String name, String isbn, String aisle, String author){
        RestAssured.baseURI="http://216.10.245.166";

        Response response= given()
                .header("content-type","application/json")
                //.body(Payload.AddBook("Learn Appium","abc","722","foe"))
                .body(Payload.AddBook(name,isbn,aisle,author))
                .log().all()
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().extract().response();
        JsonPath jsonPath=Resuable.rawToJson(response.asString());
        String bookId=jsonPath.getString("ID");

        // Delete book
        String delresponse=given()
                .header("content-type","application/json")
                .body(Payload.DeleteBook(bookId))
                .log().all()
                .when().post("/Library/DeleteBook.php")
                .then().log().all()
                .assertThat()
                .extract()
                .response().asString();
        JsonPath jsonPath1=Resuable.rawToJson(delresponse);
        System.out.println(jsonPath1);
    }


    @Test
    public void addBook2() {
        RestAssured.baseURI="http://216.10.245.166";
        Response response1= given()
                .header("content-type","application/json")
                //.body(Payload.AddBook("Learn Appium","abc","722","foe"))
                //.body(Payload.AddBook(name,isbn,aisle,author))
                .body(new File(System.getProperty("user.dir")+"/src/staticjson/addBook.json"))
                .log().all()
                .when().post("/Library/Addbook.php")
                .then().log().all()
                .assertThat().extract().response();
        JsonPath jsonPath=Resuable.rawToJson(response1.asString());
        System.out.println(jsonPath.prettify());
        String bookId=jsonPath.getString("ID");

        //Delete book
        String delresponse=given()
                .header("content-type","application/json")
                .body(Payload.DeleteBook(bookId))
                .log().all()
                .when().post("/Library/DeleteBook.php")
                .then().log().all()
                .assertThat()
                .extract()
                .response().asString();
        JsonPath jsonPath1=Resuable.rawToJson(delresponse);
        System.out.println(jsonPath1);
    }


    //dataprovider utility
    @DataProvider(name="addBookData")
    public Object[][] getData(){
        //array collection of element
        //below multidimensional arrays
        return new Object[][]{
                {"Learn Appium1","cba","272","for"}, //array1
                {"Learn Appium2","abc","212","fos"}, //array2
                {"Learn Appium3","bca","222","foa"}  //array3
        };
    }
}
