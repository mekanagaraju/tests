package test.screen;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import test.tests.SingleSeleniumSolution;

public class GmailScreen
{
   protected Log log = LogFactory.getLog(this.getClass().getName());
   SingleSeleniumSolution webController;


   public GmailScreen(WebDriver webDriver)
   {
      this.webController = new SingleSeleniumSolution(webDriver);
   }
   /**** screen objects ****/
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


   // *****************************************************************************************************//
   public void openValidMail(String gmailUserName, String gmailPassword, String searchSubjectContent)
   {
      String url = "https://mail.google.com/mail/";
      log.info("Opening Gmail: " + url);
      webController.get(url);
      int systemTime = returnMailTimeFormat(getSystemTime());
      log.info("Current System Time: " + getSystemTime());
      if (webController.isElementVisible(GMAIL_CURRENTMAIL_NAME))
      {
         log.info("Logout the existing user...");
         webController.waitForElementVisible(GMAIL_CURRENTMAIL_NAME);
         webController.click(GMAIL_CURRENTMAIL_NAME);
         webController.waitForElementVisible(GMAIL_SIGNOUT);
         webController.click(GMAIL_SIGNOUT);
         webController.delay(5);
      }
      boolean flag = false;
      if (webController.isElementVisible(LINK_GMAILSIGNIN))
      {
         log.info("Clicking on Gmail Sign in Link");
         webController.click(LINK_GMAILSIGNIN);
         webController.waitForElementVisible(GMAIL_USERNAME, 60);
         flag = true;
      }
      else if (webController.isElementVisible(GMAIL_SWITCHTODIFFERENTACOUNT))
      {
         log.info("Some other user has logged in, switching to " + gmailUserName + " account");
         webController.click(GMAIL_SWITCHTODIFFERENTACOUNT);
         webController.delay(3);
         webController.waitForElementVisible(GMAIL_ADDACOUNT, 60);
         webController.click(GMAIL_ADDACOUNT);
         webController.waitForElementVisible(GMAIL_USERNAME, 60);
         flag = true;
      }
      else if (webController.isElementVisible(GMAIL_USERNAME))
      {
         flag = true;
      }
      if (flag == true)
      {
         setGmailIDPasswordNew(gmailUserName, gmailPassword, searchSubjectContent, systemTime);
      }
   }


