package test.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test.tests.SingleSeleniumSolution;

public class mainValidations
{
   protected Log log = LogFactory.getLog(this.getClass().getName());
   SingleSeleniumSolution webDriver;


   public SingleSeleniumSolution getWebController()
   {
      return webDriver;
   }


   public void assertElementExists(String locator, String message)
   {
      Assert.assertTrue(getWebController().isElementPresentWithWait(locator, 120), "Could not locate " + message + " on Screen" + " --->" + locator);
      log.info(message + " Element exists on the page");
   }


   public void assertMatch(String message, String expectedValue, String actualValue)
   {
      Assert.assertTrue(expectedValue.trim().equals(actualValue.trim()), " Expected Value: " + expectedValue + " Actual Value: " + actualValue);
      log.info("Passed: " + message + " -->  " + expectedValue + " contains " + actualValue);
   }


   public void assertEquals(String message, String expectedValue, String actualValue)
   {
      Assert.assertEquals(expectedValue.trim(), actualValue.trim(), message);
      log.info("Passed: " + message + " -->  " + expectedValue + " Equals " + actualValue);
   }


   public void assertTrue(boolean condition, String... message)
   {
      StringBuilder sb = new StringBuilder();
      for (String each : message)
      {
         sb.append(each);
      }
      Assert.assertTrue(condition, sb.toString());
   }


   public void assertNotNullOrEmpty(String condition, String... errorMessage)
   {
      StringBuilder sb = new StringBuilder();
      for (String each : errorMessage)
      {
         sb.append(each);
      }
      Assert.assertTrue(condition != null && !condition.isEmpty(), sb.toString());
   }
}
