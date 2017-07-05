package test.tests;

import java.lang.reflect.Method;

import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import test.screen.JenkinsScreen;

public class PullJenkinsFailuresReportTest
{
   protected static final Logger log = LoggerFactory.getLogger(PullJenkinsFailuresReportTest.class);
   public TestName name = new TestName();
   WebDriver driver = null;
   SingleSeleniumSolution webController = new SingleSeleniumSolution(driver);
   protected static final String AAD = "_01 AADW8";
   protected static final String ACRMMOBILE = "_02 ACRM Mobile";
   protected static final String C360DESKTOP = "_03 C360 Desktop";
   protected static final String MYACCOUNT = "_04 MyAccount";
   protected static final String MYSERVICES = "_05 MyServices";
   protected static final String OBP = "_06 OBP";
   protected static final String PMT = "_07 PMT";
   // protected static final String IVR = "_08 IVR";
   protected static final String REGISTRATION = "_09 Registration";
   // protected static final String JMETERPERFORMANCE = "_10 JMeter Performance";
   protected static final String EMS = "_11EMS";
   protected static final String MYACCOUNTREDESIGN = "_13MyAccount Redesign";
   protected static final String OKM = "_12 OKM";
   protected static final String ITK = "_14 ITK";
   protected static final String SBT = "_15SBTool";
   private JenkinsScreen js;


   @BeforeMethod(alwaysRun = true)
   public void beforeMethod(Method method)
   {
      String url = "cyrano.corp.mystrotv.com:8080";
      webController = SingleSeleniumSolution.buildWebDriver(url, "chrome", method);
   }


   @AfterMethod(alwaysRun = true)
   public void afterMethod()
   {
      webController.quit();
   }


   @Test
   public void pullSingleReportDSM()
   {
      js = new JenkinsScreen(webController.getWebDriver());
      js.pullApplicationsReports();
   }


   @Test
   public void pullATFReport()
   {
      js = new JenkinsScreen(webController.getWebDriver());
      js.pullApplicationReport(OBP);
   }
}
