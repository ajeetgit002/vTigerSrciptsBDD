 package evs.vTiger.TestRunner;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;




@RunWith(Cucumber.class)
@CucumberOptions(
		
		features= {".//Feature/Loginfeature.feature"},
		glue= "evs\\vTiger\\StpeDefi",
		dryRun=true,
				//plugin={"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		plugin= {"pretty","html:target/Reports/reports.html"}
		)


public class Run {
	 
	
	
}
