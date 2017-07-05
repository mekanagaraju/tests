package test.screen;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import test.tests.SingleSeleniumSolution;

public class JenkinsScreen
{
   protected Log log = LogFactory.getLog(this.getClass().getName());
   SingleSeleniumSolution webController;
   static Workbook wbook;
   static WritableWorkbook wwbCopy;
   static String ExecutedTestCasesSheet;
   static WritableSheet shSheet;
   static String sheetName;
   static String currentBrowser;
   static String sheetNameCreated, excelPath, excelName;
   boolean flag = false;
   File file = null;
   /*** Screen Objects ***/
   public static final String USERNAME = "css=input[name='j_username']";
   public static final String PASSWORD = "css=input[name='j_password']";
   public static final String LOGIN_LINK = "//b[contains(text(), 'log in')]";
   public static final String LOGOUT_LINK = "//b[contains(text(), 'log out')]";
   public static final String LOGIN_BUTTON = "//button[contains(text(), 'log in')]";
   public static final String LOGIN_ERROR = "//div[@id='main-panel']/div[1]";
   public static final String CONSOLE_OUTPUT = "//a[contains(text(), 'Console Output')]";
   public static final String FULL_LOG = "//a[contains(text(), 'Full Log')]";
   public static final String SKIP = "//h1[contains(text(), 'Skipped')]";
   public static final String TABS = "//div[@class='tabBar']/div";
   public static final String MAINPANNEL = "//div[@id='main-panel']/pre";
   public static final String MAINPANNEL1 = "//div[@id='main-panel']/pre[1]";
   public static final String MAINPANNEL2 = "//div[@id='main-panel']/pre[2]";
   public static final String MAINPANNEL3 = "//div[@id='main-panel']/pre[3]";
   protected static final String AAD = "_01 AADW8";
   protected static final String ACRMMOBILE = "_02 ACRM Mobile";
   protected static final String C360DESKTOP = "_03 C360 Desktop";
   protected static final String MYACCOUNT = "_04 MyAccount";
   protected static final String MYSERVICES = "_05 MyServices";
   protected static final String OBP = "_06 OBP";
   protected static final String PMT = "_07 PMT";
   protected static final String IVR = "_08 IVR";
   protected static final String REGISTRATION = "_09 Registration";
   // protected static final String JMETERPERFORMANCE = "_10 JMeter Performance";
   protected static final String EMS = "_11EMS";
   protected static final String OKM = "_12 OKM";
   protected static final String MYACCOUNTREDESIGN = "_13MyAccount Redesign";
   protected static final String ITK = "_14 ITK";
   protected static final String SBT = "_15SBTool";
   int tableNo = 1;
   ArrayList<ArrayList<String>> failures = new ArrayList<ArrayList<String>>();
   ArrayList<ArrayList<String>> atfFailures = new ArrayList<ArrayList<String>>();


   public JenkinsScreen(WebDriver webDriver)
   {
      this.webController = new SingleSeleniumSolution(webDriver);
   }


   private ArrayList<String> appList()
   {
      ArrayList<String> apps = new ArrayList<String>();
      apps.add(AAD);
      apps.add(ACRMMOBILE);
      apps.add(C360DESKTOP);
      apps.add(MYACCOUNT);
      apps.add(MYSERVICES);
      apps.add(OBP);
      apps.add(PMT);
      apps.add(REGISTRATION);
      apps.add(EMS);
      apps.add(OKM);
      apps.add(MYACCOUNTREDESIGN);
      apps.add(ITK);
      apps.add(SBT);
      return apps;
   }


   public void pullApplicationsReports()
   {
      ArrayList<String> apps = appList();
      for (String application : apps)
      {
         String applications = "//div[@class='tabBar']//a[contains(text(), '" + application + "')]";
         scrollUp();
         webController.waitForElementVisible(applications, 30);
         webController.click(applications);
         log.info("Navigated to " + application + " Application");
         getIndividualReport(application);
         String appName = null;
         String[] appNames = application.split(" ", 2);
         if (appNames.length <= 1)
         {
            appName = application.substring(3);
         }
         else
         {
            appName = appNames[1].trim();
         }
         log.info(appName + " application failures count: " + failures.size());
         if (failures.size() > 0)
         {
            log.info("Captured the Jenkins failures for " + appName + " application, count of failures: " + failures.size());
            setExcelSheet(appName);
            setDataToExcel(0);
            closeFiles();
            // String url = webController.getCurrentUrl();
            // sendEmail(appName);
            // webController.get(url);
         }
      }
   }


   public void pullApplicationReport(String application)
   {
      String applications = "//div[@class='tabBar']//a[contains(text(), '" + application + "')]";
      webController.waitForElementVisible(applications, 30);
      webController.click(applications);
      log.info("Navigated to " + application + " Application");
      getIndividualReport(application);
      String appName = null;
      String[] appNames = application.split(" ", 2);
      if (appNames.length <= 1)
      {
         appName = application.substring(3);
      }
      else
      {
         appName = appNames[1].trim();
      }
      log.info(appName + " application failures count: " + failures.size());
      if (failures.size() > 0)
      {
         log.info("Collecting the ATF failures");
         ATFReporterScreen js = new ATFReporterScreen(webController.getWebDriver());
         atfFailures = js.getJiraID(appName);
         log.info("Captured the Jenkins failures for " + appName + " application, count of failures: " + failures.size());
         setExcelSheet(appName);
         setDataToExcel(0);
         closeFiles();
         webController.goBack();
         // sendEmail(appName);
      }
   }


