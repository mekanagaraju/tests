package test.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;

import test.tests.SingleSeleniumSolution;

public class redBusScreen
{
   protected Log log = LogFactory.getLog(this.getClass().getName());
   SingleSeleniumSolution webDriver;


   public redBusScreen(WebDriver webDriver)
   {
      this.webDriver = new SingleSeleniumSolution(webDriver);
   }
   public static final String MAINELEMENT = "css=div[class='TSB']";
   public static final String LOGINTOYOURREDBUS_POPUP = "css=img[onclick^=' cbar_close_popup']";
   public static final String FROMADDRESS = "css=input[id='txtSource']";
   public static final String TOADDRESS = "css=input[id='txtDestination']";
   public static final String DATEOFJOURNEY_INPUTBOX = "css=input[id='txtOnwardCalendar']";
   public static final String DATEOFJOURNEY = "//div[@id='rbcal_txtOnwardCalendar']";


   public void initRedBus()
   {
      webDriver.get("https://www.redbus.in/");
      webDriver.waitForElementVisible(MAINELEMENT, 120);
      log.info("Navigated to Red Bus home page");
   }


   public void closeSigninPopup()
   {
      if (webDriver.isElementPresentWithWait(LOGINTOYOURREDBUS_POPUP, 30))
      {
         webDriver.click(LOGINTOYOURREDBUS_POPUP);
         log.info("Closed the Login to your redBus popup");
         webDriver.delay(2);
      }
   }


   public void fromAndTo(String fromAddress, String toAddress)
   {
      webDriver.waitForElementVisible(FROMADDRESS, 120);
      log.info("Setting From: " + fromAddress);
      webDriver.type(FROMADDRESS, fromAddress);
      log.info("Setting To: " + toAddress);
      webDriver.type(TOADDRESS, toAddress);
   }


   public void planDateOfJourney(String journeyDate)
   {
      webDriver.waitForElementVisible(DATEOFJOURNEY_INPUTBOX, 120);
      webDriver.click(DATEOFJOURNEY_INPUTBOX);
   }


   public void name()
   {
      webDriver.getCurrentBrowserVersion();
      String browserVersion = webDriver.getCurrentBrowserVersion();
      String browserName = webDriver.getBrowserName();
      if (browserName.contains("chrome"))
      {
         browserName = "Chrome";
      }
      else if (browserName.contains("firefox"))
      {
         browserName = "Firefox";
      }
      else if (browserName.contains("internet explorer"))
      {
         browserName = "Internet Explorer";
      }
      else if (browserName.contains("safari"))
      {
         browserName = "Safari";
      }
      else
      {
         browserName = "";
      }
      String value = browserName + " version " + browserVersion;
      log.info(value);
   }
}
