package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;

import static org.hamcrest.Matchers.lessThan;


public class TestConfig {

    public static RequestSpecification videoGame_requestSpec;
    public static RequestSpecification football_requestSpec;
    public static ResponseSpecification responseSpec;

    @BeforeClass
    public static void setup(){
        RestAssured.proxy("localhost", 8888);
        videoGame_requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(8080)
                .setBasePath("/app/")
                .addHeader("Content-Type","application/xml")
                .addHeader("Accept", "application/xml")
                .build();

        football_requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://api.football-data.org")
                .setBasePath("/v2/")
                .addHeader("X-Auth-Token", "fbfa6eb136ed49d1a22c3888e2d592c4")
                .addHeader("X-Response-Control", "minified")
                .build();

        RestAssured.requestSpecification = videoGame_requestSpec;

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(20000L))
                .build();
        RestAssured.responseSpecification = responseSpec;
    }
}