   private void getIndividualReport(String application)
   {
      if (application.equals(PMT))
      {
         getRegReport(application);
      }
      else if (application.equals(REGISTRATION))
      {
         getRegReport(application);
      }
      else
      {
         getDefaultReport(application);
      }
      createFolder();
   }


   private void createFolder()
   {
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
      String directoryName = sdf.format(new Date());
      File theDir = new File("C:/apps/JenkinsFailures");
      if ( !theDir.exists())
      {
         try
         {
            log.info("Creating directory: JenkinsFailures");
            theDir.mkdir();
         } catch (SecurityException se)
         {}
      }
      theDir = new File("C:/apps/JenkinsFailures/" + directoryName);
      if ( !theDir.exists())
      {
         try
         {
            log.info("Creating directory: " + directoryName);
            theDir.mkdir();
         } catch (SecurityException se)
         {}
      }
   }


   private void getDefaultReport(String application)
   {
      String mainTabCountE = "//div[@class='tabBar']/div";
      webController.waitForElementVisible(mainTabCountE, 20);
      String browserName = webController.getText(mainTabCountE).trim();
      webController.waitForElementVisible(TABS, 20);
      int tabCount = webController.getElementsCount(TABS);
      for (int t = 1; t <= tabCount; t++)
      {
         mainTabCountE = "//div[@class='tabBar']/div[" + t + "]/a";
         scrollUp();
         webController.waitForElementVisible(mainTabCountE, 20);
         browserName = webController.getText(mainTabCountE).trim();
         if (( !browserName.contains("Preview Demo")) && ( !browserName.contains("Test Danny")))
         {
            webController.click(mainTabCountE);
            log.info(browserName + " TAB");
            currentBrowser = getCurrentRunningBrowser(browserName);
            grabJobData();
         }
      }
   }


   private void getRegReport(String application)
   {
      String mainTabCountE = "//div[@class='tabBar']/div";
      webController.waitForElementVisible(mainTabCountE, 20);
      int mainTabCount = webController.getElementsCount(mainTabCountE);
      for (int m = 1; m <= mainTabCount; m++)
      {
         mainTabCountE = "//div[@class='tabBar']/div[" + m + "]/a";
         scrollUp();
         String browserName = webController.getText(mainTabCountE).trim();
         webController.waitForElementVisible(mainTabCountE, 20);
         webController.click(mainTabCountE);
         if ((browserName.startsWith("All ")) || (browserName.endsWith("Jobs")))
         {
            m = 2;
            mainTabCountE = "//div[@class='tabBar']/div[" + m + "]/a";
            browserName = webController.getText(mainTabCountE).trim();
            currentBrowser = getCurrentRunningBrowser(browserName);
            webController.click(mainTabCountE);
         }
         log.info(" " + browserName);
         int tabCount = webController.getElementsCount("//div[@class='tabBar']/div");
         for (int t = 1; t <= tabCount; t++)
         {
            scrollUp();
            mainTabCountE = "//div[@class='tabBar']/div[" + t + "]/a";
            webController.click(mainTabCountE);
            browserName = webController.getText(mainTabCountE).trim();
            log.info(browserName + " TAB");
            grabJobData();
         }
         navigateToNextTab(application);
      }
   }


   private void navigateToNextTab(String application)
   {
      String url = "cyrano.corp.mystrotv.com:8080";
      webController.getWebDriver().get(url);
      String applications = "//div[@class='tabBar']//a[contains(text(), '" + application + "')]";
      webController.waitForElementVisible(applications, 30);
      webController.click(applications);
   }


   protected void grabJobData()
   {
      String epicE = "//table[@id='projectstatus']/tbody/tr";
      webController.waitForElementVisible(epicE, 20);
      int cpicsCount = webController.getElementsCount(epicE);
      for (int e = 2; e <= cpicsCount; e++)
      {
         String epicsE = epicE + "[";
         String green = epicsE + e + "][@class=' job-status-blue']";
         String yellow = epicsE + e + "][@class=' job-status-yellow']";
         String yellowRunning = epicsE + e + "][@class=' job-status-yellow-anime']";
         String aborted = epicsE + e + "][@class=' job-status-aborted']";
         String abortedRunning = epicsE + e + "][@class=' job-status-aborted-anime']";
         String red = epicsE + e + "][@class=' job-status-red']";
         if (webController.isElementVisible(green))
         {
            String jobName = webController.getAttribute(green, "id");
            log.info(getJobName(jobName) + " : Green");
         }
         else if (webController.isElementVisible(yellow))
         {
            String jobName = webController.getAttribute(yellow, "id");
            log.info(getJobName(jobName) + " : Yellow");
            String yJobE = yellow + "/td[3]/a";
            webController.waitForElementVisible(yJobE, 20);
            webController.click(yJobE);
            grabResult(getJobName(jobName));
         }
         else if (webController.isElementVisible(yellowRunning))
         {
            String jobName = webController.getAttribute(yellowRunning, "id");
            log.info(getJobName(jobName) + " : Yellow");
            String yJobE = yellowRunning + "/td[3]/a";
            webController.waitForElementVisible(yJobE, 20);
            webController.click(yJobE);
            grabResult(getJobName(jobName));
         }
         else if (webController.isElementVisible(aborted))
         {
            String jobName = webController.getAttribute(aborted, "id");
            log.info(getJobName(jobName) + " : Aborted");
            String aJobE = aborted + "/td[3]/a";
            webController.waitForElementVisible(aJobE, 20);
            webController.click(aJobE);
            grabAbortedResult(getJobName(jobName));
         }
         else if (webController.isElementVisible(abortedRunning))
         {
            String jobName = webController.getAttribute(abortedRunning, "id");
            log.info(getJobName(jobName) + " : Aborted");
            String aJobE = abortedRunning + "/td[3]/a";
            webController.waitForElementVisible(aJobE, 20);
            webController.click(aJobE);
            grabAbortedResult(getJobName(jobName));
         }
         else if (webController.isElementVisible(red))
         {
            String jobName = webController.getAttribute(red, "id");
            log.info(getJobName(jobName) + " : Red");
            String rJobE = red + "/td[3]/a";
            webController.waitForElementVisible(rJobE, 20);
            webController.click(rJobE);
            grabRedJobResult(getJobName(jobName));
         }
      }
   }


