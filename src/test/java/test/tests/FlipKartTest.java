package test.tests;

import java.lang.reflect.Method;

import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import test.screen.GmailScreen;

public class FlipKartTest
{
   protected static final Logger log = LoggerFactory.getLogger(FlipKartTest.class);
   public TestName name = new TestName();
   WebDriver driver = null;
   SingleSeleniumSolution webController = new SingleSeleniumSolution(driver);
   String url = null;


   @BeforeMethod
   public void beforeMethod(Method method)
   {
      url = "http://www.flipkart.com/";
      webController = SingleSeleniumSolution.buildWebDriver(url, "ie", method);
   }


   @AfterMethod
   public void afterMethod()
   {
      GmailScreen gs = new GmailScreen(webController.getWebDriver());
      gs.doLogin("nagaraju.meka.accenture@gmail.com", "Accenture0", "Test Over");
      webController.quit();
   }
   public static final String LOGIN_LINK = "//b[contains(text(), 'log in')]";


   @Test
   public void bigFileTest()
   {
      webController.setCheckBoxState("css=input[id='mainpage_RadUpload2checkbox0']", true);
      webController.switchToNewlyOpenedWindow();
      webController.sendKeys("css=input[class='ruButton ruBrowse']", "C:\\apps\\JenkinsFailures\\OBP Jenkins failures on 16-6-2016 10-57 AM.xls");
      webController.sendKeys("//*[@id='mainpage_txtRecipientName']", "nagaraju.meka");
      webController.delay(2);
      webController.click("css=input[id='mainpage_btnAddRec']");
      webController.delay(2);
      webController.sendKeys("css=input[id='mainpage_tbSubject']", "Jenkins failures");
      webController.click("css=input[id='mainpage_btnUpload']");
   }
   public static final String SKIP = "//h1[contains(text(), 'Skipped')]";
   public static final String CONSOLE_OUTPUT = "//a[contains(text(), 'Console Output')]";


   @Test
   public void testMobile()
   {
      String url = null;
      GmailScreen gs = new GmailScreen(webController.getWebDriver());
      boolean flag = false;
      if ( !webController.isElementVisible("//*[@id='container']/div/div/header/div[2]/div/div[1]/ul/li[8]/a"))
      {
         webController.reload();
         webController.reload();
      }
      webController.waitForElementVisible("//*[@id='container']/div/div/header/div[2]/div/div[1]/ul/li[8]/a", 50);
      webController.click("//*[@id='container']/div/div/header/div[2]/div/div[1]/ul/li[8]/a");
      webController.sendKeys("//*[@id='fk-mainbody-id']/div/div/div[1]/div/div[4]/div[2]/input", "inagaraju4u@gmail.com");
      webController.sendKeys("//*[@id='fk-mainbody-id']/div/div/div[1]/div/div[4]/div[4]/input", "N@garaju01");
      webController.click("//*[@id='fk-mainbody-id']/div/div/div[1]/div/div[4]/div[7]/input");
      for (int a = 1; a <= 900000000; a++)
      {
         for (int b = 1; b <= 900000000; b++)
         {
            for (int c = 1; c <= 900000000; c++)
            {
               for (int d = 1; d <= 900000000; d++)
               {
                  for (int e = 1; e <= 900000000; e++)
                  {
                     for (int f = 1; f <= 900000000; f++)
                     {
                        for (int g = 1; g <= 900000000; g++)
                        {
                           for (int h = 1; h <= 900000000; h++)
                           {
                              for (int i = 1; i <= 900000000; i++)
                              {
                                 for (int j = 1; j <= 900000000; j++)
                                 {
                                    webController.get("https://www.flipkart.com/wishlist?link=home_wishlist");
                                    if (webController.isElementVisible("//div[@id='wishlist']/div[2]//input[@value='Add to Cart']"))
                                    {
                                       log.info("Black is available");
                                       url = webController.getCurrentUrl();
                                       gs.doLogin("nagaraju.meka.accenture@gmail.com", "Accenture0", "Boss Gold device is available");
                                       webController.navigateTo(url);
                                       flag = true;
                                    }
                                    if (webController.isElementVisible("//div[@id='wishlist']/div[3]//input[@value='Add to Cart']"))
                                    {
                                       log.info("Silver is available");
                                       url = webController.getCurrentUrl();
                                       gs.doLogin("nagaraju.meka.accenture@gmail.com", "Accenture0", "Boss Black device is available");
                                       webController.navigateTo(url);
                                       flag = true;
                                    }
                                    if (webController.isElementVisible("//div[@id='wishlist']/div[4]//input[@value='Add to Cart']"))
                                    {
                                       log.info("Gold is available");
                                       url = webController.getCurrentUrl();
                                       gs.doLogin("nagaraju.meka.accenture@gmail.com", "Accenture0", "Boss Silver device is available");
                                       webController.navigateTo(url);
                                       flag = true;
                                    }
                                    if ( !flag)
                                    {
                                       log.info("Checking " + a + " " + b + " " + c + " " + d + " " + e + " " + f + " " + g + " " + h + " " + i + " " + j + " ");
                                       webController.reload();
                                       log.info("Mobiles not available");
                                       webController.delay(30);
                                    }
                                    else
                                    {
                                       flag = false;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
