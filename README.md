## RUN ACCESSIBILITY TESTS ON YOUR WEBSITES
 **Accessibility Test Automation in Katalon
 Using The Axe-Core Library In Katalon Platform (Doc V1.0)**

- Overview
      This document provides you with the step-by-step guide to achieve accessibility testing using Katalon Automation Platform on your website. Axe is a fast and lightweight accessibility testing tool that checks the entire document against the rules and generates a report with all violations, passes, etc.It checks if your website follows the WCAG and other guidelines (as supported by axe-core library).
In this guide, you will learn:
How axe-core library is used with Katalon Studio to achieve Accessibility testing
What are the components of the generated report

- Prerequisites

      Before you can integrate your Katalon Studio tests with and generate reports using the axe-core library, ensure that the following prerequisites are complete:

- Download the axe-core jar file and save it in your local system and add it to Katalon Studio Project >> Settings >> Library Management . 


You will use this file to import required packages in the sample code snippets.

-- Run Your First Accessibility Test
 
     You can add the following custom keyword script, the accessibility checks are invoked using the axe-core library in this script. The generated report is saved in a JSON and TXT file format as per the path provided in the script (In Runtime report Folder).

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



Note: You can also write assertions in your script based on the violations in the report.json and mark your test passed or failed based on these assertions.

-- You can use this custom keyword in the test case Add>>Custom Keyword after you navigate to your desired pages on the website. You can use this keyword for different pages of the website as you navigate as per your flow.  You can execute on any browser (Chrome, Mozilla Firefox, Edge Chromium, Safari).



-- After adding the test case in the test suite, You can execute and see the accessibility results in JSON and TXT format will get generated in the report folder, the same can get uploaded in TestOps as well.






- Understand Components of The Report 

    After you successfully run the test script, a text and json file are generated as per the path set in the script. 

-- The report in TXT file contains the violation information 



J-- SON file contains the following accessibility test information: Some of the information components available in the generated JSON report are:


-- violations (array): These results indicate what elements failed in the rules.
-- passes (array): These results indicate what elements passed in the rules.
-- incomplete (array): It contains results that were aborted and require further testing. This can happen either because of technical restrictions to what the rule can test or because of a javascript error that occurred.
-- inapplicable (array): These results indicate rules that did not run because no matching content was found on the page. For example, if no video exists, those rules won’t run.