   private void grabRedJobResult(String jobName)
   {
      String build = null;
      ArrayList<String> text = new ArrayList<String>();
      text.add(jobName);// Jenkins Job Name
      text.add("Build Failed");// Status
      String biuldE = null, biuldE1 = null;
      for (int i = 2; i <= 6; i++)
      {
         biuldE = "//div[@id='buildHistory']/div[2]/table//tr[" + i + "]/td/div[2]/table";
         biuldE1 = "//div[@id='buildHistory']/div[2]/table//tr[" + i + "]/td/div[1]/div";
         if (webController.isElementPresent(biuldE))
         {
            biuldE1 = "//div[@id='buildHistory']/div[2]/table//tr[" + (i + 1) + "]/td/div[1]/div";
            biuldE = biuldE1;
            break;
         }
         else if (webController.isElementPresent(biuldE1))
         {
            biuldE = biuldE1;
            break;
         }
      }
      webController.click(biuldE);
      String count = "//div[@class='top-sticker-inner']/ul[1]/li";
      int buildE = webController.getElementsCount(count) - 1;
      build = webController.getText(count + "[" + buildE + "]").trim();
      String buildNo = build.replace("#", "");// build number
      if (webController.isElementVisible(FULL_LOG))
      {
         webController.click(FULL_LOG);
         flag = true;
      }
      webController.waitForElementVisible(MAINPANNEL, 20);
      String body = webController.getText(MAINPANNEL);
      String[] nameMachine = null;
      if (body.contains("ERROR: "))
      {
         nameMachine = body.split("ERROR: ");
         String errorMessage = nameMachine[1];
         nameMachine = nameMachine[1].split("\\n");
         errorMessage = "ERROR: " + nameMachine[0].trim();
         if (errorMessage.contains(""))
         {
            text.add("ERROR: There is not enough space on the disk.");// Test Script Name
            text.add("ERROR: There is not enough space on the disk.");// Test Description
         }
         else
         {
            text.add(errorMessage);// Test Script Name
            text.add(errorMessage);// Test Description
         }
      }
      else
      {
         text.add("Please check and update the reason for build failure");// Test Script Name
         text.add("Please check and update the reason for build failure");// Test Description
      }
      String machineName = body;
      nameMachine = machineName.split("Building remotely on");
      machineName = nameMachine[1];
      nameMachine = machineName.split("in workspace");
      machineName = nameMachine[0].trim();
      if (body.contains("There is not enough space on the disk."))
      {
         String a = "In " + machineName + " machine there is not enough space on the disk. Please delete the unwanted data from " + machineName + " machine.";
         text.add(a);// Reason for Failure
      }
      else
      {
         text.add("Please check and update the reason for build failure");// Reason for Failure
      }
      if (body.contains(">channel started"))
      {
         String[] error = body.split(">channel started");
         body = error[1];
         error = body.split("\n");
         body = error[1];
         error = body.split("-Dhardware_info=123");
         body = error[1].replaceAll(",", "").trim();
         error = body.split(";");
         body = error[0];
         if (body.contains("Chrome:"))
         {
            body = "Chrome";
         }
         else if (body.contains("Firefox:"))
         {
            body = "Firefox";
         }
         else
         {
            body = "Internetexplorer";
         }
      }
      else
      {
         body = currentBrowser;
      }
      text.add(body);// Browser Type
      text.add(buildNo);// Build No
      text.add(machineName);// Machine Name
      failures.add(text);
      log.info(text);
      text = new ArrayList<String>();
      if (flag)
      {
         webController.goBack();
      }
      flag = false;
      webController.goBack();
      webController.goBack();
   }


