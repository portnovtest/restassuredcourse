import config.TestConfig;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;

public class GPathJSONTests extends TestConfig {

    @Test
    public void extractMapOfElementsWithFind(){
        Response response = get("competitions?Season=2016");
        Map<String,?> allTeamDataForSingleTeam = response.path("competitions.find { it.name == 'Superliga Argentina'}");
        System.out.println(allTeamDataForSingleTeam);
    }

    @Test
    public void extractSingleValueWithFind(){
        Response response = get("competitions?Season=2016");
        String certainPlayer = response.path("competitions.find { it.name == 'Superliga Argentina'}.plan");
        System.out.println(certainPlayer);
    }

    @Test
    public void extractListOfValuesWithFindAll(){
        Response response = get("competitions?Season=2016");
        List<String> playerNames = response.path("competitions.findAll { it.numberOfAvailableSeasons > 3}.name");
        System.out.println(playerNames);
    }

    @Test
    public void extractSingleValueWithHighestNumber(){
        Response response = get("competitions?Season=2016");
        String playerName = response.path("competitions.max { it.numberOfAvailableSeasons }.name");
        System.out.println(playerName);
    }

    @Test
    public void extractMultipleValuesAndSumThem(){
        Response response = get("competitions?Season=2016");
        int sumOfJerseys = response.path("competitions.collect { it.numberOfAvailableSeasons }.sum()");
        System.out.println(sumOfJerseys);
    }

    @Test
    public void extractMapOfObjectWithFindAndFindAll(){
        String position = "Premier League";
        String nationallity = "TIER_FOUR";

        Response response = get("competitions?Season=2016");
        Map<String,?> playerOfCertainPosition = response.path(
                "competitions.findAll { it.name == '%s'}.find {it.plan == '%s'}", position, nationallity);
        System.out.println(playerOfCertainPosition);
    }

    @Test
    public void extractMultiplePlayers(){
        String position = "Premier League";
        String nationallity = "TIER_FOUR";

        Response response = get("competitions?Season=2016");
        ArrayList<Map<String,?>> allPlayersCertainNation = response.path(
                "competitions.findAll { it.name == '%s'}.findAll {it.plan == '%s'}", position, nationallity);
        System.out.println(allPlayersCertainNation);
    }
}
