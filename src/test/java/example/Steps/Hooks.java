package example.Steps;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class Hooks {

    @Before
    public void set_up_base_url() {

        RestAssured.baseURI = ("https://opensource-demo.orangehrmlive.com/web/index.php/api/v2");
    }
}
