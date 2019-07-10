import config.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class FootballTests extends TestConfig {
    @Test
    public void getAllCompetitionsOneSeason(){
        given()
                .spec(football_requestSpec)
                .queryParam("Season", 2016)
        .when()
                .get("competitions/");
    }

    @Test
    public void getTeamCount_OneComp(){
        given()
                .spec(football_requestSpec)
                .when()
                .get("competitions/?Season=2016")
                .then()
                .body("count", equalTo(148));
    }

    @Test
    public void getFirstTeamName(){
        given()
                .spec(football_requestSpec)
                .when()
                .get("competitions/?Season=2016")
                .then()
                .body("competitions.name[0]", equalTo("WC Qualification"));
    }

    @Test
    public void getAllTeamData(){
        String responseBody = given().spec(football_requestSpec).when().get("competitions/?Season=2016").asString();
        System.out.println(responseBody);
    }

    @Test
    public void getAllTeamData_DoCheckFirst(){
        Response response =
                given()
                    .spec(football_requestSpec)
                .when()
                    .get("competitions/?Season=2016")
                .then()
                    .contentType(ContentType.JSON)
                    .extract().response();
        String jsonResponseAsString = response.asString();
    }

    @Test
    public void extractHeaders(){
        Response response =
                given()
                        .spec(football_requestSpec)
                        .when()
                        .get("competitions/?Season=2016")
                        .then()
                        .contentType(ContentType.JSON)
                        .extract().response();

        Headers headers = response.getHeaders();

        String contentType = response.getHeader("Content-Type");

        System.out.println(contentType);
    }

    @Test
    public void extractFirstTeamName(){
        String firstTeamName = given().spec(football_requestSpec).when()
                .get("competitions/?Season=2016").jsonPath().getString("competitions.name[0]");
        System.out.println(firstTeamName);
    }

    @Test
    public void extractAllTeamNames(){
        Response response = given()
                .spec(football_requestSpec)
                .when()
                .get("competitions/?Season=2016")
                .then()
                .contentType(ContentType.JSON)
                .extract().response();

        List<String> teamNames = response.path("competitions.name");
        for (String teamName : teamNames) {
            System.out.println(teamName);
        }
    }
}
