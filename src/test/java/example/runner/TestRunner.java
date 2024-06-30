package example.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/main/resources/feature",
                 glue = "example.Steps",
        plugin = {"pretty", "json:target/cucumber.json"},
        tags = "@API"
)

public class TestRunner extends AbstractTestNGCucumberTests {



}
