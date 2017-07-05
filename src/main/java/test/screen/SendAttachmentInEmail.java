package test.screen;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;

import test.tests.SingleSeleniumSolution;

public class SendAttachmentInEmail
{
   protected Log log = LogFactory.getLog(this.getClass().getName());
   SingleSeleniumSolution webController;
   public static final String LINK_GMAILSIGNIN = "//a[@data-g-label='Sign in']";
   public static final String GMAIL_USERNAME = "//*[@id='Email']";
   public static final String GMAIL_NEXT = "//*[@id='next']";
   public static final String GMAIL_PASSWORD = "//*[@id='Passwd']";
   public static final String GMAIL_SIGNIN_BUTTON = "css=input[id='signIn']";
   public static final String GMAIL_SIGNINREMENBER = "//label[@class='remember']/input";
   public static final String GMAIL_SWITCHTODIFFERENTACOUNT = "//*[@class='switch-account']/a[1]";
   public static final String GMAIL_ADDACOUNT = "//ul[@id='account-chooser-options']/li[1]/a[1]";
   public static final String GMAIL_BACKTOINBOX = "css=div[data-tooltip='Back to Inbox']";
   public static final String GMAIL_CURRENTMAIL_TIME = "//table//tbody//tr[1]//td[8]//b";
   public static final String GMAIL_DELETE_CURRENTMAIL = "css=div[data-tooltip='Delete']";
   public static final String GMAIL_CURRENTMAIL_NAME = "//div[@role='banner']//div[4]";
   public static final String GMAIL_SIGNOUT = "//div[@role='banner']//div[4]//div[3]//div[2]/a";


   public SendAttachmentInEmail(WebDriver webDriver)
   {
      this.webController = new SingleSeleniumSolution(webDriver);
   }


   public void setPath(String subject, String locator, String path)
   {
      webController.click(locator);
      path = path.replaceAll("/", "\\");
      if ( !path.contains("\\"))
      {
         path = path.replaceAll("[/]", "\\");
      }
      log.info("Path: " + path);
      webController.delay(3);
      try
      {
         StringSelection stringSelection = new StringSelection(path);
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
         Robot robot = new Robot();
         robot.keyPress(KeyEvent.VK_CONTROL);
         robot.keyPress(KeyEvent.VK_V);
         robot.keyRelease(KeyEvent.VK_V);
         robot.keyRelease(KeyEvent.VK_CONTROL);
         robot.keyPress(KeyEvent.VK_ENTER);
         robot.keyRelease(KeyEvent.VK_ENTER);
      } catch (Exception exp)
      {
         exp.printStackTrace();
      }
      log.info("Added attachement");
      String element = "css=div[aria-label='Attachment: " + subject + ".xls. Press enter to view the attachment and delete to remove it']";
      webController.waitForElementVisible(element, 220);
   }


   public void sendMail(String subject, String path, String to)
   {
      String gmailUserName = "nagaraju.meka.accenture@gmail.com";
      String gmailPassword = "Accenture0";
      String url = "https://mail.google.com/mail/";
      log.info("Opening Gmail: " + url);
      webController.get(url);
      if (webController.isElementPresentWithWait(GMAIL_USERNAME, 90))
      {
         log.info("Setting the user name: " + gmailUserName);
         webController.waitForElementVisible(GMAIL_USERNAME);
         webController.type(GMAIL_USERNAME, gmailUserName);
         if (webController.isElementVisible(GMAIL_NEXT))
         {
            webController.click(GMAIL_NEXT);
         }
         webController.waitForElementVisible(GMAIL_PASSWORD, 60);
         log.info("Setting the password for user: " + gmailPassword);
         webController.type(GMAIL_PASSWORD, gmailPassword);
         webController.setCheckBoxState(GMAIL_SIGNINREMENBER, false);
         log.info("Sign in Please Wait...");
         webController.click(GMAIL_SIGNIN_BUTTON);
         if (webController.isElementPresentWithWait("//table//tbody//tr[1]//td[6]", 90))
         {
            log.info("login to the Gmail: " + gmailUserName + " account successfully");
         }
         else
         {
            log.info("Not able to login to the Gmail: " + gmailUserName + " account");
         }
         String body = "Hello Team,\n\nPlease find the " + subject + " report in the attachment.\n\nBefore going to upload this to Jira, please do the necessary changes in the spread sheet if needed.\n\nThanks,\nNagaraju.Meka";
         log.info("Bady: " + body);
         webController.waitForElementVisible("//div[contains(text(), 'COMPOSE')]", 30);
         webController.click("//div[contains(text(), 'COMPOSE')]");
         webController.waitForElementVisible("//textarea[@aria-label='To']", 30);
         webController.sendKeys("//textarea[@aria-label='To']", to);
         webController.delay(1);
         webController.sendKeys("//input[@name='subjectbox']", subject);
         webController.delay(1);
         webController.sendKeys("//div[@aria-label='Message Body']", body);
         webController.delay(1);
         setPath(subject, "//div[@data-tooltip='Attach files']", path);
         webController.click("//div[contains(text(), 'Send')]");
      }
   }
}