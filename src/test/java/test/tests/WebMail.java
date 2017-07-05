package test.tests;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import test.screen.WebMailScreen;

public class WebMail
{
   protected static final Log log = LogFactory.getLog("WebMail");
   WebDriver driver = null;
   SingleSeleniumSolution webDriver = new SingleSeleniumSolution(driver);
   private WebMailScreen webMail;


   @BeforeMethod(alwaysRun = true)
   public void beforeMethod(Method method)
   {
      webDriver = SingleSeleniumSolution.buildWebDriver("https://webmail.twcable.com/owa/", "chrome", method);
   }


   @AfterMethod(alwaysRun = true)
   public void afterMethod()
   {
      webDriver.quit();
   }


   @Test
   public void testWebMail()
   {
      webMail = new WebMailScreen(webDriver.getWebDriver());
      webMail.doLoginToWebMail();
      webMail.sendMail();
   }
}
