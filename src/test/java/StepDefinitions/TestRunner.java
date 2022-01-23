package StepDefinitions;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/Features",
glue={"StepDefinitions"},
plugin={"pretty", 
		"junit:target/JunitReports/report.xml" ,
		"json:target/CucumberReports/report.json",
		"html:target/HtmlReports/report.html"},
monochrome=true
)
public class TestRunner {
	
}