   private void grabAbortedResult(String jobName)
   {
      String build = null;
      ArrayList<String> text = new ArrayList<String>();
      String biuldE = null, biuldE1 = null;
      for (int i = 2; i <= 6; i++)
      {
         biuldE = "//div[@id='buildHistory']/div[2]/table//tr[" + i + "]/td/div[2]/table";
         biuldE1 = "//div[@id='buildHistory']/div[2]/table//tr[" + i + "]/td/div[1]/a";
         if (webController.isElementPresent(biuldE))
         {
            biuldE1 = "//div[@id='buildHistory']/div[2]/table//tr[" + (i + 1) + "]/td/div[1]/a";
            biuldE = biuldE1;
            break;
         }
         else if (webController.isElementPresent(biuldE1))
         {
            biuldE = biuldE1;
            break;
         }
      }
      webController.click(biuldE);
      String count = "//div[@class='top-sticker-inner']/ul[1]/li";
      int buildE = webController.getElementsCount(count) - 1;
      build = webController.getText(count + "[" + buildE + "]").trim();
      build = build.replace("#", "");// build number
      webController.click(CONSOLE_OUTPUT);
      if (webController.isElementVisible(FULL_LOG))
      {
         webController.click(FULL_LOG);
         flag = true;
      }
      webController.waitForElementVisible(MAINPANNEL, 20);
      String body = webController.getText(MAINPANNEL);
      String machineName = body;
      String[] nameMachine = machineName.split("Building remotely on");
      machineName = nameMachine[1];
      nameMachine = machineName.split("in workspace");
      machineName = nameMachine[0].trim();
      // log.info(machineName);
      if (body.contains("Aborted by"))
      {
         String bodyE = body;
         String[] error = body.split("Aborted by");
         body = error[1];
         error = body.split("\n");
         body = error[0].trim();
         text.add(jobName);
         text.add("Build Aborted");
         text.add("");
         text.add("");
         text.add("Build Aborted by " + body);
         error = bodyE.split(">channel started");
         body = error[1];
         error = body.split("\n");
         body = error[1];
         error = body.split("-Dhardware_info=123");
         body = error[1].replaceAll(",", "").trim();
         error = body.split(";");
         body = error[0];
         if (body.contains("Chrome:"))
         {
            body = "Chrome";
         }
         else if (body.contains("Firefox:"))
         {
            body = "Firefox";
         }
         else
         {
            body = "Internetexplorer";
         }
         text.add(body);
         text.add(build);
         text.add(machineName);
      }
      else
      {
         String bodyE = body;
         text.add(jobName);
         text.add("Build Aborted");
         if (body.contains("Build timed out "))
         {
            String[] error = body.split("Build timed out");
            body = error[1].trim();
            error = body.split("\n");
            body = error[0].trim();
            String texts = webController.getText("//*[@id='main-panel']/pre").trim();
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
                  text.add(standardOutputs2[0]);// Test script Name
                  String temp = standardOutputs1[1];
                  String[] finals = temp.split("\\n");
                  text.add(finals[0].trim());// Description
                  textE1 = textE[textE.length - 1];
                  standardOutputs1 = textE1.split(" - Another Build: [{] Browser:", 2);
                  temp = standardOutputs1[1];
                  finals = temp.split("}");
                  temp = finals[0].trim();
                  text.add("Build timed out " + body);// Reason for failure
                  body = temp;// Browser Type
               }
               else
               {
                  text.add("Build timed out " + body);
                  text.add("Build timed out " + body);
                  text.add("Build timed out " + body);
               }
            }
            else
            {
               text.add("Build timed out " + body);
               text.add("Build timed out " + body);
               text.add("Build timed out " + body);
            }
         }
         else
         {
            String[] error = body.split("ERROR: ");
            body = error[1].trim();
            error = body.split("\n");
            body = ("ERROR: " + error[0] + ". " + error[1]);
            text.add("");
            text.add("");
            text.add(body);
            error = bodyE.split(">channel started");
            body = error[1];
            error = body.split("\n");
            body = error[1];
            error = body.split("-Dhardware_info=123");
            body = error[1].replaceAll(",", "").trim();
            error = body.split(";");
            body = error[0];
            if (body.contains("Chrome:"))
            {
               body = "Chrome";
            }
            else if (body.contains("Firefox:"))
            {
               body = "Firefox";
            }
            else
            {
               body = "Internetexplorer";
            }
         }
         text.add(body);
         text.add(build);
         text.add(machineName);
      }
      if (flag)
      {
         webController.goBack();
      }
      flag = false;
      log.info(text);
      failures.add(text);
      webController.goBack();
      webController.goBack();
      webController.goBack();
   }


   private void grabResult(String jobName)
   {
      String machineName = null, build = null;
      String failuresE = "//table[1]//a[contains(text(), 'Latest Test Result')]";
      if ( !webController.isElementPresentWithWait(failuresE))
      {
         failuresE = "//table//a[contains(text(), 'Latest Test Result')]";
         webController.waitForElementVisible(failuresE);
      }
      int skipEpic = 0;
      webController.click(failuresE);
      String failuresCountE = "//div[@id='main-panel']/table[2]//tr";
      webController.waitForElementVisible(failuresCountE);
      int failuresCount = webController.getElementsCount(failuresCountE);
      String backUpBrowser = null;
      failure: for (int f = 2; f <= failuresCount; f++)
      {
         if (f == 2)
         {
            boolean flag = false;
            webController.waitForElementVisible(CONSOLE_OUTPUT, 20);
            webController.click(CONSOLE_OUTPUT);
            if (webController.isElementVisible(FULL_LOG))
            {
               webController.click(FULL_LOG);
               flag = true;
            }
            webController.waitForElementVisible(MAINPANNEL, 20);
            machineName = webController.getText(MAINPANNEL);
            String[] nameMachine = machineName.split("Building remotely on");
            machineName = nameMachine[1];
            nameMachine = machineName.split("in workspace");
            machineName = nameMachine[0].trim(); // machine name
            webController.goBack();
            if (flag)
            {
               webController.goBack();
            }
            webController.delay(2);
            String count = "//div[@class='top-sticker-inner']/ul[1]/li";
            int buildE = (webController.getElementsCount(count)) - 3;
            build = webController.getText(count + "[" + buildE + "]").trim();
            build = build.replace("#", "");// build number
         }
         webController.waitForElementVisible("//table[2]//tr[" + f + "]/td[1]/a[3]", 20);
         String testScriptNameE = webController.getText("//table[2]//tr[" + f + "]/td[1]/a[3]");
         String[] testScriptName = testScriptNameE.split("[.]");
         testScriptNameE = testScriptName[testScriptName.length - 1].trim();
         String testScriptE = "//div[@id='main-panel']/table[2]//tr[" + f + "]//a[3]";
         webController.click(testScriptE);
         webController.waitForElementVisible("//div[@id='main-panel']//span", 20);
         ArrayList<String> text = new ArrayList<String>();
         String testName = webController.getText("//div[@id='main-panel']//span");
         String testNames[] = testName.split("[.]");
         testName = testNames[testNames.length - 1].trim();
         text.add(jobName);// Job Name
         if (testScriptNameE.equalsIgnoreCase("afterMethodBaseCase"))
         {
            skipEpic++;
            if (skipEpic == 4)
            {
               text.add("Test cases skipped");// Error Type
               text.add("No of test cases skipped: " + (failuresCount - 1));// Test Case
               text.add("Please check the Application manually");// Description
               text.add("Please check the Application manually");// Reason for failure
               text.add(backUpBrowser);// Browser
               log.info(text);
               failures.add(text);
               webController.goBack();
               break failure;
            }
         }
         text.add("ERROR");// Error Type
         text.add(testName);// Test Case
         String standardOutput = null;
         boolean check = true;
         if ( !webController.isElementVisible(MAINPANNEL3))
         {
            if ( !webController.isElementVisible(MAINPANNEL2))
            {
               if (webController.isElementVisible(SKIP))
               {
                  standardOutput = "SKIP";
                  check = false;
               }
               else
               {
                  webController.waitForElementVisible(MAINPANNEL1, 20);
                  standardOutput = webController.getText(MAINPANNEL1).trim();
               }
            }
            else
            {
               webController.waitForElementVisible(MAINPANNEL2, 20);
               standardOutput = webController.getText(MAINPANNEL2).trim();
            }
         }
         else
         {
            webController.waitForElementVisible(MAINPANNEL3, 20);
            standardOutput = webController.getText(MAINPANNEL3).trim();
         }
         if ( !check)
         {
            text.add("Please check the Jenkins job manually for the reason why test scripts are getting skipped");// Description
            webController.waitForElementVisible(CONSOLE_OUTPUT, 20);
            webController.click(CONSOLE_OUTPUT);
            if (webController.isElementVisible(FULL_LOG))
            {
               webController.click(FULL_LOG);
            }
            String body = webController.getText(MAINPANNEL1).trim();
            String[] error = body.split("-Dhardware_info=123");
            body = error[1].replaceAll(",", "").trim();
            error = body.split(";");
            body = error[0];
            if (body.contains("Chrome:"))
            {
               body = "Chrome";
            }
            else if (body.contains("Firefox:"))
            {
               body = "Firefox";
            }
            else
            {
               body = "Internetexplorer";
            }
            text.add("From timeout: Timed out");// Reason for failure
            text.add(body);// Browser
            webController.goBack();
            webController.goBack();
            webController.goBack();
            check = true;
         }
         else
         {
            if ( !standardOutput.contains(" - Description:"))
            {
               if ( !webController.isElementVisible(MAINPANNEL2))
               {
                  webController.waitForElementVisible(MAINPANNEL1, 20);
                  standardOutput = webController.getText(MAINPANNEL1).trim();
               }
               else
               {
                  webController.waitForElementVisible(MAINPANNEL2, 20);
                  standardOutput = webController.getText(MAINPANNEL2).trim();
               }
            }
            if (standardOutput.contains(" - Description:"))
            {
               String[] standardOutputs = standardOutput.split(" - Description:", 2);
               String temp = standardOutputs[1];
               String[] finals = temp.split("\\n");
               text.add(finals[0].trim());// Description
               String errorName = webController.getText(MAINPANNEL1).trim();
               errorName = trimReasonForFailure(errorName);
               text.add(errorName);// Reason for failure
               standardOutputs = standardOutput.split(" - Another Build: [{] Browser:", 2);
               temp = standardOutputs[1];
               finals = temp.split("}");
               temp = finals[0].trim();
               temp.replaceAll("\n", " ").replaceAll("\\r\\n|\\r|\\n", " ").replaceAll("\\r\\n", " ").replaceAll("\\r", " ").replaceAll("\\n", " ").trim();
               text.add(temp);// Browser
               backUpBrowser = temp;
            }
            else
            {
               standardOutput = webController.getText(MAINPANNEL1).trim();
               text.add("Please check the Jenkins job manually for the reason why test scripts are getting skipped");// Description
               String[] textB = standardOutput.split("\n");
               String error = textB[0];
               error = trimReasonForFailure(error);
               text.add(error);// Reason for failure
               if (standardOutput.contains("browserName="))
               {
                  String browser = textB[4];
                  textB = browser.split("browserName=");
                  error = textB[1];
                  textB = error.split(", ");
                  error = textB[0];
                  if (error.toLowerCase().contains("chrome"))
                  {
                     error = "Chrome";
                  }
                  else if (error.toLowerCase().contains("firefox"))
                  {
                     error = "Firefox";
                  }
                  else
                  {
                     error = "Internetexplorer";
                  }
                  text.add(error);// Browser
                  backUpBrowser = error;
               }
               else
               {
                  text.add(currentBrowser);// Browser
               }
            }
         }
         text.add(build);// build number
         text.add(machineName); // machine name
         log.info(text);
         failures.add(text);
         webController.goBack();
      }
      webController.goBack();
      webController.goBack();
   }


   private String trimReasonForFailure(String error)
   {
      String exactError = null;
      if (error.contains("java.lang.NullPointerException: null"))
      {
         if (error.contains("screens"))
         {
            String[] finalText = error.split("screens.");
            exactError = "java.lang.NullPointerException: null " + finalText[1].trim();
            if (exactError.contains(")"))
            {
               finalText = exactError.split("[)]");
               exactError = finalText[0].trim() + ")";
               log.info(exactError);
            }
         }
         else
         {
            exactError = error;
         }
      }
      else if (error.contains("Other element would receive the click"))
      {
         String[] finalText = error.split("Other element would receive the click");
         exactError = finalText[0].trim() + " Other element would receive the click";
      }
      else if (error.contains("Timed out after "))
      {
         String[] finalText = error.split("Build info");
         exactError = finalText[0].trim();
      }
      else if (error.contains(" (Session info:"))
      {
         String[] finalText = error.split("[(]Session info:");
         if ( !finalText[0].trim().isEmpty())
         {
            exactError = finalText[0].replaceAll("\n", "").trim();
         }
         else
         {
            exactError = error;
         }
      }
      else if (error.contains("Build info:"))
      {
         String[] finalText = error.split("Build info");
         if ( !finalText[0].trim().isEmpty())
         {
            exactError = finalText[0].replaceAll("\n", "").trim();
         }
         else
         {
            exactError = error;
         }
      }
      else
      {
         exactError = error;
      }
      return exactError;
   }


   private String getJobName(String jobName)
   {
      return jobName.replace("job_", "").trim();
   }


   public void creatExcelSheet(String appName, int sheetNo)
   {
      sheetName = appName;
      try
      {
         shSheet = wwbCopy.createSheet(sheetName, sheetNo);
      } catch (Exception e)
      {}
   }


   public void setExcelSheet(String appName)
   {
      sheetName = appName;
      SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy h-mm a");
      SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
      excelName = appName + " Jenkins failures on " + sdf.format(new Date());
      String location = "C:/apps/JenkinsFailures/" + sdf1.format(new Date()).toString() + "/" + excelName + ".xls";
      File theDir = new File(location);
      excelPath = theDir.toString();
      try
      {
         wwbCopy = Workbook.createWorkbook(new File(excelPath));
         shSheet = wwbCopy.createSheet(sheetName, 0);
      } catch (Exception e)
      {}
   }


   private void setValueIntoCell(int iColumnNumber, int iRowNumber, String strData, WritableCellFormat format, int sheetNo)
   {
      WritableSheet wshTemp = wwbCopy.getSheet(sheetNo);
      Label labTemp = new Label(iColumnNumber, iRowNumber, strData, format);
      try
      {
         wshTemp.addCell(labTemp);
      } catch (Exception e)
      {}
   }


   public void wiriteFile()
   {
      try
      {
         wwbCopy.write();
      } catch (Exception e)
      {}
   }


   public void closeFiles()
   {
      try
      {
         wwbCopy.write();
         wwbCopy.close();
      } catch (Exception e)
      {}
   }


   private WritableCellFormat getApplicationErrorCellFormat()
   {
      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
      WritableCellFormat format = new WritableCellFormat(cellFont);
      try
      {
         format.setAlignment(Alignment.LEFT);
         format.setBackground(Colour.RED);
         format.setBorder(Border.ALL, BorderLineStyle.THIN);
      } catch (WriteException e)
      {}
      return format;
   }


   private WritableCellFormat getSkipErrorCellFormat()
   {
      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BROWN);
      WritableCellFormat format = new WritableCellFormat(cellFont);
      try
      {
         format.setAlignment(Alignment.LEFT);
         format.setBackground(Colour.WHITE);
         format.setBorder(Border.ALL, BorderLineStyle.THIN);
      } catch (WriteException e)
      {}
      return format;
   }


   private WritableCellFormat getErrorCellFormat()
   {
      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
      WritableCellFormat format = new WritableCellFormat(cellFont);
      try
      {
         format.setAlignment(Alignment.LEFT);
         format.setBackground(Colour.WHITE);
         format.setBorder(Border.ALL, BorderLineStyle.THIN);
      } catch (WriteException e)
      {}
      return format;
   }


   private WritableCellFormat getCellFormat()
   {
      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.AUTOMATIC);
      WritableCellFormat format = new WritableCellFormat(cellFont);
      try
      {
         format.setAlignment(Alignment.LEFT);
         format.setBackground(Colour.WHITE);
         format.setBorder(Border.ALL, BorderLineStyle.THIN);
      } catch (WriteException e)
      {}
      return format;
   }


   private WritableCellFormat getHedderFormat()
   {
      WritableFont hedderFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
      WritableCellFormat format = new WritableCellFormat(hedderFont);
      try
      {
         format.setAlignment(Alignment.LEFT);
         format.setBackground(Colour.SEA_GREEN);
         format.setBorder(Border.ALL, BorderLineStyle.THIN);
      } catch (WriteException e)
      {}
      return format;
   }


   public void setDataToExcel(int sheetNo)
   {
      WritableCellFormat hedder = getHedderFormat();
      WritableCellFormat white = getCellFormat();
      WritableCellFormat red = getErrorCellFormat();
      WritableCellFormat skip = getSkipErrorCellFormat();
      WritableCellFormat appError = getApplicationErrorCellFormat();
      setValueIntoCell(0, 0, "||SNo", hedder, sheetNo);
      setValueIntoCell(1, 0, "||Jenkins Job Name", hedder, sheetNo);
      setValueIntoCell(2, 0, "||Status", hedder, sheetNo);
      setValueIntoCell(3, 0, "||Test Script Name", hedder, sheetNo);
      setValueIntoCell(4, 0, "||Test Description", hedder, sheetNo);
      setValueIntoCell(5, 0, "||Reason for Failure", hedder, sheetNo);
      setValueIntoCell(6, 0, "||Browser", hedder, sheetNo);
      setValueIntoCell(7, 0, "||Build No", hedder, sheetNo);
      setValueIntoCell(8, 0, "||Machine", hedder, sheetNo);
      setValueIntoCell(9, 0, "||Jira ID||", hedder, sheetNo);
      int row = 1;
      for (ArrayList<String> e : failures)
      {
         int column = 1;
         for (String s : e)
         {
            if (s.contains("[|]"))
            {
               s = s.replaceAll("\\|", "").replaceAll("  ", " ");
            }
            if (s.equals("Please check and update the reason for build failure"))
            {
               s = "|" + s;
               setValueIntoCell(column++, row, s, red, sheetNo);
            }
            else if (s.equals("Please check the Jenkins job manually for the reason why test scripts are getting skipped"))
            {
               s = "|" + s;
               setValueIntoCell(column++, row, s, skip, sheetNo);
            }
            else if (s.equals("Test cases skipped"))
            {
               s = "|" + s;
               setValueIntoCell(column++, row, s, appError, sheetNo);
            }
            else if (s.equals("No of test cases skipped:"))
            {
               s = "|" + s;
               setValueIntoCell(column++, row, s, appError, sheetNo);
            }
            else if (s.equals("Please check the Application manually"))
            {
               s = "|" + s;
               setValueIntoCell(column++, row, s, appError, sheetNo);
            }
            else if (s.contains(" there is not enough space on the disk."))
            {
               s = "|" + s;
               setValueIntoCell(column++, row, s, red, sheetNo);
            }
            else
            {
               setValueIntoCell(column++, row, "|" + s, white, sheetNo);
               if (column == 3)
               {
                  String print = null;
                  print = s.replaceAll("[|]", "");
                  if (print.equals("Build Failed"))
                  {
                     print = "|Not available|";
                     setValueIntoCell(9, row, print, white, sheetNo);
                  }
               }
               if (column == 4)
               {
                  String print = null;
                  print = s.replaceAll("[|]", "");
                  boolean flag = false;
                  print = null;
                  jira: for (ArrayList<String> d : atfFailures)
                  {
                     print = s.replaceAll("[|]", "");
                     String h = d.get(1).toString();
                     if (print.equals(h))
                     {
                        print = "|" + d.get(3).toString() + "|";
                        setValueIntoCell(9, row, print, white, sheetNo);
                        flag = true;
                        break jira;
                     }
                     else
                     {
                        flag = false;
                     }
                  }
                  if ( !flag)
                  {
                     print = "|Not available|";
                     setValueIntoCell(9, row, print, white, sheetNo);
                  }
               }
            }
         }
         if (row <= failures.size())
         {
            setValueIntoCell(0, row, String.valueOf("|" + row), white, sheetNo);
         }
         row++;
      }
      String print = null;
      for (ArrayList<String> d : atfFailures)
      {
         String h = d.get(0).toString();
         if (h.equalsIgnoreCase("SKIPPED"))
         {
            int column = 1;
            setValueIntoCell(column++, row, "|", skip, sheetNo);
            setValueIntoCell(column++, row, "|" + h, skip, sheetNo);
            setValueIntoCell(column++, row, "|" + d.get(1), skip, sheetNo);
            setValueIntoCell(column++, row, "|" + d.get(2), skip, sheetNo);
            setValueIntoCell(column++, row, "|" + d.get(4), skip, sheetNo);
            setValueIntoCell(column++, row, "|", skip, sheetNo);
            setValueIntoCell(column++, row, "|", skip, sheetNo);
            setValueIntoCell(column++, row, "|", skip, sheetNo);
            print = "|" + d.get(3).toString() + "|";
            setValueIntoCell(9, row, print, skip, sheetNo);
            setValueIntoCell(0, row, String.valueOf("|" + row), skip, sheetNo);
            row++;
         }
      }
      failures = new ArrayList<ArrayList<String>>();
      sheetAutoFitColumns();
   }


   private void sheetAutoFitColumns()
   {
      WritableSheet sheet = wwbCopy.getSheet(sheetName);
      for (int i = 0; i < sheet.getColumns(); i++)
      {
         Cell[] cells = sheet.getColumn(i);
         int longestStrLen = -1;
         if (cells.length == 0)
         {
            continue;
         }
         /* Find the widest cell in the column. */
         for (Cell cell : cells)
         {
            if (cell.getContents().length() > longestStrLen)
            {
               String str = cell.getContents();
               if (str == null || str.isEmpty())
               {
                  continue;
               }
               longestStrLen = str.trim().length();
            }
         }
         /* If not found, skip the column. */
         if (longestStrLen == -1)
         {
            continue;
         }
         /* If wider than the max width, crop width */
         if (longestStrLen > 255)
         {
            longestStrLen = 255;
         }
         CellView cv = sheet.getColumnView(i);
         if ((longestStrLen * 256 + 100) >= 40000)
         {
            cv.setSize(40000); /* Every character is 256 units wide, so scale it. */
         }
         else
         {
            cv.setSize(longestStrLen * 256 + 100); /* Every character is 256 units wide, so scale it. */
         }
         sheet.setColumnView(i, cv);
         sheet.setColumnView(0, 5);
         sheetName = null;
      }
   }


   private void scrollUp()
   {
      Actions action = new Actions(webController.getWebDriver());
      for (int i = 1; i <= 10; i++)
      {
         action.sendKeys(Keys.PAGE_UP).build().perform();
      }
      webController.delay(2);
   }


   public void sendEmail(String appName)
   {
      String to = null;
      if (appName.equals("AADW8"))
      {
         // to = "sivakumar.azhagesan@accenture.com,naveen.kumar.nagaraj@accenture.com";
         to = "sivakumar.azhagesan@accenture.com";
      }
      else if (appName.equals("ACRM Mobile"))
      {
         to = "naveen.kumar.nagaraj@accenture.com,sivakumar.azhagesan@accenture.com";
      }
      else if (appName.equals("C360 Desktop"))
      {
         to = "naveen.kumar.nagaraj@accenture.com,sivakumar.azhagesan@accenture.com";
      }
      else if (appName.equals("MyAccount"))
      {
         to = "shuvo.deb@accenture.com,nagaraju.meka@accenture.com";
      }
      else if (appName.equals("MyServices"))
      {
         to = "d.negi@accenture.com,nagaraju.meka@accenture.com";
      }
      else if (appName.equals("OBP"))
      {
         to = "nagaraju.meka@accenture.com";
      }
      else if (appName.equals("PMT"))
      {
         to = "neha.rajeshwarkar@accenture.com,ramya.mathivanan@accenture.com";
      }
      else if (appName.equals("Registration"))
      {
         to = "shuvo.deb@accenture.com,d.negi@accenture.com";
      }
      else if (appName.equals("EMS"))
      {
         to = "ramya.mathivanan@accenture.com,neha.rajeshwarkar@accenture.com";
      }
      else if (appName.equals("OKM"))
      {
         to = "sachin.sasikumar@accenture.com,naveen.kumar.nagaraj@accenture.com";
      }
      else if (appName.equals("MyAccount Redesign"))
      {
         to = "shuvo.deb@accenture.com,nagaraju.meka@accenture.com";
      }
      else if (appName.equals("ITK"))
      {
         to = "sachin.sasikumar@accenture.com,naveen.kumar.nagaraj@accenture.com";
      }
      else if (appName.equals("SBTool"))
      {
         to = "naveen.kumar.nagaraj@accenture.com";
      }
      SendAttachmentInEmail sendMail = new SendAttachmentInEmail(webController.getWebDriver());
      sendMail.sendMail(excelName, excelPath, to);
      log.info("Mail sent");
   }


   public void weTransfer(String to)
   {
      webController.get("https://www.wetransfer.com/");
      webController.waitForElementVisible("//a[contains(text(),'Take me to free')]", 40);
      webController.click("//a[contains(text(),'Take me to free')]");
      webController.waitForElementVisible("//div[@id='accepting']", 40);
      webController.delay(2);
      webController.click("//div[@id='accepting']");
      webController.delay(1);
      webController.sendKeys("//*[@id='uploader_field']", excelPath);
      if ( !to.contains("sachin.sasikumar@accenture.com"))
      {
         to = to + ",sachin.sasikumar@accenture.com";
      }
      if ( !to.contains("nagaraju.meka@accenture.com"))
      {
         to = to + ",nagaraju.meka@accenture.com";
      }
      if ( !to.contains("sivakumar.azhagesan@accenture.com"))
      {
         to = to + ",sivakumar.azhagesan@accenture.com";
      }
      webController.click("//*[@id='to']");
      webController.delay(1);
      webController.sendKeys("//*[@id='to']", to);
      String message = excelName.replace("C:\\apps\\JenkinsFailures\\", "").trim();
      message = message + ". Please click the below link to download the report";
      webController.delay(1);
      webController.sendKeys("//*[@id='message']", message);
      webController.waitForElementVisible("//*[@id='add-sender']", 40);
      webController.delay(1);
      webController.click("//*[@id='add-sender']");
      webController.delay(1);
      webController.sendKeys("//*[@id='from']", "nagaraju.meka@accenture.com");
      webController.delay(1);
      webController.click("//*[@id='transfer']");
      webController.waitForElementVisible("//div[@id='ok']", 90);
   }


   private String getCurrentRunningBrowser(String browserName)
   {
      String browser = null;
      browser = browserName.toLowerCase().replaceAll(" ", "");
      if (browser.contains("chrome"))
      {
         browser = "Chrome";
      }
      else if (browser.contains("firefox"))
      {
         browser = "Firefox";
      }
      else
      {
         browser = "Internetexplorer";
      }
      return browser;
   }
}