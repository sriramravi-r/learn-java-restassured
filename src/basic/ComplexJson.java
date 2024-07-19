package basic;

import groovy.json.JsonOutput;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import payload.Payload;
import reusable.Resuable;

public class ComplexJson {
    public static void main(String[] args) {
        JsonPath jsonPath=Resuable.rawToJson(Payload.CoursePrice());
        //print no of course returned by api
        System.out.println(jsonPath.getInt("courses.size()"));
        //print purchase amount
        System.out.println(jsonPath.getInt("dashboard.purchaseAmount"));
        //print title of first course
        System.out.println(jsonPath.getString("courses[0].title"));
        //print all courses and respective titles
        for(int i=0;i<=jsonPath.getInt("courses.size()")-1;i++){
            System.out.print(jsonPath.getString("courses["+i+"].title")+" ");
            System.out.print(jsonPath.get("courses["+i+"].price").toString());
            System.out.println("");
        }
        //print no of course sold by RPA courses
        for(int i=0;i<=jsonPath.getInt("courses.size()")-1;i++){
            //System.out.print(jsonPath.getString("courses["+i+"].title"));
            String courseTitle=jsonPath.getString("courses["+i+"].title");
            if(courseTitle.equalsIgnoreCase("RPA")){
                //copies sold
                System.out.println("no of course sold by RPA courses: "+jsonPath.getInt("courses[" + i + "].copies"));
                break;
            }
        }
        //sum of all courses
        int sum=0;
        for(int i=0;i<jsonPath.getInt("courses.size()");i++){
            int coursePrice=jsonPath.getInt("courses["+i+"].price");
            int coursecopies=jsonPath.getInt("courses["+i+"].copies");
            int amount=coursecopies*coursePrice;
            sum+=amount;
        }
        int purchaseAmount=jsonPath.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum,purchaseAmount);
    }
}
