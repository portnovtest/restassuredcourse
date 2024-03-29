import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AuthenticationTests {

    @BeforeClass
    public static void setup(){
        RestAssured.proxy("localhost", 8888);
    }

    @Test
    public void basicPreemptiveTest(){
        given().auth().preemptive().basic("username","password")
                .when()
                .get("http://localhost:8080/someEndpoint");
    }

    @Test
    public void basicChallengedAuthTest(){
        given().auth().basic("username","password")
                .when()
                .get("http://localhost:8080/someEndpoint");
    }

    @Test
    public void oauth1Test(){
        given().auth().oauth("consumerKey","consumerSecret","accessToken",
                "secretToken")
                .when()
                .get("http://localhost:8080/someEndpoint");
    }

    @Test
    public void oauth2Test(){
        given().auth().oauth2("accessToken")
                .when()
                .get("http://localhost:8080/someEndpoint");
    }

    @Test
    public void relaxedHTTPSTest(){
        given().relaxedHTTPSValidation()
                .when()
                .get("https://localhost:8080/someEndpoint");
    }

    @Test
    public void keyStoreTest(){
        given().keyStore("/pathToJKS","password")
                .when()
                .get("https://localhost:8080/someEndpoint");
    }
}
