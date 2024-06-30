package example.Steps;

import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DeleteCandidateThruAPI {


    private static ResponseOptions<Response> apiResponse;
    AddCandidateThruAPI object = new AddCandidateThruAPI();

    @When("Delete candidate using {string}")
    public void Delete_Candidate_Using(String endpoint) {
        Integer createdID = object.getCreatedId();
        String jsonAsString = "{\n" +
                "    \"ids\": [\n" +
                "        " + createdID + "\n" +
                "    ]\n" +
                "}";
        Map<String, Object> dataAsMap = new JsonPath(jsonAsString).getMap("");
        //  String v =  cookies.get("orangehrm");
        apiResponse = given()
                .header("Cookie", "orangehrm=4824487ac0924a66085cad72ffa5f10f")
                .contentType("application/json")
                .body(dataAsMap)
                .when()
                .delete(endpoint);
        apiResponse.body().prettyPrint();

    }

    @Then("Assert candidate is Deleted")
    public void assert_candidate_is_deleted() {

        assert apiResponse.getStatusCode() == 200 : "Status code is not 200!";
    }
}
