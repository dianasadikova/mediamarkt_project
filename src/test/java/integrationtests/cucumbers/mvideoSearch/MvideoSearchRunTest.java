package integrationtests.cucumbers.mvideoSearch;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"integrationtests.cucumbers.mvideoSearch",
                "cucumber.api.spring"},
        tags = "@mvideoSearch")
public class MvideoSearchRunTest {
}
