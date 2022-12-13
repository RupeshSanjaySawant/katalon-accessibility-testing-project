import com.kms.katalon.core.util.KeywordUtil
import java.text.SimpleDateFormat;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.deque.html.axecore.selenium.ResultType;
import static com.deque.html.axecore.selenium.AxeReporter.getReadableAxeResults;

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory

class Sample {

	@Keyword
	def checkAccessibility() {


		Results results = new AxeBuilder().analyze(DriverFactory.getWebDriver())
		List<Rule> violations = results.getViolations()
		if(violations.size()==0){
			KeywordUtil.logInfo("No Violation Found")
		}

		//String AxeReportPath = System.getProperty("user.dir") + File.separator + "AxeReports" + File.separator

		String AxeReportPath = RunConfiguration.getReportFolder()+ File.separator

		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date())
		String AxeViolationReportPath=AxeReportPath + "AccessibilityViolations_" + timeStamp
		AxeReporter.writeResultsToJsonFile(AxeViolationReportPath,results)
		KeywordUtil.logInfo("Violation Report Path"+ AxeViolationReportPath)

		if(getReadableAxeResults(ResultType.Violations.getKey(),DriverFactory.getWebDriver(),violations) ){
			AxeReporter.writeResultsToTextFile(AxeViolationReportPath, AxeReporter.getAxeResultString())


		}
	}
}