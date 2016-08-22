package acceptance;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
//    features = {"src/test/specs"},
    features = {"src/test/specs/financial-status/"},
//    features = {"src/test/specs/income-proving"},
//    features = {"src/test/specs/income-proving/family"},
//    features = {"src/test/specs/income-proving/generic"},
    glue = {"steps"}
 //   tags = {"~@WIP", "~@Demo"}
)
public class AcceptanceTests {
}
