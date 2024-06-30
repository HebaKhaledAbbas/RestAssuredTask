package example.Steps;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AddCandidateThruAPI {

    private int createdId;
    private static ResponseOptions<Response> apiResponse;
    private Map<String, String> cookies = new HashMap<>();
    ;


    @Given("Login with username {} and password {}")
    public void login_with_username_and_password(String username, String password) {

        String loginPayload = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }", username, password);
        apiResponse = given()
                .header("Content-Type", "application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login");

        cookies = apiResponse.getCookies();
        System.out.println("Cookies: " + cookies);
    }


    @When("Add new candidate using {string}")
    public void user_adding_new_candidates_using(String endpoint) {

        String jsonAsString =
                "{\n" +
                        "    \"firstName\": \"testing\",\n" +
                        "    \"lastName\": \"App\",\n" +
                        "    \"email\": \"test@test.com\",\n" +

                        "}";


        Map<String, Object> dataAsMap = new JsonPath(jsonAsString).getMap("");

        //  String v =  cookies.get("orangehrm");
        //  Header h = new Header("Cookie","orangehrm=".concat(v));

        apiResponse = given()
                //.headers(cookies)
                //  .cookies(cookies)
                // .cookie("orangehrm="+v)
                .header("Cookie", "orangehrm=4824487ac0924a66085cad72ffa5f10f")
                //  .header(h)
                .contentType(ContentType.JSON)
                .body(dataAsMap)
                .when()
                .post(endpoint).then().extract().response();
        apiResponse.body().prettyPrint();
        JsonPath jsonPath = apiResponse.body().jsonPath();
        createdId = jsonPath.getInt("data.id");
        System.out.println("Created ID: " + createdId);

    }

    public int getCreatedId() {
        return createdId;
    }


    @Then("Assert on status code and response body")
    public void assert_on_status_code_and_response_body() {

        assert apiResponse.getStatusCode() == 200 : "Status code is not 200!";

        JsonPath jsonResponse = new JsonPath(apiResponse.body().asPrettyString());
        HashMap<String, String> data = jsonResponse.get("data");
        String actualFirstName = data.get("firstName");
        String actualLastName = data.get("lastName");
        String actualEmail = data.get("email");


        String expectedFirstName = "testing";
        String expectedLastName = "App";
        String expectedEmail = "test@test.com";


        assert actualFirstName.equals(expectedFirstName) : "First name does not match!";
        assert actualLastName.equals(expectedLastName) : "Last name does not match!";
        assert actualEmail.equals(expectedEmail) : "Email does not match!";
    }
}







