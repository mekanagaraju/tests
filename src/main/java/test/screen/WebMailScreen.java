package test.screen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import test.tests.SingleSeleniumSolution;

public class WebMailScreen
{
   SingleSeleniumSolution webController;
   protected static final Log log = LogFactory.getLog("WebMailScreen");
   protected static boolean exceed = true;
   protected static int mailNumber = 0;
   protected static String pMailTime = null;
   protected static boolean mailtoDelete = false;
   private static final String INPUT_USERNAME = "css=input[id='username']";
   private static final String INPUT_PASSWORD = "css=input[id='password']";
   private static final String PRIVATECOMPUTER = "css=input[id='chkPrvt']";
   private static final String BUTTON_SIGNIN = "//span[contains(text(), 'sign in')]";
   private static final String NEWMAIL = "//span[contains(text(), 'New mail')]";
   private static final String COLLAPSE_MENU = "css=button[aria-label='Collapse Folder Pane']";
   private static final String EXPAND_MENU = "css=button[aria-label='Expand Folder Pane']";
   private static final String INBOX = "//div[@aria-label='Folder Pane']//div[@role='group']/div[1]";
   private static final String DRAFTS = "//div[@aria-label='Folder Pane']//div[@role='group']/div[2]";
   private static final String SENTITEMS = "//div[@aria-label='Folder Pane']//div[@role='group']/div[3]";
   private static final String DELETED_ITEMS = "//div[@aria-label='Folder Pane']//div[@role='group']/div[4]";
   private static final String JUNK_EMAIL = "//div[@aria-label='Folder Pane']//div[@role='group']/div[5]";
   private static final String NOTES = "//div[@aria-label='Folder Pane']//div[@role='group']/div[6]";
   private static final String MAILSCOUNT = "//div[@aria-label='Mail list']//div[starts-with(@id, '_ariaId_')][@tabindex='-1']";
   private static final String SETTINGS = "//div[2]//div[3]/div[1]/div[2]/div[2]//div[1]//td[2]//span";
   private static final String SETTINGS_OPTIONS = "//span[contains(text(), 'Options')]";
   private static final String SETTINGS_OPTIONS_SETTINGS = "//a[contains(text(), 'settings')]";
   private static final String SETTINGS_OPTIONS_SETTINGS_REGIONAL = "//a[contains(text(), 'regional')]";
   private static final String SETTINGS_FRAME = "//div[@id='mainFrame']/iframe[1]";
   private static final String SETTINGS_CURRENT_TIME_ZONE = "css=select[class='autoWidth minWidth multiLanguangeChar']";
   private static final String SETTINGS_CURRENT_TIME_ZONE_US = SETTINGS_CURRENT_TIME_ZONE + " option[value='Eastern Standard Time']";
   private static final String SETTINGS_SAVE = "//button[contains(text(), 'save')]";
   private static final String NEW_MAIL = "//span[contains(text(), 'New mail')]";
   private static final String TO = "css=input[aria-label='To recipients. Enter an email address or a name from your contact list.']";
   private static final String CC = "css=input[aria-label='Cc recipients. Enter an email address or a name from your contact list.']";
   private static final String SUBJECT = "css=input[aria-labelledby='MailCompose.SubjectWellLabel']";
   private static final String INSERT = "//span[contains(text(), 'INSERT')]";
   private static final String SEND = "//span[contains(text(), 'SEND')]";
   private static final String ATTACHMENT = "css=div[aria-label='Insert attachment']";
   public String user = "V566596";
   public String password = "Accenture01";


   public WebMailScreen(WebDriver webDriver)
   {
      this.webController = new SingleSeleniumSolution(webDriver);
   }


