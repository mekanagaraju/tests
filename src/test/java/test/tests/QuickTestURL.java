package test.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.rules.TestName;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import test.screen.SpectrumScreen;

public class QuickTestURL
{
   protected static final Logger log = LoggerFactory.getLogger(QuickTestURL.class);
   public TestName name = new TestName();
   WebDriver driver = null;
   SingleSeleniumSolution sss = new SingleSeleniumSolution(driver);
   String url = null;
   public static final String END_DATE = "css=input[name='endDate']";
   public static final String END_DATE_SELECTION = "//*[@id='datepicker']/table/tbody/tr[9]/td/button[1]";
   public static final String END_DATE_SELECTION_CLOSE = "//*[@id='datepicker']/table/tbody/tr[9]/td/button[2]";
   public static final String CALENDER_CLOSE = "//button[contains(text(), 'close')]";
   public static final String CALENDER_LEFTARROW = "css=td[class='dpButtonTD']:nth-child(1)";
   public static final String CALENDER_RIGHTARROW = "css=td[class='dpButtonTD']:nth-child(3)";
   public static final String EMS_CHANGE_LOG = "//table[@id='yspsubnav']//tr/td[4]/nav/ul/li/a";
   public static final String EMSIMPORT_STARTDATE = "css=table[class='dpTable']";
   public static final String EMPLOYEE_TERMINATION_COLOR = "css=tbody > tr > td > table:nth-child(3) > tbody > tr:nth-child(1) > td:nth-child(1) > table > tbody > tr:nth-child(1) > th";
   public static final String SEARCH_RESULTS = "//*[@id='change2']/tr[2]/td[3]/a";
   public static final String START_DATE = "css=input[name='startDate']";
   public static final String START_DATE_SELECTION_MONTH = "css=tr[class='dpTitleTR'] td:nth-child(1)";


   @BeforeMethod
   public void beforeMethod(Method method)
   {
      url = "https://www.google.co.in";
      sss = SingleSeleniumSolution.buildWebDriver(url, "ff", method);
   }


   @AfterMethod
   public void afterMethod()
   {
      sss.quit();
   }
   public static final String LOGIN_LINK = "//b[contains(text(), 'log in')]";


   @Test
   public void bigFileTest()
   {
      sss.setCheckBoxState("css=input[id='mainpage_RadUpload2checkbox0']", true);
      sss.switchToNewlyOpenedWindow();
      sss.sendKeys("css=input[class='ruButton ruBrowse']", "C:\\apps\\JenkinsFailures\\OBP Jenkins failures on 16-6-2016 10-57 AM.xls");
      sss.sendKeys("//*[@id='mainpage_txtRecipientName']", "nagaraju.meka");
      sss.delay(2);
      sss.click("css=input[id='mainpage_btnAddRec']");
      sss.delay(2);
      sss.sendKeys("css=input[id='mainpage_tbSubject']", "Jenkins failures");
      sss.click("css=input[id='mainpage_btnUpload']");
   }
   public static final String SKIP = "//h1[contains(text(), 'Skipped')]";
   public static final String CONSOLE_OUTPUT = "//a[contains(text(), 'Console Output')]";


   @Test
   public void test()
   {
      String texts = sss.getText("//*[@id='main-panel']/pre").trim();
      String patten = "##################################################################################";
      if (texts.contains(patten))
      {
         String[] textE = texts.split(patten);
         if (textE.length >= 2)
         {
            String textE1 = textE[textE.length - 2];
            String[] standardOutputs1 = textE1.split(" - Description:", 2);
            String[] standardOutputs2 = textE1.split(" - Test starting:", 2);
            String testName = standardOutputs2[1];
            standardOutputs2 = testName.split("\\n");
            log.info(standardOutputs2[0]);
            String temp = standardOutputs1[1];
            String[] finals = temp.split("\\n");
            log.info(finals[0]);
            // text.add(finals[0].trim());// Description
            textE1 = textE[textE.length - 1];
            standardOutputs1 = textE1.split(" - Another Build: [{] Browser:", 2);
            temp = standardOutputs1[1];
            finals = temp.split("}");
            temp = finals[0].trim();
            log.info(temp);
         }
      }
   }


   @Test
   public void selectDateFromCalender()
   {
      Alert alert = sss.getWebDriver().switchTo().alert();
      alert.setCredentials(new UserAndPassword("TWCCORP\\V595865", "CAFAAutomation@01"));
      // sss.getWebDriver().findElement(By.id("Username")).sendKeys("TWCCORP\\V595865");
      // sss.getWebDriver().findElement(By.id("Password")).sendKeys("CAFAAutomation@01");
      // sss.sendKeys("Username", "TWCCORP\\V595865");
      // sss.sendKeys("Password", "CAFAAutomation@01");
      sss.switchToDefaultContent();
      // setStartDate(01, 05, 2015);
      // setEndDate(31, 05, 2015);
   }


   @Test
   public void aptTesting()
      throws JSONException
   {
      try
      {
         URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address=india&sensor=false&#8221");
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("Accept", "application/json");
         if (conn.getResponseCode() != 200)
         {
            throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
         }
         Scanner scan = new Scanner(url.openStream());
         String entireResponse = new String();
         while (scan.hasNext())
         {
            entireResponse += scan.nextLine();
         }
         System.out.println("Response : " + entireResponse);
         scan.close();
         JSONObject obj = new JSONObject(entireResponse);
         String responseCode = obj.getString("status");
         System.out.println("status : " + responseCode);
         JSONArray arr = obj.getJSONArray("results");
         for (int i = 0; i < arr.length(); i++)
         {
            String placeid = arr.getJSONObject(i).getString("place_id");
            System.out.println("Place id : " + placeid);
            String formatAddress = arr.getJSONObject(i).getString("formatted_address");
            System.out.println("Address : " + formatAddress);
            if (formatAddress.equalsIgnoreCase("Chicago, IL, USA"))
            {
               System.out.println("Address is as Expected");
            }
            else
            {
               System.out.println("Address is not as Expected");
            }
         }
         conn.disconnect();
      } catch (MalformedURLException e)
      {
         e.printStackTrace();
      } catch (IOException e)
      {
         e.printStackTrace();
      }
   }


   @Test
   public void testSample()
   {
      new SpectrumScreen(sss.getWebDriver());
   }
}