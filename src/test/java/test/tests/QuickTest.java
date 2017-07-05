package test.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.text.WordUtils;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class QuickTest
{
   protected static final Logger log = LoggerFactory.getLogger(QuickTest.class);
   public TestName name = new TestName();
   WebDriver driver = null;
   SingleSeleniumSolution webController = new SingleSeleniumSolution(driver);


   @Test
   public void reversing()
   {
      int num = 12345;
      int reversenum = 0;
      while (num != 0)
      {
         reversenum = reversenum * 10;
         reversenum = reversenum + num % 10;
         num = num / 10;
      }
      System.out.println("" + reversenum);
      num = reversenum;
      reversenum = 0;
      for (; num != 0;)
      {
         reversenum = reversenum * 10;
         reversenum = reversenum + num % 10;
         num = num / 10;
      }
      System.out.println("" + reversenum);
   }


   @Test
   public void circumferenceOfCircle()
   {
      // double radius = 1;
      int radius = 1;
      double area = Math.PI * (radius * radius);
      System.out.println("The area of circle is: " + area);
      double circumference = Math.PI * 2 * radius;
      System.out.println("The circumference of the circle is: " + circumference);
   }


   @Test
   public void calculateAreaOfTriangle()
   {
      double base = 20.0;
      double height = 110.5;
      double area = (base * height) / 2;
      System.out.println("Area of Triangle is: " + area);
   }


   @Test
   public void sumOfArray()
   {
      // Advanced for loop
      int[] array = { 10, 20, 30, 40, 50, 10 };
      int sum = 0;
      for (int num : array)
      {
         sum = sum + num;
      }
      System.out.println("Sum of array elements is: " + sum);
      // simple if loop
      int[] arrays = { 10, 20, 30, 40, 50, 60 };
      int sums = 0;
      System.out.println("Enter the elements: ");
      for (int num : arrays)
      {
         sums = sums + num;
      }
      System.out.println("Sum of array elements is: " + sums);
   }


   @Test
   public void checkPrimeNumber()
   {
      int temp;
      boolean isPrime = true;
      Scanner scan = new Scanner(System.in);
      System.out.println("Enter a number for check:");
      // capture the input in an integer
      int num = scan.nextInt();
      for (int i = 2; i <= num / 2; i++)
      {
         temp = num % i;
         if (temp == 0)
         {
            isPrime = false;
            break;
         }
      }
      // If isPrime is true then the number is prime else not
      if (isPrime)
      {
         System.out.println(num + " is Prime Number");
      }
      else
      {
         System.out.println(num + " is not Prime Number");
      }
   }


   @Test
   private void checkEvenOrOddNumber()
   {
      int num;
      num = 109;
      if (num % 2 == 0)
      {
         System.out.println(num + " number is even");
      }
      else
      {
         System.out.println(num + " number is odd");
      }
   }


   @Test
   private void arraySort()
   {
      int iArr[] = { 2, 1, 9, 6, 4 };
      for (int number : iArr)
      {
         System.out.println("Number = " + number);
      }
      Arrays.sort(iArr);
      System.out.println("The sorted int array is:");
      for (int number : iArr)
      {
         System.out.println("Number = " + number);
      }
   }


   @Test
   public void largestSmallestNumberInArray()
   {
      int numbers[] = new int[] { 32, 43, 53, 54, 32, 65, 63, 98, 43, 23 };
      int smallest = numbers[0];
      int largetst = numbers[0];
      for (int i = 1; i < numbers.length; i++)
      {
         if (numbers[i] > largetst)
         {
            largetst = numbers[i];
         }
         else if (numbers[i] < smallest)
         {
            smallest = numbers[i];
         }
      }
      System.out.println("Largest Number is : " + largetst);
      System.out.println("Smallest Number is : " + smallest);
      int[] number = { 88, 33, 55, 23, 64, 123 };
      int x = 3;
      String s = "3.0";
      float f = 3.0f;
      try
      {
         x = 10 / Integer.parseInt(s);
      } catch (IllegalArgumentException e)
      {
         f = 10 / Float.parseFloat(s);
      }
      System.out.println("Smallest Number is : " + x + " " + f);
      int largest = Integer.MIN_VALUE;
      int smalest = Integer.MAX_VALUE;
      for (int element : number)
      {
         if (element > largest)
         {
            largest = element;
         }
         if (element < smalest)
         {
            smalest = element;
         }
      }
      System.out.println("Largest number in array is : " + largest);
      System.out.println("Smallest number in array is : " + smalest);
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
   static Workbook wbook;
   static WritableWorkbook wwbCopy;
   static String ExecutedTestCasesSheet;
   static WritableSheet shSheet;
   static String sheetName;
   static String sheetNameCreated, excelPath, excelName;


   @Test
   public void testSplit()
   {
      String appName = "AAD";
      SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy h-mm a");
      SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
      excelName = appName + " Jenkins failures on " + sdf.format(new Date());
      String location = "C:/apps/JenkinsFailures/" + sdf1.format(new Date()).toString() + "/" + excelName + ".xls";
      File theDir = new File(location);
      excelPath = theDir.toString();
      try
      {
         wwbCopy = Workbook.createWorkbook(new File(excelPath));
         shSheet = wwbCopy.createSheet("AAD", 0);
      } catch (Exception e)
      {}
   }


   @Test
   public void Quick()
   {
      String sysEnv = System.getenv("env");
      log.info(" {}", sysEnv);
      System.getenv("ENV");
      String day = "15-07-2016";
      day.split("-");
   }


   @Test
   public void testNull()
   {
      String a = selectDropDown("", "", "");
      if (a == null || a.length() == 0)
      {
         log.info("Done");
      }
   }


   private String selectDropDown(String... locatores)
   {
      String element = null;
      for (String each : locatores)
      {
         if (each.equals("1"))
         {
            log.info("Selecting the Account Type as: {}", each);
            element = each;
            break;
         }
      }
      return element;
   }


   @Test
   public void testDate()
   {
      try
      {
         SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
         Date date1 = sdf.parse("2009-12-31");
         Date date2 = sdf.parse("2010-01-31");
         System.out.println(sdf.format(date1));
         System.out.println(sdf.format(date2));
         if (date1.compareTo(date2) > 0)
         {
            System.out.println("Date1 is after Date2");
         }
         else if (date1.compareTo(date2) < 0)
         {
            System.out.println("Date1 is before Date2");
         }
         else if (date1.compareTo(date2) == 0)
         {
            System.out.println("Date1 is equal to Date2");
         }
         else
         {
            System.out.println("How to get here?");
         }
      } catch (ParseException ex)
      {
         ex.printStackTrace();
      }
   }


   @Test
   public void testDates()
      throws ParseException
   {
      SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
      Date date1 = sdf.parse("Oct 12");
      Date date2 = sdf.parse("Sep 8");
      log.info(sdf.format(date1));
      log.info(sdf.format(date2));
      if (date1.compareTo(date2) == 0)
      {
         System.out.println("The emails in Inbox are arranged by new to old by dates " + date1 + " " + date2);
      }
      else if (date1.compareTo(date2) > 0)
      {
         System.out.println("The emails in Inbox are arranged by new to old by date " + date1 + " " + date2);
      }
      else if (date1.compareTo(date2) < 0)
      {
         System.out.println("The emails in Inbox are arranged by old to new by date " + date1 + " " + date2);
      }
   }


   @Test
   public void splitTest()
   {
      String each = "css=input[name='slackNotifyAborted']";
      each = each.replaceAll("css=input[name='", "").replaceAll("']", "");
      log.info(each);
      String a = "@org.testng.annotations.Test(expectedExceptions=[], skipFailedInvocations=false, dependsOnGroups=[], sequential=false, invocationTimeOut=0, groups=[hi], description=Nagaraju, retryAnalyzer=class java.lang.Class, alwaysRun=false, priority=0, enabled=true, successPercentage=100, timeOut=0, expectedExceptionsMessageRegExp=.*, ignoreMissingDependencies=false, singleThreaded=false, threadPoolSize=0, dataProviderClass=class java.lang.Object, dependsOnMethods=[], invocationCount=1, dataProvider=, parameters=[], suiteName=, testName=)";
      String b = a.toString().split("description=")[1].split(", retryAnalyzer=")[0];
      log.info(b);
   }


   @Test
   public void getMailSubject()
   {
      String a = WordUtils.capitalize("naga raju");
      log.info(a);
      int mailNum = 20;
      String page = String.valueOf(mailNum);
      if ((page.length() >= 2) && !(mailNum == 10))
      {
         int length = page.length() - 1;
         page = page.substring(0, length);
      }
      if (mailNum == 0)
      {
         mailNum = 1;
      }
   }


   @Test
   public void testSample()
   {
      String udid = getDeviceName("iOS");
      Process p = null;
      ArrayList<String> deviceName = new ArrayList<String>();
      BufferedReader in = null;
      String osVersion = null;
      try
      {
         p = new ProcessBuilder("/bin/bash", "-l", "-c", "instruments -s devices").start();
         try
         {
            p.waitFor();
         } catch (Exception e)
         {}
         in = new BufferedReader(new InputStreamReader(p.getInputStream()));
         while ((osVersion = in.readLine()) != null)
         {
            deviceName.add(osVersion.trim().toString());
         }
      } catch (IOException e)
      {}
      for (String s : deviceName)
      {
         if (s.contains(udid))
         {
            osVersion = s.replaceAll(udid, "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
            String[] list = osVersion.split(" \\(");
            deviceName = new ArrayList<String>();
            deviceName.add(list[0].trim());
            deviceName.add(list[1].replaceAll("\\)", "").trim());
            break;
         }
      }
      log.info(deviceName.get(0));
      log.info(deviceName.get(1));
   }


   public String getDeviceName(String osPlatform)
   {
      Process p = null;
      BufferedReader in = null;
      String device = null;
      if (osPlatform.equalsIgnoreCase("ios"))
      {
         try
         {
            p = new ProcessBuilder("/bin/bash", "-l", "-c", "idevice_id -l").start();
            try
            {
               p.waitFor();
            } catch (Exception e)
            {}
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            device = in.readLine().trim();
         } catch (IOException e)
         {}
      }
      else
      {
         try
         {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.startsWith("windows"))
            {
               p = new ProcessBuilder("cmd.exe", "/C", "adb devices").start();
            }
            else if (OS.startsWith("mac os"))
            {
               p = new ProcessBuilder("/bin/bash", "-l", "-c", "adb devices").start();
            }
            try
            {
               p.waitFor();
            } catch (Exception e)
            {}
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            in.readLine();
            in.readLine().replaceAll("device", "").replaceAll("\t", "").trim();
         } catch (IOException e)
         {}
      }
      return device;
   }


   @Test
   public void testsplitdata()
   {
      String element = "[[IOSDriver:  on MAC (24bd9cd1-eff8-4bd3-9424-61733a8f66f8)] -> xpath: //*[@value='ComPrice Username']]";
      String[] info = element.split("xpath: ");
      element = info[1];
      element = element.substring(0, info[1].trim().length() - 1);
      log.info(element);
   }


   @Test
   public void testFutureDate()
   {
      int i = 1;
      Date dNow = new Date();
      DateFormat newForm = new SimpleDateFormat("EEE d MMM");
      newForm.format(dNow);
      Calendar cal = Calendar.getInstance();
      cal.setTime(dNow);
      cal.add(Calendar.DATE, i);
      newForm.format(cal.getTime());
   }


   protected String typeRandomNumbe()
   {
      int value = ((int)(Math.random() * (60 - 01))) + 60;
      String a = String.valueOf(value);
      if (a.length() == 1)
      {
         a = "0" + a;
      }
      return a;
   }


   @Test
   public void tetRanNum()
   {
      ArrayList<String> time = new ArrayList<String>();
      int value = ((int)(Math.random() * (23 - 01)));
      String a = String.valueOf(value);
      if (a.length() == 1)
      {
         a = "0" + a;
      }
      time.add(a);
      value = value + 1;
      a = String.valueOf(value);
      if (a.length() == 1)
      {
         a = "0" + a;
      }
      time.add(a);
      value = ((int)(Math.random() * (60 - 01)));
      a = String.valueOf(value);
      if (a.length() == 1)
      {
         a = "0" + a;
      }
      time.add(a);
   }


   public int getRandomInteger(int maximum, int minimum)
   {
      return ((int)(Math.random() * (maximum - minimum))) + minimum;
   }


   @Test
   public void printDate()
   {
      SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
      System.out.println(sdf.format(new Date()));
      LocalDate date = LocalDate.now();
      LocalDate tomorrow = date.plusDays(1);
      System.out.println("Today date: " + date);
      System.out.println("Tommorow date: " + tomorrow);
   }


   @Test
   public void dateAddition()
   {
      Date dNow = new Date();
      DateFormat newForm = new SimpleDateFormat("dd MMMM yyyy");
      newForm.format(dNow);
      Calendar cal = Calendar.getInstance();
      cal.setTime(dNow);
      cal.add(Calendar.DATE, 1);
      String newDate = newForm.format(cal.getTime());
      log.info("" + newDate);
   }


   @Test
   private void getNewTime()
   {
      int length = 5 * 1000;
      String a = null;
      Random random = new Random();
      new StringBuilder(length);
      a = Integer.toString(random.nextInt(length));
      log.info("" + a);
   }


   @Test
   public static void startAppiumServer()
   {
      new RuntimeExec();
      RuntimeExec.startAppium("appium");
      // Process p = null;
      // try
      // {
      // p = new ProcessBuilder("/bin/bash", "appium", "--address", "127.0.0.1", "--port", "4723").start();
      // try
      // {
      // p.waitFor();
      // } catch (Exception e)
      // {
      // System.out.println(" " + e);
      // }
      // new BufferedReader(new InputStreamReader(p.getInputStream()));
      // } catch (IOException e)
      // {}
      // log.info("Appium server started");
   }


   /**
    * This method is used to stop the Appium service
    */
   public static void stopAppiumServer()
   {
      Process p = null;
      String pid = null;
      String[] pidValue = null;
      BufferedReader in = null;
      try
      {
         p = new ProcessBuilder("/bin/bash", "ps -A | grep appium").start();
         try
         {
            p.waitFor();
         } catch (Exception e)
         {}
         in = new BufferedReader(new InputStreamReader(p.getInputStream()));
         pid = in.readLine().trim();
      } catch (IOException e)
      {}
      pidValue = pid.split(" ");
      pid = pidValue[0];
      try
      {
         p = new ProcessBuilder("/bin/bash", "kill " + pid).start();
         try
         {
            p.waitFor();
         } catch (Exception e)
         {}
      } catch (IOException e)
      {}
      log.info("Appium server stopped");
   }
   private static Process process;
   private static String APPIUMSERVERSTART = "node /home/adminuser/Java_Projects/Appium/appium";


   @Test
   public static void startAppiumServers()
   {
      Runtime runtime = Runtime.getRuntime();
      try
      {
         process = runtime.exec(APPIUMSERVERSTART);
      } catch (IOException e)
      {
         e.printStackTrace();
      }
      try
      {
         Thread.sleep(5000);
      } catch (InterruptedException e)
      {
         e.printStackTrace();
      }
      if (process != null)
      {
         System.out.println("Appium server started");
      }
   }


   public static void stopAppiumServers()
   {
      if (process != null)
      {
         process.destroy();
      }
      System.out.println("Appium server stop");
   }


   public static void main(String args[])
      throws IOException, InterruptedException
   {
      startAppiumServer();
      stopAppiumServer();
   }


   @Test
   public void quit(ITestResult result)
   {
      // String className = result.getTestClass().getRealClass().getSimpleName();
      String className = result.getClass().getSimpleName();
      log.info(" " + className);
   }


   @Test
   public void setup(ITestContext context)
   {
      for (ITestNGMethod method : context.getAllTestMethods())
      {
         if (method.getRealClass() == this.getClass())
         {
            log.info(" " + method.getRealClass().getSimpleName());
         }
      }
   }


   @Test
   public void hasMap()
   {
      HashMap<String, String> hmap = new HashMap<String, String>();
      /* Adding elements to HashMap */
      hmap.put("12", "Chaitanya");
      hmap.put("2", "Rahul");
      hmap.put("7", "Singh");
      /* Display content using Iterator */
      Set set = hmap.entrySet();
      Iterator iterator = set.iterator();
      while (iterator.hasNext())
      {
         Map.Entry mentry = (Map.Entry)iterator.next();
         System.out.print("key is: " + mentry.getKey() + " & Value is: ");
         System.out.println(mentry.getValue());
      }
      /* Get values based on key */
      String var = hmap.get(2);
      System.out.println("Value at index 2 is: " + var);
      /* Remove values based on key */
      hmap.remove(3);
      System.out.println("Map key and values after removal:");
      Set set2 = hmap.entrySet();
      Iterator iterator2 = set2.iterator();
      while (iterator2.hasNext())
      {
         Map.Entry mentry2 = (Map.Entry)iterator2.next();
         System.out.print("Key is: " + mentry2.getKey() + " & Value is: ");
         System.out.println(mentry2.getValue());
      }
   }


   public ArrayList<ArrayList<String>> getDeviceDetails(String platform)
   {
      // HashMap<String, String> deviceDetail = new HashMap<String, String>();
      // HashMap<String, String> deviceDetails = new HashMap<String, String>();
      ArrayList<ArrayList<String>> deviceDetail = new ArrayList<ArrayList<String>>();
      ArrayList<String> deviceDetails = new ArrayList<String>();
      Process p = null;
      BufferedReader in = null;
      String osVersion = null;
      if (platform.equalsIgnoreCase("ios"))
      {
         String udid = getUDID("", false);
         try
         {
            p = new ProcessBuilder("/bin/bash", "-l", "-c", "instruments -s devices").start();
            try
            {
               p.waitFor();
            } catch (Exception e)
            {}
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((osVersion = in.readLine()) != null)
            {
               deviceDetails.add(osVersion.trim().toString());
            }
         } catch (IOException e)
         {}
         for (String s : deviceDetails)
         {
            if (s.contains(udid))
            {
               osVersion = s.replaceAll(udid, "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
               String[] list = osVersion.split(" \\(");
               deviceDetails = new ArrayList<String>();
               String dUdid, dName, dVersion = null;
               dName = list[0].trim();
               dVersion = list[1].replaceAll("\\)", "").trim();
               dUdid = udid.trim();
               deviceDetails.add(dName);
               deviceDetails.add(dVersion);
               deviceDetails.add(dUdid);
               deviceDetail.add(deviceDetails);
               break;
            }
         }
      }
      else
      {
         try
         {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.startsWith("windows"))
            {
               p = new ProcessBuilder("cmd.exe", "/C", "adb devices -l").start();
            }
            else if (OS.startsWith("mac os"))
            {
               p = new ProcessBuilder("/bin/bash", "-l", "-c", "adb devices -l").start();
            }
            try
            {
               p.waitFor();
            } catch (Exception e)
            {}
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((OS = in.readLine()) != null)
            {
               if ( !OS.startsWith("List of devices attached"))
               {
                  deviceDetails.add(OS.trim().toString());
               }
            }
            for (String s : deviceDetails)
            {
               if ( !s.equals(""))
               {
                  ArrayList<String> deviceDetails1 = new ArrayList<String>();
                  String[] details = null;
                  String id = null, osV = null;
                  details = s.split("model:");
                  details = details[1].split(" ");// device Name
                  deviceDetails1.add(details[0].trim());
                  details = s.split(" ");
                  id = details[0].trim();
                  osV = getUDID(id, true);
                  deviceDetails1.add(osV);// device osVersion
                  deviceDetails1.add(id);// device UDID
                  deviceDetail.add(deviceDetails1);
               }
            }
         } catch (IOException e)
         {}
      }
      return deviceDetail;
   }


   protected String getUDID(String udid, boolean android)
   {
      Process p = null;
      String deviceName = null;
      BufferedReader in = null;
      if ( !android)
      {
         try
         {
            p = new ProcessBuilder("/bin/bash", "-l", "-c", "idevice_id -l").start();
            try
            {
               p.waitFor();
            } catch (Exception e)
            {}
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            deviceName = in.readLine().trim();
         } catch (IOException e)
         {}
      }
      else
      {
         try
         {
            String OS = System.getProperty("os.name").toLowerCase();
            String cmd = "adb -s " + udid.trim() + " shell getprop ro.build.version.release";
            if (OS.startsWith("windows"))
            {
               p = new ProcessBuilder("cmd.exe", "/C", cmd).start();
            }
            else if (OS.startsWith("mac os"))
            {
               p = new ProcessBuilder("/bin/bash", "-l", "-c", cmd).start();
            }
            try
            {
               p.waitFor();
            } catch (Exception e)
            {}
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            deviceName = in.readLine().trim();
         } catch (IOException e)
         {}
      }
      return deviceName;
   }


   @Test
   public ArrayList<HashMap<String, String>> deviceDetails()
   {
      HashMap<String, String> devicesDetails = new HashMap<String, String>();
      ArrayList<String> deviceDetails = new ArrayList<String>();
      ArrayList<HashMap<String, String>> listOfDevices = new ArrayList<HashMap<String, String>>();
      String platform = "android", udid = null;
      Process p = null;
      BufferedReader in = null;
      String osVersion = null;
      if (platform.equalsIgnoreCase("ios"))
      {
         try
         {
            p = new ProcessBuilder("/bin/bash", "-l", "-c", "idevice_id -l").start();
            try
            {
               p.waitFor();
            } catch (Exception e)
            {}
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            udid = in.readLine().trim();
         } catch (IOException e)
         {}
         try
         {
            p = new ProcessBuilder("/bin/bash", "-l", "-c", "instruments -s devices").start();
            try
            {
               p.waitFor();
            } catch (Exception e)
            {}
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((osVersion = in.readLine()) != null)
            {
               deviceDetails.add(osVersion.trim().toString());
            }
         } catch (IOException e)
         {}
         for (String s : deviceDetails)
         {
            if (s.contains(udid))
            {
               osVersion = s.replaceAll(udid, "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
               String[] list = osVersion.split(" \\(");
               String dUdid, dName, dVersion = null;
               dName = list[0].trim();
               dVersion = list[1].replaceAll("\\)", "").trim();
               dUdid = udid.trim();
               devicesDetails.put("deviceName", dName);
               devicesDetails.put("platformVersion", dVersion);
               devicesDetails.put("udid", dUdid);
               listOfDevices.add(devicesDetails);
               break;
            }
         }
      }
      else if (platform.equalsIgnoreCase("android"))
      {
         try
         {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.startsWith("windows"))
            {
               p = new ProcessBuilder("cmd.exe", "/C", "adb devices -l").start();
            }
            else if (OS.startsWith("mac os"))
            {
               p = new ProcessBuilder("/bin/bash", "-l", "-c", "adb devices -l").start();
            }
            try
            {
               p.waitFor();
            } catch (Exception e)
            {}
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((OS = in.readLine()) != null)
            {
               if ( !OS.startsWith("List of devices attached"))
               {
                  deviceDetails.add(OS.trim().toString());
               }
            }
            for (String s : deviceDetails)
            {
               if ( !s.equals(""))
               {
                  String[] details = null;
                  String osV = null;
                  details = s.split("model:");
                  details = details[1].split(" ");
                  devicesDetails = new HashMap<String, String>();
                  devicesDetails.put("deviceName", details[0].trim());// deviceName
                  details = s.split(" ");
                  udid = details[0].trim();
                  try
                  {
                     OS = System.getProperty("os.name").toLowerCase();
                     String cmd = "adb -s " + udid.trim() + " shell getprop ro.build.version.release";
                     if (OS.startsWith("windows"))
                     {
                        p = new ProcessBuilder("cmd.exe", "/C", cmd).start();
                     }
                     else if (OS.startsWith("mac os"))
                     {
                        p = new ProcessBuilder("/bin/bash", "-l", "-c", cmd).start();
                     }
                     try
                     {
                        p.waitFor();
                     } catch (Exception e)
                     {}
                     in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                     osV = in.readLine().trim();
                  } catch (IOException e)
                  {}
                  devicesDetails.put("platformVersion", osV);// platformVersion
                  devicesDetails.put("udid", udid);// udid
                  listOfDevices.add(devicesDetails);
               }
            }
         } catch (IOException e)
         {}
      }
      return listOfDevices;
   }
}