   private void setGmailIDPasswordNew(String gmailUserName, String gmailPassword, String subjectContentNeedtoFind, int systemTime)
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
      webController.delay(5);
      if (webController.isElementPresentWithWait("//table//tbody//tr[1]//td[6]", 90))
      {
         log.info("login to the Gmail: " + gmailUserName + " account successfully");
      }
      else
      {
         log.info("Not able to login to the Gmail: " + gmailUserName + " account");
      }
      getEmailSubjectNew(subjectContentNeedtoFind, systemTime);
   }


   private void getEmailSubjectNew(String subjectNeedtoFind, int systemTime)
   {
      checkMailTime(systemTime);
      int q = 0;
      mailCheck: for (int z = 1; z <= 10; z++)
      {
         String mainSubject = webController.getText("//table//tbody//tr[" + z + "]//td[6]//span[1]");
         String mailTimeElement = "//table//tbody//tr[" + z + "]//td[8]//b";
         String openMail = "//table//tbody//tr[" + z + "]//td[6]";
         String currentMailTime = validateCurrentMailTime(mailTimeElement);
         log.info("Current Mail Time: " + currentMailTime);
         if (returnMailTimeFormat(currentMailTime) >= systemTime)
         {
            if (mainSubject.contains(subjectNeedtoFind))
            {
               log.info("Opening the mail with subject: " + mainSubject);
               webController.click(openMail);
               break;
            }
            else
            {
               log.info("Validating the subject of Mail: '" + z + "' in inbox");
               log.info("Mail: '" + z + "' subject is not matching, Checking the next mail");
               continue mailCheck;
            }
         }
         else
         {
            mailReload(2);
            q = q + 1;
            z = 1;
            if (q == 3)
            {
               currentMailTime = validateCurrentMailTime(mailTimeElement);
               if ( !(returnMailTimeFormat(currentMailTime) >= systemTime))
               {
                  log.info("Current Mail Time: " + validateFirstMailTime());
                  log.info("Mail has not arrived, Exiting the test");
                  Assert.fail("Mail has not arrived, Exiting the test");
               }
               else
               {
                  z = 1;
                  continue mailCheck;
               }
            }
            log.info("Refreshing the mail box, Looking for the email with subject... " + subjectNeedtoFind);
         }
      }
   }


   public void openLinkandVerify()
   {
      int countBefore = webController.getNumberOfOpenWindows();
      log.info("Opening new window to register the TWC request");
      webController.delay(5);
      int countAfter = webController.getNumberOfOpenWindows();
      if (countAfter > countBefore)
      {
         webController.switchToNewlyOpenedWindow();
         webController.switchToDefaultContent();
         log.info("Switching to newly opened window");
      }
      webController.switchToNewlyOpenedWindow();
      webController.windowMaximize();
      webController.delay(10);
   }


   private void checkMailTime(int systemTime)
   {
      webController.reload();
      log.info("Initating the mail checking process...");
      webController.delay(15);
      for (int q = 1; q <= 4; q++)
      {
         int mailTime = returnMailTimeFormat(validateFirstMailTime());
         if (validateFirstMailTime() == null)
         {
            mailTime = returnMailTimeFormat(validateFirstMailTime());
         }
         boolean flag = false;
         String VMailCal = returnTimeFormat(validateFirstMailTime());
         log.info("Current Mail Time: " + VMailCal);
         String VSysCal = returnTimeFormat(getSystemTime());
         if (VMailCal.equals(VSysCal))
         {
            flag = true;
         }
         if ((mailTime < systemTime) || (flag == false))
         {
            webController.reload();
            log.info("Refreshing the mail box for New Mails...");
            mailReload(2);
         }
         else if ((mailTime >= systemTime) && (flag == true))
         {
            log.info("New Mail Arrived, Validating the subject");
            break;
         }
      }
   }


   private String validateFirstMailTime()
   {
      String mailTim = null;
      if ( !(webController.isElementPresentWithWait(GMAIL_CURRENTMAIL_TIME, 240)))
      {
         webController.reload();
         webController.delay(15);
      }
      webController.waitForElementVisible(GMAIL_CURRENTMAIL_TIME, 240);
      mailTim = webController.getText(GMAIL_CURRENTMAIL_TIME);
      return mailTim;
   }


   private String validateCurrentMailTime(String mailElement)
   {
      String mailTim = null;
      if ( !(webController.isElementPresentWithWait(mailElement, 240)))
      {
         webController.reload();
         webController.delay(15);
      }
      webController.waitForElementVisible(mailElement, 240);
      mailTim = webController.getText(mailElement);
      return mailTim;
   }


   private String getSystemTime()
   {
      Date date = new Date();
      String strDateFormat = "h:mm a";
      SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
      String systemTime = sdf.format(date);
      systemTime = systemTime.toLowerCase();
      return systemTime;
   }


   private void mailReload(int loop)
   {
      for (int q = 1; q <= loop; q++)
      {
         webController.reload();
         webController.delay(20);
      }
   }


   public int returnMailTimeFormat(String mailTime)
   {
      mailTime = mailTime.replace(" am", "").replace(" pm", "").replace(":", "");
      return Integer.parseInt(mailTime);
   }


   private String returnTimeFormat(String text)
   {
      text = text.replace(":", "").replaceAll("\\d", "").replace(".", "").replace(" ", "");
      return text;
   }


   public void closeOtherTabs()
   {
      webController.switchToParentWindow();
      webController.closeAllChildWindows();
      log.info("Closing the Child Windows, Please wait...");
   }


   public void getText()
   {
      String text = webController.getText("//div[@style='overflow: hidden;'] [contains(text(), 'Dear To Delete,')]");
      log.info(text);
      text = text.replaceAll("Your My Account password has been reset. The new password is ", "").replaceAll("Dear To Delete,", "").replaceAll("Thank You,", "").replaceAll("Time Warner Cable Business Class", "").replace("\n", "").replace(".", "").trim();
      log.info(text);
   }


   public void doLogin(String gmailUserName, String gmailPassword, String subject)
   {
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
         webController.delay(5);
         if (webController.isElementPresentWithWait("//table//tbody//tr[1]//td[6]", 90))
         {
            log.info("login to the Gmail: " + gmailUserName + " account successfully");
         }
         else
         {
            log.info("Not able to login to the Gmail: " + gmailUserName + " account");
         }
         sendEmail(subject);
      }
      else
      {
         log.info("User name is not visible");
      }
   }


   private void sendEmail(String subject)
   {
      webController.waitForElementVisible("//div[contains(text(), 'COMPOSE')]", 30);
      webController.click("//div[contains(text(), 'COMPOSE')]");
      webController.waitForElementVisible("//textarea[@aria-label='To']", 30);
      webController.sendKeys("//textarea[@aria-label='To']", "inagaraju4u@gmail.com");
      webController.sendKeys("//input[@name='subjectbox']", subject);
      webController.click("//div[contains(text(), 'Send')]");
      log.info("Mail Sent");
   }


   public void setPath(String subject, String locator, String path)
   {
      webController.click(locator);
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


   public void sendMail(String subject, String body, String path)
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
         webController.waitForElementVisible("//div[contains(text(), 'COMPOSE')]", 30);
         webController.click("//div[contains(text(), 'COMPOSE')]");
         webController.waitForElementVisible("//textarea[@aria-label='To']", 30);
         webController.sendKeys("//textarea[@aria-label='To']", "inagaraju4u@gmail.com");
         webController.sendKeys("//input[@name='subjectbox']", subject);
         webController.sendKeys("//div[@aria-label='Message Body']", body);
         setPath(subject, "//div[@data-tooltip='Attach files']", path);
         webController.click("//div[contains(text(), 'Send')]");
         log.info("Mail Sent");
      }
   }
}