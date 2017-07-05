package test.tests;

import java.lang.reflect.Method;

import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import test.screen.SendSMSScreen;

public class SendSMS
{
   protected static final Logger log = LoggerFactory.getLogger(SendSMS.class);
   public TestName name = new TestName();
   WebDriver driver = null;
   SingleSeleniumSolution webDriver = new SingleSeleniumSolution(driver);
   private SendSMSScreen sms;


   @BeforeMethod(alwaysRun = true)
   public void beforeMethod(Method method)
   {
      String url = "http://site24.way2sms.com/entry";
      webDriver = SingleSeleniumSolution.buildWebDriver(url, "chrome", method);
   }


   @AfterMethod(alwaysRun = true)
   public void afterMethod()
   {
      webDriver.quit();
   }


   @Test
   public void testSendSMS()
   {
      sms = new SendSMSScreen(webDriver.getWebDriver());
      sms.doLogin("9491671279", "123456");
      sms.sendFreeSMS("9491671279", "avialable");
   }
}
