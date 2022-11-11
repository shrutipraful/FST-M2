package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GitHubProject {
    //Declare Request specification
    RequestSpecification requestSpec;
    //Global Properties
    String sshKey;
    int sshKeyId;

    @BeforeClass
    public void setUp(){
        //Create new Request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
                .setBaseUri("https://api.github.com")
                .build();
        sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCaQTeMuhUV5hboyKRwTtCqDRghLTrKT5hiqh7szowZZbjsjhISG3V0JLIM00TMwI8/nKB3WhcK6szNy1kAioNVv8kZopmlw6yY8z1Aw+2NZJQWGVldPTIWOlNYdzim+8L9WzB9T4rfsXd4s63NGUgRqCVdnsOYH/67ofaOivHxllHpnYi1elJMPQF8IzuPSGqe2VLx20+vcSTtVSmzdevcgfUihAZlL230izXzyjayzX3p70Z6Ww+ptlJ2r+WBiA1j3HQ+HYUL45ZNRQfBUKuPj9weMcsn545fu17caxa83ncLmPzC/w4khe3hbdAK1xlPCJuuABFw+lUUiKor2zWT";

    }
    @Test(priority=1)
    public void addKeys() {
        Map<String, Object> reqBody =new HashMap();
        reqBody.put("id", 77324);
        reqBody.put("Name", "Doggy");
        reqBody.put("status", "alive");
      //  String reqBody = "{\"title\":\"TestKey\",\"key\":\"" + sshKey + "\"}";
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post("/user/keys");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        sshKeyId = response.then().extract().path("id");

        response.then().statusCode(201);
    }
    @Test(priority=2)
    public void getKeys() {
        Response response = given().spec(requestSpec)
                .pathParam("keyId",sshKeyId)
                .when().get("/user/keys/{keyId}");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        response.then().statusCode(200);
    }
    @Test(priority=2)
    public void deleteKeys() {
        Response response = given().spec(requestSpec)
                .pathParam("keyId",sshKeyId)
                .when().delete("/user/keys/{keyId}");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        response.then().statusCode(204);
    }

}