package evs.vTiger.TestRunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//@RunWith(Cucumber.class)
@CucumberOptions(
		
		features= {".//Feature/Loginfeature.feature"},
		glue= "evs\\vTiger\\StpeDefi",
		dryRun=false,
				plugin={"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		//plugin= {"pretty","html:target/Reports/reports.html"}
		)


public class Run extends AbstractTestNGCucumberTests {
	 
	
	
}
