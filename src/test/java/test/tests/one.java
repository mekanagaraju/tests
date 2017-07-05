package test.tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import test.screen.GmailScreen;

public class one
{
   protected static final Logger log = LoggerFactory.getLogger(one.class);
   private GmailScreen gmail;
   public TestName name = new TestName();
   WebDriver driver = null;
   SingleSeleniumSolution webDriver = new SingleSeleniumSolution(driver);
   String url = null;


   // @BeforeMethod(alwaysRun = true)
   public void beforeMethod(Method method)
   {
      url = "https://mysvcs-staging-bravo.dev-webapps.timewarnercable.com/";
      // url = "http://V595865:CAFAAutomation%401@scorecardqa.twcable.com/carolinas/";
      webDriver = SingleSeleniumSolution.buildWebDriver(url, "chrome", method);
   }


   @AfterMethod(alwaysRun = true)
   public void afterMethod()
   {
      webDriver.quit();
   }


   @Test
   public void fileupload()
   {
      /* WebDriver driver = new FirefoxDriver();
       * driver.get("https://www.wetransfer.com/"); */
      webDriver.get("https://www.wetransfer.com/");
      webDriver.delay(50);
      driver.findElement(By.xpath("//*[@id='takeover-skip']")).click();
      driver.findElement(By.xpath("//*[@id='accepting']")).click();
      driver.findElement(By.xpath("//*[@id='uploader_field']")).sendKeys("C:\\Apollo\\Apollo_Input_Grid.xls");
      driver.findElement(By.xpath("//*[@id='add-recipient']")).click();
      driver.findElement(By.xpath("//*[@id='to']")).sendKeys("nagaraju.meka@accenture.com");
      driver.findElement(By.xpath("//*[@id='add-sender']")).click();
      driver.findElement(By.xpath("//*[@id='from']")).sendKeys("nagaraju.meka@accenture.com");
      driver.findElement(By.xpath("//*[@id='message']")).sendKeys("Test message");
      driver.findElement(By.xpath("//*[@id='transfer']")).click();
   }