   public void openMail(String timeZone, String mailSubject)
   {
      boolean mailBox = isMailBoxUpdated(timeZone);
      int i = getMailsCount();
      log.info("Mail box is updated with new mails: " + mailBox);
      main: for (int z = 1; z <= i; z++)
      {
         boolean mTime = checkMailTime(timeZone, z);
         boolean mSubject = checkMailSubject(mailSubject, z);
         if (mTime)
         {
            if (mSubject)
            {
               log.info("Mail time: {} and Mail subject is: {}, opening the mail" + mSubject);
               String mailNeedToOpen = MAILSCOUNT + "[" + z + "]";
               mailtoDelete = true;
               mailNumber = z;
               webController.click(mailNeedToOpen);
               break;
            }
            else
            {
               continue main;
            }
         }
         else
         {
            if (exceed)
            {
               z = 1;
            }
            if (( !mTime) && (z == 1))
            {
               mailBox = isMailBoxUpdated(timeZone);
               log.info("Mail box is updated with new mails: " + mailBox);
               continue main;
            }
            if (( !mTime) && (z == 2))
            {
               Assert.fail("Mail not came, failing the test script");
            }
         }
      }
      if (mailtoDelete)
      {
         String mailNeedToDelete = MAILSCOUNT + "[" + mailNumber + "]//button[@title='Delete']";
         String mail = getMailSubject(mailNumber);
         log.info("Deleting the mail with subject {} " + mail);
         webController.click(mailNeedToDelete);
      }
   }


   protected void deleteMail(boolean flag)
   {
      if (flag)
      {
         if (mailtoDelete)
         {
            String mailNeedToDelete = MAILSCOUNT + "[" + mailNumber + "]//button[@title='Delete']";
            String mail = getMailSubject(mailNumber);
            log.info("Deleting the mail with subject {}" + mail);
            webController.click(mailNeedToDelete);
         }
         else
         {
            log.info("Unable to delete the mail {}" + mailNumber);
         }
      }
   }


   public void doLoginToWebMail()
   {
      if ( !webController.isElementVisibleWithWait(INPUT_USERNAME, 20))
      {
         String url = "https://webmail.twcable.com/owa/";
         webController.navigateTo(url);
         log.info("Navigating to: " + url);
      }
      webController.waitForElementVisible(INPUT_USERNAME, 30);
      log.info("Login into outlook account using User name: {" + user + "} with Password: {" + password + "} ");
      webController.type(INPUT_USERNAME, user);
      webController.type(INPUT_PASSWORD, password);
      webController.setCheckBoxState(PRIVATECOMPUTER, false);
      webController.click(BUTTON_SIGNIN);
      webController.waitForElementNotFound(BUTTON_SIGNIN, 30);
      webController.waitForElementVisible(NEWMAIL, 90);
      if (webController.isElementVisible(EXPAND_MENU))
      {
         webController.click(EXPAND_MENU);
         webController.waitForElementVisible(COLLAPSE_MENU, 30);
      }
      webController.click(INBOX);
   }


   private int getMailsCount()
   {
      webController.waitForElementVisible(MAILSCOUNT, 30);
      return webController.getElementsCount(MAILSCOUNT);
   }


   protected ArrayList<String> getMailTime(int mailValue)
   {
      ArrayList<String> values = new ArrayList<String>();
      String pMailTimeElement = MAILSCOUNT + "[" + mailValue + "]//div[4]/div[3]/span[1]";
      String pMailTime = webController.getText(pMailTimeElement).trim();
      String[] a = pMailTime.split(" ");
      String pDay = a[0];
      if (pDay.contains(":"))
      {
         Date dNow = new Date();
         SimpleDateFormat ft = new SimpleDateFormat("E");
         pMailTime = ft.format(dNow) + " " + pMailTime;
         a = pMailTime.split(" ");
         pDay = a[0];
      }
      int l = a[1].length();
      String pZone = a[1].substring((l - 1), l);
      String pTime = a[1].replace(pZone, "");
      pZone = pZone + "m";
      values.add(pDay);
      values.add(pTime);
      values.add(pZone);
      values.add(pTime.replace(":", ""));
      return values;
   }


   private boolean checkMailSubject(String mailSubject, int z)
   {
      boolean flag = false;
      String pMailSubjectElement = MAILSCOUNT + "[" + z + "]//div[4]/div[2]/span[1]";
      String pMailSubject = webController.getText(pMailSubjectElement).trim();
      if (mailSubject.contains(pMailSubject))
      {
         flag = true;
      }
      return flag;
   }


   private String getMailSubject(int z)
   {
      String pMailSubjectElement = MAILSCOUNT + "[" + z + "]//div[4]/div[2]/span[1]";
      return webController.getText(pMailSubjectElement).trim();
   }


