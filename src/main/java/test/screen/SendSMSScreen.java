package test.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import test.tests.SingleSeleniumSolution;

public class SendSMSScreen
{
   protected Log log = LogFactory.getLog(this.getClass().getName());
   SingleSeleniumSolution webController;
   /*** Screen Objects ***/
   public static final String USERNAME = "css=input[name='username']";
   public static final String PASSWORD = "css=input[name='password']";
   public static final String LOGIN = "css=input[id='loginBTN']";
   public static final String SKIP = "css=input[value='Skip']";
   public static final String SENDFREESMS = "css=input[value='Send Free SMS']";
   public static final String SENDSMS_TAB = "//a[contains(text(), 'Send SMS')]";
   public static final String SENDSMS_FAME = "//iframe[@name='frame']";
   public static final String MOBILENUMBER = "css=input[name='mobile']";
   public static final String MESSAGEBODY = "css=textarea[name='message']";
   public static final String SENDSMS = "css=input[name='Send']";
   public static final String SENDSMS_MESSAGE_STATUS = "//p[@class='mess']//span";
   public static final String BACKTO_SENDSMS = "css=p[onclick='javascript:goBack('sendSMS');']";


   public SendSMSScreen(WebDriver webDriver)
   {
      this.webController = new SingleSeleniumSolution(webDriver);
   }


   public void doLogin(String userName, String password)
   {
      log.info("Login into the application with number " + userName);
      webController.closeAllChildWindows();
      webController.waitForElementVisible(USERNAME, 90);
      webController.type(USERNAME, userName);
      webController.type(PASSWORD, password);
      webController.click(LOGIN);
      webController.waitForElementNotFound(LOGIN, 30);
      if (webController.isElementPresentWithWait(SKIP, 90))
      {
         log.info("Login into the application successfully");
      }
      else
      {
         Assert.fail("Not able to Login");
      }
      navigateToSendSMS();
   }


   public void navigateToSendSMS()
   {
      webController.waitForElementVisible(SKIP, 90);
      webController.click(SKIP);
      webController.waitForElementVisible(SENDFREESMS, 60);
      webController.click(SENDFREESMS);
      log.info("Navigated to SMS page");
   }


   public void sendFreeSMS(String mobileNumber, String message)
   {
      webController.waitForElementVisible(SENDSMS_TAB, 60);
      webController.click(SENDSMS_TAB);
      log.info("Navigating to Send SMS tab");
      webController.waitForElementVisible(SENDSMS_FAME, 30);
      webController.waitForElementVisible(MOBILENUMBER, 30);
      webController.type(MOBILENUMBER, mobileNumber);
      webController.type(MESSAGEBODY, message);
      webController.click(SENDSMS);
      webController.waitForElementVisible(SENDSMS_MESSAGE_STATUS, 60);
      String text = webController.getText(SENDSMS_MESSAGE_STATUS);
      log.info(" " + text + " " + mobileNumber);
      webController.click(BACKTO_SENDSMS);
   }
}