   @Test(enabled = false)
   public void cyara()
   {
      WebDriver driver = new FirefoxDriver();
      driver.get("http://cyrpapp01.corp.twcable.com/CyaraWebPortal/public/Login");
      driver.manage().window().maximize();
      driver.findElement(By.xpath("//*[@id='Username']")).sendKeys("shobhit.mishra");
      driver.findElement(By.xpath("//*[@id='Password']")).sendKeys("Bangalore@1");
      driver.findElement(By.xpath("//*[@id='loginButton']")).click();
      driver.findElement(By.className("dropdown-toggle")).click();
      driver.findElement(By.linkText("Test Cases & Blocks")).click();
      driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
      driver.findElement(By.linkText("Residential - With Blocks")).click();
      driver.findElement(By.linkText("GenericPortal")).click();
      String testCaseCount = driver.findElement(By.xpath("//*[@class='pull-left paginate-summary']//span[3]")).getText();
      int testCount = Integer.parseInt(testCaseCount);
      for (int i = 1; i <= testCount; i++)
      {
         String element = "//*[@id='testblocksgrid']/tbody/tr[" + i + "]/td[3]/a";
         String testCaseSteps = driver.findElement(By.xpath("//div[@id='testGridContainer']//table[1]//tbody[1]//tr[" + i + "]//td[5]")).getText();
         int testSteps = Integer.parseInt(testCaseSteps);
         log.info("Updating the " + i + " module");
         driver.findElement(By.xpath(element)).click();
         driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
         for (int z = 1; z <= testSteps; z++)
         {
            String elementnew = "//*[@class='edtStep editable editable-click editable-disabled'][contains(text(), '" + z + "')]";
            WebElement we = driver.findElement(By.xpath(elementnew));
            log.info("Updating the " + z + " test case");
            doubleClick(we, driver);
            driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id='stepsgrid']/tbody//td[10]/a")).click();
            driver.findElement(By.cssSelector("input.input-medium")).clear();
            driver.findElement(By.cssSelector("input.input-medium")).sendKeys("90");
            driver.findElement(By.xpath("//*[@class='icon-ok icon-white']")).click();
            driver.findElement(By.id("saveButton")).click();
            log.info("Updated the " + z + " test case scuccessfully");
            driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
         }
         driver.findElement(By.xpath("//*[@id='saveButton']")).click();
         log.info("Updated the " + i + " module scuccessfully");
         driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
      }
   }


   public void doubleClick(WebElement elementnew, WebDriver driver)
   {
      try
      {
         Actions action = new Actions(driver);
         action.moveToElement(elementnew).perform();
         action.doubleClick(elementnew);
         action.perform();
         log.info("Double clicked the test case");
      } catch (StaleElementReferenceException e)
      {
         log.info("Element is not attached to the page document " + e.getStackTrace());
      } catch (NoSuchElementException e)
      {
         log.info("Element " + elementnew + " was not found in DOM " + e.getStackTrace());
      } catch (Exception e)
      {
         log.info("Element " + elementnew + " was not clickable " + e.getStackTrace());
         e.printStackTrace();
      }
   }


   @Test(enabled = false)
   public void sampleTest()
   {
      for (int i = 1; i <= 1000; i++)
      {
         String value = null;
         do
         {
            double start = 2.01;
            double end = 4.99;
            double random = new Random().nextDouble();
            double result = start + (random * (end - start));
            DecimalFormat cents = new DecimalFormat("#.##");
            value = cents.format(result);
            if (value.length() == 4)
            {
               System.out.println(value);
               break;
            }
         } while ( !(value.length() == 4));
      }
   }


   @Test()
   public void randomDollarValue()
   {
      for (int i = 1; i <= 100; i++)
      {
         double start = 2.01;
         double end = 6.98;
         double random = new Random().nextDouble();
         double result = start + (random * (end - start));
         final DecimalFormat cents = new DecimalFormat("#.##");
         String value = cents.format(result);
         if (value.length() < 2.01 || value.length() > 6.99)
         {
            value = "5.52";
         }
         log.info("Amount is : " + value);
      }
   }


   @Test
   public void openBrowser()
   {
      InternetExplorerDriver driver = new InternetExplorerDriver();
      System.setProperty("webdriver.ie.driver", "C:/selenium/IEDriverServer.exe");
      driver.get("https://mysvcs-staging-charlie.dev-webapps.timewarnercable.com/");
      driver.get("javascript:document.getElementById('overridelink').click()");
   }


   @Test(enabled = false)
   public void Safaribrowser()
      throws InterruptedException, IOException
   {
      SafariDriver driver = new SafariDriver();
      driver.get("https://mysvcs-staging-charlie.dev-webapps.timewarnercable.com/");
      Runtime.getRuntime().exec("C:\\Users\\nagaraju.meka\\Desktop\\continue.exe ");
      /* Thread.sleep(3000);
       * Robot rb;
       * try {
       * rb = new Robot();
       * rb.keyPress(KeyEvent.VK_ENTER);
       * // rb.keyRelease(KeyEvent.VK_ENTER);
       * } catch (AWTException e) {
       * // TODO Auto-generated catch block
       * e.printStackTrace(); */
   }


   @Test(enabled = false)
   public void sleep()
   {
      try
      {
         TimeUnit.SECONDS.sleep(60);
         TimeUnit.MINUTES.sleep(1);
      } catch (InterruptedException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }


   @Test(enabled = true)
   public void splitTheString()
   {
      String text = "12/08/2014 04:08 PM";
      String[] q = text.split(" ");
      int p = q.length;
      if (p <= 2)
      {
         System.out.println(text);
      }
      else if (p >= 3)
      {
         String[] r = text.split(" ", 3);
         System.out.println(r[0] + " " + r[2]);
      }
   }


   @Test
   public void splitTime()
   {
      ArrayList<String> values = new ArrayList<String>();
      String pMailTime = "12:11p";
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
   }


   @Test
   public void getSystemDay()
   {
      TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
      String timeFormat = "h:mm a";
      DateFormat dateFormat = new SimpleDateFormat(timeFormat);
      Calendar cal = Calendar.getInstance(timeZone);
      dateFormat.setTimeZone(cal.getTimeZone());
      String currentTime = dateFormat.format(cal.getTime());
      System.out.println("Current Time: " + currentTime);
   }


   @Test(enabled = true)
   public void numberReplace()
   {
      ArrayList<String> systemtime = getSystemTime();
      String systemTime = systemtime.get(0);
      String AcceptanceSystemTime = systemtime.get(1);
      systemTime = returnTimeValue(systemTime);
      AcceptanceSystemTime = returnTimeValue(AcceptanceSystemTime);
      // String systemTime = "1:09 pm";
      String mailTime = "12:48 pm";
      int mailTim = 1248;
      int sysTime = 109;
      boolean flag = false;
      boolean acceptTime = false;
      String VMailCal = mailTime;
      VMailCal = returnTimeFormat(VMailCal);
      String VSysCal = systemTime;
      VSysCal = returnTimeFormat(VSysCal);
      if (VMailCal.equals(VSysCal)) // am == am or pm == pm
      {
         flag = true;
      }
      if (acceptanceTime(intParsint(systemTime), intParsint(AcceptanceSystemTime), mailTim) == true) // 10:00 - 10:15
      {
         acceptTime = true;
      }
      if ((mailTim < sysTime) || (flag == false) || (acceptTime == false))
      {
         log.info("Refreshing the mail box for New Mails...");
      }
      else if ((mailTim >= sysTime) && (flag == true) && (acceptTime == true))
      {
         log.info("New Mail came, Validating the subject");
      }
   }


   @Test(enabled = true)
   public void timeRoundFigure()
   {
      Date dNow = new Date();
      DateFormat newForm = new SimpleDateFormat("h:mm a");
      String newTime1 = newForm.format(dNow);
      newTime1 = newTime1.toLowerCase();
      log.info("Current System Time : " + newTime1);
      Calendar cal = Calendar.getInstance();
      cal.setTime(dNow);
      cal.add(Calendar.MINUTE, 15);
      dNow = cal.getTime();
      newForm = new SimpleDateFormat("h:mm a");
      String newTime2 = newForm.format(dNow);
      newTime2 = newTime2.toLowerCase();
      log.info("Acceptance System Time : " + newTime2);
      newTime1 = newTime1.replaceAll(" pm", "").replaceAll(" am", "").replace(":", "");
      newTime2 = newTime2.replaceAll(" pm", "").replaceAll(" am", "").replace(":", "");
      int time1 = Integer.parseInt(newTime1);
      int time2 = Integer.parseInt(newTime2);
      int mailTime = 108;
      int currentMailTime = 1246;
      int sysTime = time1;
      for (int i = time1; i <= time2; i++)
      {
         if ((i == mailTime) && (currentMailTime >= sysTime))
         {
            log.info("Sucess: " + i);
            break;
         }
         else
         {
            log.info("Fail: " + i);
         }
      }
   }


   public int intParsint(String ParssintValue)
   {
      int value = Integer.parseInt(ParssintValue);
      return value;
   }


   public boolean acceptanceTime(int sysTime, int accpTime, int mailTime)
   {
      boolean flag = false;
      for (int i = sysTime; i <= accpTime; i++)
      {
         if (i == mailTime)
         {
            flag = true;
            break;
         }
         else
         {
            flag = false;
         }
      }
      return flag;
   }


   public String returnTimeFormat(String text)
   {
      text = text.replace(":", "").replaceAll("\\d", "").replaceAll(".", "").replaceAll(" ", "");
      return text;
   }


   public String returnTimeValue(String text)
   {
      text = text.replace(":", "").replaceAll(" pm", "").replaceAll(" am", "").replaceAll(" ", "");
      return text;
   }


   public ArrayList<String> getSystemTime()
   {
      ArrayList<String> sysTime = new ArrayList<String>();
      Date dNow = new Date();
      DateFormat newForm = new SimpleDateFormat("h:mm a");
      String newTime1 = newForm.format(dNow);
      newTime1 = newTime1.toLowerCase();
      // log.info("Current System Time : " + newTime1);
      sysTime.add(newTime1);
      Calendar cal = Calendar.getInstance();
      cal.setTime(dNow);
      cal.add(Calendar.MINUTE, 15);
      dNow = cal.getTime();
      newForm = new SimpleDateFormat("h:mm a");
      String systemTime = newForm.format(dNow);
      systemTime = systemTime.toLowerCase();
      log.info("Acceptance System Time : " + systemTime);
      sysTime.add(systemTime);
      return sysTime;
   }


   @Test(enabled = true)
   public void extractFileName()
   {
      Date dNow = new Date();
      DateFormat newForm = new SimpleDateFormat("h:mm a");
      String newTime1 = newForm.format(dNow);
      newTime1 = newTime1.toLowerCase();
      String path = "onclick='window.open('admin/act_excel.cfm?filename=ems_exception_V566596Aug132015.xls','','status=no,toolbar=no,menubar=no,resizable=yes');'";
      String path1 = path.split("=")[2];
      path1.split("'");
   }


   @Test
   public void getFileName()
   {
      BufferedReader br = null;
      String everything = null;
      try
      {
         br = new BufferedReader(new FileReader("C:\\Windows\\notepad.exe"));
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      try
      {
         Robot r = new Robot();
         r.keyPress(KeyEvent.VK_CONTROL);
         r.keyPress(KeyEvent.VK_V);
         r.keyRelease(KeyEvent.VK_CONTROL);
         r.keyRelease(KeyEvent.VK_V);
      } catch (AWTException e1)
      {
         e1.printStackTrace();
      }
      try
      {
         StringBuilder sb = new StringBuilder();
         String line = null;
         try
         {
            line = br.readLine();
         } catch (IOException e)
         {
            e.printStackTrace();
         }
         while (line != null)
         {
            sb.append(line);
            sb.append(System.lineSeparator());
            try
            {
               line = br.readLine();
            } catch (IOException e)
            {
               e.printStackTrace();
            }
         }
         everything = sb.toString();
      } finally
      {
         try
         {
            br.close();
         } catch (IOException e)
         {
            e.printStackTrace();
         }
      }
      System.out.println(everything);
      /* String path= System.getProperty("user.home");
       * path = path + "\\downloads\\";
       * String FilePath = path+"ems_exception_V566596Aug132015.xls";
       * System.out.println(FilePath);
       * String q = "http://scorecardqa.twcable.com/carolinas/pdf/userAAD39A492789B810B5D274003A0C5A6C.pdf";
       * String path1 = q.split("pdf/")[1];
       * System.out.println(path1); */
   }


   @Test
   public void first()
   {
      String text = "Cable (16)";
      text = text.trim();
      String[] b = text.split(" ", 2);
      System.out.println(b[0].replace("Job Id ", "").trim());
      System.out.println(b[1].replace("Scheduled Date", "").trim());
      System.out.println(b[3].replace("Scheduled Time", "").trim());
      System.out.println(b[4].replace("Tech Number", "").trim());
      System.out.println(b[5].trim());
      // abcScreen abcScreen = new abcScreen();
      // abcScreen.abcScreen();
   }


   @Test
   public void deleteFiles()
   {
      File dir = new File("c:/apps/jenkins/workspace");
      File[] currList;
      Stack<File> stack = new Stack<File>();
      stack.push(dir);
      while ( !stack.isEmpty())
      {
         if (stack.lastElement().isDirectory())
         {
            currList = stack.lastElement().listFiles();
            if (currList.length > 0)
            {
               for (File curr : currList)
               {
                  stack.push(curr);
               }
            }
            else
            {
               stack.pop().delete();
            }
         }
         else
         {
            stack.pop().delete();
         }
      }
   }


   @Test
   public void browserVersion()
   {
      String q = getBrowserVersion();
      String[] b = q.split("Chrome/");
      String[] c = b[1].split(" ", 2);
      String d = c[0].substring(0, 3);
      log.info("Chrome/" + d);
      webDriver.quit();
   }


   public String getBrowserVersion()
   {
      String browsername = webDriver.getBrowserName();
      String browser_version = webDriver.getBrowserVersion();
      return browsername + " vers. " + browser_version;
   }


   @Test
   public void reverseForLoop()
   {
      int value;
      value = 5;
      for (int q = value; q > 3; q--)
      {
         System.out.println(q);
      }
      log.info("");
   }


   @Test
   public void randomDollarValues()
   {
      double randomDollar = ((int)(Math.random() * 100) / 100.0) + 1;
      log.info(" " + randomDollar);
      log.info("Amount is: " + String.format("%.2f", randomDollar));
      double start = 2.01;
      double end = 4.99;
      double random = new Random().nextDouble();
      double result = start + (random * (end - start));
      log.info("Amount is: " + String.format("%.2f", result));
   }


   @Test
   public void removeDuplicates()
   {
      ArrayList<String> list = new ArrayList<String>();
      list.add("Krishna");
      list.add("Krishna");
      list.add("Kishan");
      list.add("Krishn");
      list.add("Aryan");
      list.add("Harm");
      log.info("List" + list);
      HashSet hs = new HashSet();
      hs.addAll(list);
      list.clear();
      list.addAll(hs);
      log.info("List" + list);
      log.info("List" + hs);
      webDriver.quit();
   }


   @Test
   private void removeDuplicates(List< ? > list)
   {
      int count = list.size();
      for (int i = 0; i < count; i++)
      {
         for (int j = i + 1; j < count; j++)
         {
            if (list.get(i).equals(list.get(j)))
            {
               list.remove(j--);
               count--;
            }
         }
      }
   }


   @Test
   public void getSystemDate()
   {
      SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
      String date = sdf.format(new Date());
      System.out.println(date);
   }


   @Test
   public void getSystemDateTime()
   {
      int q = 1;
      for (int i = 1; i <= 10; i++)
      {
         q = q + 1;
      }
      SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
      String date = sdf.format(new Date());
      log.info(date);
      SimpleDateFormat sdfd = new SimpleDateFormat("h:mm a");
      date = sdfd.format(new Date());
      log.info(date);
   }


   @Test
   public void getBackUpCount()
   {
      int backupCount = 776, rowCount = 800, selectRow, run = 8;
      ;
      selectRow = rowCount - backupCount;
      selectRow = 100 - selectRow;
      log.info("Count : " + selectRow);
      rowCount = (run * 100) - backupCount;
      selectRow = 100 - rowCount;
      log.info("Count : " + selectRow);
   }


   @Test
   public void getText()
   {
      webDriver.get("https://mail.google.com/mail");
      webDriver.delay(5);
      String text = webDriver.getText("//div[@style='overflow: hidden;']/div[2]");
      // log.info(text);
      String b[] = text.split("\n");
      // log.info(" " + b[1]);
      // log.info(" " + b[2]);
      String a = b[2].replace("Your My Account password has been reset. The new password is ", "").replace(".", "");
      log.info(" " + a.trim());
   }


   @Test
   public void randomString()
   {
      String strType = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      String intType = "0123456789";
      Random rnd = new Random();
      StringBuilder sba = new StringBuilder();
      StringBuilder sbb = new StringBuilder();
      for (int i = 0; i < 10; i++)
      {
         sba.append(strType.charAt(rnd.nextInt(strType.length())));
         if (i >= 5)
         {
            sbb.append(intType.charAt(rnd.nextInt(intType.length())));
         }
      }
      log.info("Value :" + sba + sbb);
   }


   @Test
   public void testDragAndDropExample()
   {
      driver = new FirefoxDriver();
      driver.manage().window().maximize();
      driver.navigate().to("http://jqueryui.com/droppable/");
      // Wait for the frame to be available and switch to it
      WebDriverWait wait = new WebDriverWait(driver, 5);
      wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".demo-frame")));
      WebElement Sourcelocator = driver.findElement(By.cssSelector(".ui-draggable"));
      WebElement Destinationlocator = driver.findElement(By.cssSelector(".ui-droppable"));
      dragAndDrop(Sourcelocator, Destinationlocator);
      String actualText = driver.findElement(By.cssSelector("#droppable>p")).getText();
      Assert.assertEquals(actualText, "Dropped!");
   }


   public String[][] dragAndDrop(WebElement sourceElement, WebElement destinationElement)
   {
      String[][] d = { { "shiv", "kumar" }, { "naga", "raju" }, { "some", "thing" } };
      System.out.println("no. of string arrays - " + d.length + " no. of strings in one array - " + d[0].length);
      (new Actions(driver)).dragAndDrop(sourceElement, destinationElement).perform();
      return d;
   }


   @Test
   public Boolean checkScrollBarPresence()
   {
      String execScript = "return document.documentElement.scrollHeight>document.documentElement.clientHeight;";
      JavascriptExecutor scrollBarPresent = (JavascriptExecutor)driver;
      Boolean flag = (Boolean)(scrollBarPresent.executeScript(execScript));
      return flag;
   }


   /* @Test
    * public void copyTestNGReport()
    * {
    * File source = new
    * File("C:/Users/nagaraju.meka/Perforce/AAD-23-03-2015/SIT/atf-main/system-controllers/aad-web8/test-output");
    * SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy h.mm.ss a");
    * String fileName = sdf.format(new Date());
    * fileName = "test-output " + fileName;
    * File dest = new File("C:/SeleniumtTestNGTestResults/");
    * if (!dest.exists())
    * {
    * dest.mkdir();
    * }
    * dest = new File("C:/SeleniumtTestNGTestResults/" + fileName);
    * try
    * {
    * // FileUtils.copyDirectory(source, dest);
    * } catch (IOException e)
    * {}
    * } */
   @Test
   public void rightClick()
   {
      String element = "//span[contains(text(), 'right click me')]";
      webDriver.waitForElementVisible(element, 30);
      webDriver.rightClick(element);
      webDriver.delay(90);
   }


   @Test
   public void getTexts()
   {
      String text = "/scorecardqa.twcable.com/midwest/teamlogo/aces.jpg";
      String reverse = new StringBuffer(text).reverse().toString();
      String texts[] = reverse.split("/", 2);
      reverse = new StringBuffer(texts[0]).reverse().toString();
      log.info(reverse);
   }


   @Test
   public void openGmailTest()
   {
      gmail = new GmailScreen(webDriver.getWebDriver());
      gmail.openValidMail("cafasittest@gmail.com", "cafasitpass1234", "");
   }


   @Test
   public void testEdge()
   {
      EdgeOptions options = new EdgeOptions();
      options.setPageLoadStrategy("eager");
      driver = new EdgeDriver(options);
      driver.get("https://mysvcs-staging-charlie.dev-webapps.timewarnercable.com/");
      webDriver.getBrowserName();
   }


   public void overrideEdgeCertificateError()
   {
      String INVALID_CONTINUE = "css=id#invalidcert_continue";
      try
      {
         log.info("Security Warning Present ...");
         webDriver.waitForElementFound(INVALID_CONTINUE);
         webDriver.click(INVALID_CONTINUE);
         log.info("Skipping Certificate Warning !!! ...");
      } catch (Exception e)
      {
         log.info("Certificate Warning not Present");
      }
   }


   @Test
   public void testSplit()
   {
      // String OS = System.getProperty("os.name");
      // log.info(OS);
      String endDate = "Thu, Jan 01, '15".replace("'", "");
      String date = "Thu, Dec 18, '14".replace("'", "");
      String cycleDate[] = endDate.split(", ");
      String termDate[] = date.split(", ");
      int intEndDate = Integer.parseInt(cycleDate[2]);
      int intTermDate = Integer.parseInt(termDate[2].replace(",", ""));
      if (intTermDate < intEndDate)
      {
         log.info("Employee's Term date {}, Scorecard's Report date {}" + date + endDate);
      }
      else
      {
         Assert.fail("Employee's Term date " + date + " is not with in the cycle " + endDate);
      }
   }


   public String extractSystemSettings()
   {
      return System.getProperty("settingSystem");
   }


   @Test
   public void extractComputerName()
   {
      String computername = "";
      computername = System.getenv("COMPUTERNAME");
      if (computername.equals(""))
      {
         try
         {
            computername = InetAddress.getLocalHost().getHostName();
         } catch (UnknownHostException e)
         {}
      }
      log.info("Computer Name: " + computername);
   }


   public void scrollPerticularDiv1(String element)
   {
      JavascriptExecutor js = (JavascriptExecutor)driver;
      String s = String.format("var topPos = document.querySelector(\"" + element + "\"); topPos.scrollIntoView(false); ");
      js.executeScript(s);
   }


   public void scrollPerticularDiv2(String element)
   {
      JavascriptExecutor js = (JavascriptExecutor)driver;
      String s = String.format("$(\"" + element + "\").animate({ scrollTop: \"100px\" })");
      js.executeScript(s);
   }


   public void scrollPerticularDiv3(String element)
   {
      Actions action = new Actions(webDriver.getWebDriver());
      action.moveToElement(webDriver.findElement(element)).click().build().perform();
   }


   @Test(description = "Nagaraju")
   public void testVersion()
   {
      driver.get("http://crmqapp01.twcable.com:7240/crm/login.html");
      WebElement e = (new WebDriverWait(driver, 90)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span[id='acrmLoginVersionNumber']")));
      String version = e.getText();
      log.info(" " + version);
   }


   public void contains(String string, CharSequence str)
   {
      if ( !string.contains(str))
      {
         abbreviatedString(string);
         abbreviatedString(str);
      }
   }
   public static final int MAX_PRINTED_ELEMENTS = 8;
   public static final int MAX_STRING_LENGTH = 250;


   public static String abbreviatedString(Object object)
   {
      if (object == null)
      {
         return "null";
      }
      String s = String.valueOf(object);
      if (s.length() > MAX_STRING_LENGTH)
      {
         s = s.substring(0, MAX_STRING_LENGTH) + " ...";
      }
      return s;
   }


   public void abbreviatedString()
   {
      for (String windowHandle : webDriver.getWebDriver().getWindowHandles())
      {
         webDriver.getWebDriver().switchTo().window(windowHandle);
         webDriver.getWebDriver().getCurrentUrl();
      }
   }


   public String formString(Object... a)
   {
      String obj = "";
      for (Object b : a)
      {
         obj += b.toString();
      }
      return obj;
   }
   public static final String TAB_ADMIN_ICOMS = "//ul[@id='listMenuRoot']/li[5]";


   @Test
   public void testOverFlow()
   {
      String a = TAB_ADMIN_ICOMS + "/a";
      String b = TAB_ADMIN_ICOMS + "/ul/li[11]/a";
      String c = TAB_ADMIN_ICOMS + "/ul/li[11]/ul/li[1]/a";
      webDriver.moveToElementAndClick(a, b, c);
   }
   public static final String ROSTERLINK = "css=span[class='lnks'] a[href='index.cfm?fuseaction=ccf.default']";
   public static final String INPUT_USER = "css=input[id='txtUsername']";
   public static final String INPUT_PASSWORD = "css=input[name='password']";
   public static final String BUTTON_LOGIN = "css=input[class='submit']";


   @Test
   public void testPMT()
   {
      webDriver.waitForElementVisible(INPUT_USER, 300);
      webDriver.type(INPUT_USER, "V566593");
      webDriver.type(INPUT_PASSWORD, "pass");
      webDriver.click(BUTTON_LOGIN);
      // webDriver.waitForElementVisible(ROSTERLINK, 60);
      webDriver.click(ROSTERLINK);
      webDriver.moveToElementAndClick("//nav[@id='primary_nav_wrap']/ul/li/a", "//nav[@id='primary_nav_wrap']/ul/li/ul/li[1]/a");
   }


   @Test
   public void dateAddition()
   {
      Date dNow = new Date();
      DateFormat newForm = new SimpleDateFormat("dd/MM/yyyy");
      newForm.format(dNow);
      Calendar cal = Calendar.getInstance();
      cal.setTime(dNow);
      cal.add(Calendar.DATE, 1);
      String newDate = newForm.format(cal.getTime());
      log.info("" + newDate);
   }


   @Test
   public void testCheckAssert()
   {
      String ac = "07/06/2017 17:36:31";
      String ex = "07/06/2017 17:36:32";
      assertEquals(ac, ex);
   }


   protected void assertEquals(String expectedValue, String actualValue)
   {
      String updatedTimeValue = null, newTimeStamp = null;
      String[] tempExpDate = expectedValue.split(" ");
      String[] tempActDate = actualValue.split(" ");
      if (tempExpDate[0].equals(tempActDate[0]))
      {
         updatedTimeValue = tempExpDate[0];
         newTimeStamp = tempActDate[0];
         log.info("Passed: --> expected { " + updatedTimeValue + " } Equals actual --> { " + newTimeStamp + " }");
      }
      else
      {
         updatedTimeValue = tempExpDate[0];
         newTimeStamp = tempActDate[0];
         log.info("Failed: --> expected { " + updatedTimeValue + " } Not equals with actual --> { " + newTimeStamp + " }");
      }
      String[] tempExpTime = tempExpDate[1].split(":");
      String[] tempActTime = tempActDate[1].split(":");
      if (tempExpTime[0].equals(tempActTime[0]))
      {
         updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0];
         newTimeStamp = tempActDate[0] + " " + tempActTime[0];
         log.info("Passed: --> expected { " + updatedTimeValue + " } Equals actual --> { " + newTimeStamp + " }");
         if (tempExpTime[1].equals(tempActTime[1]))
         {
            updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0] + ":" + tempExpTime[1];
            newTimeStamp = tempActDate[0] + " " + tempActTime[0] + ":" + tempActTime[1];
            log.info("Passed: --> expected { " + updatedTimeValue + " } Equals actual --> { " + newTimeStamp + " }");
            if (tempExpTime[2].equals(tempActTime[2]))
            {
               updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0] + ":" + tempExpTime[1] + ":" + tempExpTime[2];
               newTimeStamp = tempActDate[0] + " " + tempActTime[0] + ":" + tempActTime[1] + ":" + tempActTime[2];
               log.info("Passed: --> expected { " + updatedTimeValue + " } Equals actual --> { " + newTimeStamp + " }");
            }
            else
            {
               int secondsExp = Integer.parseInt(tempExpTime[2]);
               int secondsAct = Integer.parseInt(tempActTime[2]);
               log.info("Time Seconds are not matching, checking the tolerance");
               int negetiveValue = secondsExp - secondsAct;
               if (negetiveValue <= 2)
               {
                  updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0] + ":" + tempExpTime[1] + ":" + tempExpTime[2];
                  newTimeStamp = tempActDate[0] + " " + tempActTime[0] + ":" + tempActTime[1] + ":" + tempActTime[2];
                  log.info("Passed: --> expected { " + updatedTimeValue + " } Equals actual --> { " + newTimeStamp + " }");
               }
               else
               {
                  updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0] + ":" + tempExpTime[1] + ":" + tempExpTime[2];
                  newTimeStamp = tempActDate[0] + " " + tempActTime[0] + ":" + tempActTime[1] + ":" + tempActTime[2];
                  log.info("Failed: --> expected { " + updatedTimeValue + " } Not equals with actual --> { " + newTimeStamp + " }");
               }
            }
         }
         else
         {
            updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0] + ":" + tempExpTime[1];
            newTimeStamp = tempActDate[0] + " " + tempActTime[0] + ":" + tempActTime[1];
            log.info("Time Minutes are not matching, checking the tolerance");
            int hoursExp = Integer.parseInt(tempExpTime[1]);
            int hoursAct = Integer.parseInt(tempActTime[1]);
            int negetiveValue = hoursExp - hoursAct;
            if (negetiveValue <= 1)
            {
               log.info("Passed: --> expected { " + updatedTimeValue + " } Equals actual --> { " + newTimeStamp + " }");
               int secondsExp = Integer.parseInt(tempExpTime[2]);
               int secondsAct = Integer.parseInt(tempActTime[2]);
               log.info("Time Seconds are not matching, checking the tolerance");
               negetiveValue = secondsExp - secondsAct;
               if (negetiveValue <= 2)
               {
                  updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0] + ":" + tempExpTime[1] + ":" + tempExpTime[2];
                  newTimeStamp = tempActDate[0] + " " + tempActTime[0] + ":" + tempActTime[1] + ":" + tempActTime[2];
                  log.info("Passed: --> expected { " + updatedTimeValue + " } Equals actual --> { " + newTimeStamp + " }");
               }
               else
               {
                  updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0] + ":" + tempExpTime[1] + ":" + tempExpTime[2];
                  newTimeStamp = tempActDate[0] + " " + tempActTime[0] + ":" + tempActTime[1] + ":" + tempActTime[2];
                  log.info("Failed: --> expected { " + updatedTimeValue + " } Not equals with actual --> { " + newTimeStamp + " }");
               }
            }
            else
            {
               updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0] + ":" + tempExpTime[1];
               newTimeStamp = tempActDate[0] + " " + tempActTime[0] + ":" + tempActTime[1];
               log.info("Failed: --> expected { " + updatedTimeValue + " } Not equals with actual --> { " + newTimeStamp + " }");
            }
         }
      }
      else
      {
         updatedTimeValue = tempExpDate[0] + " " + tempExpTime[0];
         newTimeStamp = tempActDate[0] + " " + tempActTime[0];
         log.info("Failed: --> expected { " + updatedTimeValue + " } Not equals with actual --> { " + newTimeStamp + " }");
      }
   }
}