   private boolean checkMailTime(String timeZone, int z)
   {
      boolean flag = false;
      ArrayList<String> pTime = getMailTime(z);
      String pMailDay = pTime.get(0);
      pMailTime = pTime.get(1);
      String pMailZone = pTime.get(2);
      int pMailTimeI = Integer.parseInt(pTime.get(3));
      String[] systemTime = timeZone.split(" ");
      if (systemTime[0].contains("/"))
      {
         exceed = true;
      }
      else
      {
         int sTime = Integer.parseInt(systemTime[0].replace(":", ""));
         String sTimeZone = systemTime[1];
         if (pMailZone.equals(sTimeZone))
         {
            if (pMailTimeI >= sTime)
            {
               log.info("Current mail time is {}" + pMailDay + pMailTime + pMailZone);
               flag = true;
            }
            else
            {
               flag = false;
            }
         }
         else
         {
            flag = false;
         }
      }
      return flag;
   }


   private boolean isMailBoxUpdated(String timeZone)
   {
      boolean flag = false;
      for (int i = 1; i <= 10; i++)
      {
         ArrayList<String> pTime = getMailTime(1);
         String pMailZone = pTime.get(2);
         int pMailTimeI = Integer.parseInt(pTime.get(3));
         String[] systemTime = timeZone.split(" ");
         int sTime = Integer.parseInt(systemTime[0].replace(":", ""));
         String sTimeZone = systemTime[1];
         if (pMailZone.equals(sTimeZone))
         {
            if (pMailTimeI >= sTime)
            {
               flag = true;
               break;
            }
            else
            {
               webController.reload();
               webController.delay(30);
               webController.waitForElementVisible(MAILSCOUNT, 90);
               flag = false;
            }
         }
         else
         {
            webController.reload();
            webController.delay(30);
            webController.waitForElementVisible(MAILSCOUNT, 90);
            flag = false;
         }
      }
      return flag;
   }


   public String getTimeZone()
   {
      TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
      String timeFormat = "h:mm a";
      DateFormat dateFormat = new SimpleDateFormat(timeFormat);
      Calendar cal = Calendar.getInstance(timeZone);
      dateFormat.setTimeZone(cal.getTimeZone());
      return dateFormat.format(cal.getTime()).toLowerCase();
   }


   public void setTimeZoneToUSEastren()
   {
      webController.waitForElementVisible(SETTINGS, 60);
      webController.click(SETTINGS);
      webController.waitForElementVisible(SETTINGS_OPTIONS, 20);
      webController.click(SETTINGS_OPTIONS);
      webController.waitForElementVisible(SETTINGS_OPTIONS_SETTINGS, 20);
      webController.click(SETTINGS_OPTIONS_SETTINGS);
      webController.waitForElementVisible(SETTINGS_OPTIONS_SETTINGS_REGIONAL, 20);
      webController.click(SETTINGS_OPTIONS_SETTINGS_REGIONAL);
      webController.switchToFrame(SETTINGS_FRAME);
      webController.click(SETTINGS_CURRENT_TIME_ZONE_US);
      webController.select(SETTINGS_CURRENT_TIME_ZONE, SETTINGS_CURRENT_TIME_ZONE_US);
      webController.click(SETTINGS_SAVE);
   }


   public void sendMail()
   {
      SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy h-mm a");
      String message = "Jekins failures on " + sdf.format(new Date()) + " IST";
      webController.waitForElementVisible(NEW_MAIL, 30);
      webController.click(NEW_MAIL);
      try
      {
         webController.waitForElementVisible(TO, 20);
      } catch (Exception e)
      {}
      if ( !webController.isElementVisible(TO))
      {
         webController.click(NEW_MAIL);
         webController.waitForElementVisible(TO, 20);
      }
      // webController.sendKeys(TO, "TWC.CTG.Automation@accenture.com");
      webController.sendKeys(TO, "nagaraju.meka@twc-contractor.com");
      webController.sendKeys(CC, "");
      webController.sendKeys(SUBJECT, message);
      webController.click(INSERT);
      webController.click(ATTACHMENT);
      Alert alert = webController.getWebDriver().switchTo().alert();
      alert.sendKeys("C:/apps/JenkinsFailures/Jenkins failures on 15-6-2016 6-16 PM.xls");
      webController.delay(20);
      webController.click(SEND);
      webController.delay(20);
   }
}