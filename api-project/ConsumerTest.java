package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslRequestWithPath;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.dsl.PactDslWithState;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.HashMap;
import java.util.Map;


@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    //Headers
    Map<String, String> headers= new HashMap();

    //Resource path
    String resourcepath="/api/users";

    //create the pact
    @Pact(consumer="UserConsumer",provider="UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        //Headers
        headers.put("Content-Type","application/json");
        //Request and Response Body
        DslPart requestResponseBody=new PactDslJsonBody()
                .numberType("id",123)
                .stringType("firstName","saha")
                .stringType("lastName","sahasra")
                .stringType("email","saha@example.com");
        //Pact
        return builder.given("A request to create user")
                .uponReceiving("A request to create user")
                .method("post")
                .path(resourcepath)
                .headers(headers)
                .body(requestResponseBody)
                .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();

    }

    @Test
    @PactTestFor(providerName="UserProvider",port="8282")
    public void postRequestTest(){
        //MockServer URL
        String mockServer="http://localhost:8282";
        //RequestBody
        Map<String, Object> reqBody=new HashMap();
        reqBody.put("id", 77232);
        reqBody.put("firstName", "sahasra1");
        reqBody.put("lastName","Sahaa");
        reqBody.put("email", "email@example.com");


        //Generate Response
        given().body(reqBody).headers(headers)
                .when().post(mockServer + resourcepath)
                .then().statusCode(201).log().all();





    }


}

