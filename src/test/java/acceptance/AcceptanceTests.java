package acceptance;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.junit.annotations.Concurrent;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = {"src/test/specs/financial-status/"},
  // features = {"src/test/specs/income-proving"},
  //  features = {"src/test/specs/income-proving/family/"},
  //  features = {"src/test/specs/income-proving/generic"},
    glue = {"steps"}
)
@Concurrent(threads="2")
public class AcceptanceTests {
}
