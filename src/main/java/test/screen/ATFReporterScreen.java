package test.screen;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;

import test.tests.SingleSeleniumSolution;

public class ATFReporterScreen
{
   protected Log log = LogFactory.getLog(this.getClass().getName());
   SingleSeleniumSolution webController;
   protected static final String AAD = "//a[contains(text(), 'AAD-Web8')]";
   protected static final String ACRMMOBILE = "//a[contains(text(), 'ACRMMobile')]";
   protected static final String C360DESKTOP = "//a[contains(text(), 'C360_Desktop')]";
   protected static final String MYACCOUNT = "//a[contains(text(), 'MyAccount')]";
   protected static final String MYSERVICES = "//a[contains(text(), 'MyServices')]";
   protected static final String OBP = "//a[contains(text(), 'OBP')]";
   protected static final String PMT = "//a[contains(text(), 'PMT')]";
   protected static final String REGISTRATION = "//a[contains(text(), 'Registration')]";
   protected static final String EMS = "//a[contains(text(), 'EMS')]";
   protected static final String OKM = "//a[contains(text(), 'OKM-InfoCenter')]";
   protected static final String MYACCOUNTREDESIGN = "//a[contains(text(), 'MyAccountRedesign')]";
   protected static final String ITK = "//a[contains(text(), 'IssueTrak')]";
   protected static final String SBT = "//a[contains(text(), 'SFB TechOps')]";
   ArrayList<ArrayList<String>> atfFailures = new ArrayList<ArrayList<String>>();


   public ATFReporterScreen(WebDriver webDriver)
   {
      this.webController = new SingleSeleniumSolution(webDriver);
   }


   public ArrayList<ArrayList<String>> getJiraID(String app)
   {
      webController.get("http://innes.corp.mystrotv.com/#/productv3");
      webController.delay(5);
      launchReporter(app);
      return atfFailures;
   }


   private void launchReporter(String app)
   {
      String element = "//select[@id='profile']";
      webController.waitForElementVisible(element, 60);
      try
      {
         webController.select(element, element + "/option[contains(text(), 'CAFA')]");
      } catch (Exception e)
      {
         webController.click(element + "/option[contains(text(), 'CAFA')]");
      }
      webController.delay(5);
      switch (app.toUpperCase().replaceAll(" ", ""))
      {
         case "AADW8":
            openFailures(AAD);
            break;
         case "ACRMMOBILE":
            openFailures(ACRMMOBILE);
            break;
         case "C360DESKTOP":
            openFailures(C360DESKTOP);
            break;
         case "MYACCOUNT":
            openFailures(MYACCOUNT);
            break;
         case "MYSERVICES":
            openFailures(MYSERVICES);
            break;
         case "OBP":
            openFailures(OBP);
            break;
         case "PMT":
            openFailures(PMT);
            break;
         case "REGISTRATION":
            openFailures(REGISTRATION);
            break;
         case "EMS":
            openFailures(EMS);
            break;
         case "OKM":
            openFailures(OKM);
            break;
         case "MYACCOUNTREDESIGN":
            openFailures(MYACCOUNTREDESIGN);
            break;
         case "ITK":
            openFailures(ITK);
            break;
         case "SBTOOL":
            openFailures(SBT);
            break;
      }
   }


   private void openFailures(String appName)
   {
      webController.waitForElementVisible("//th[contains(text(), 'Product')]", 90);
      String mainElement = "//div[@class='widget-content']//table[1]/tbody";
      webController.waitForElementVisible(mainElement, 90);
      int countE = webController.getElementsCount(mainElement);
      for (int i = 1; i <= countE; i++)
      {
         String findE = mainElement + "[" + i + "]" + appName;
         if (webController.isElementVisible(findE))
         {
            String elementToClick = mainElement + "[" + i + "]//td[4]//a";
            String fCount = webController.getText(elementToClick);
            if (Integer.parseInt(fCount) == 0)
            {
               atfFailures = new ArrayList<ArrayList<String>>();
            }
            else
            {
               webController.click(elementToClick);
               grabList(appName);
               break;
            }
         }
      }
   }


   private void grabList(String appName)
   {
      ArrayList<String> type = new ArrayList<String>();
      String tableName = appName.replace("//a[contains(text(), '", "//div[@class='widget-content']//div[@name='");
      type.add(tableName.replace("')]", " Failures']"));
      type.add(tableName.replace("')]", " Errors']"));
      type.add(tableName.replace("')]", " Skips']"));
      waitforTab(appName);
      for (String each : type)
      {
         String count = each + "//div[@class='widget-content']//table//tr";
         int failuresCount = webController.getElementsCount(count);
         for (int i = 2; i <= failuresCount; i++)
         {
            String JiraID = null;
            ArrayList<String> text = new ArrayList<String>();
            String nameE = count + "[" + i + "]/td[2]";
            String failurenameE = count + "[" + i + "]/td[3]/a";
            String jiraIDE = count + "[" + i + "]/td[5]//a";
            String descriptionE = count + "[" + i + "]/td[7]";
            String status = webController.getText(nameE).trim();
            String scriptName = webController.getText(failurenameE).trim();
            String description = webController.getText(descriptionE).trim();
            text.add(status);// Status
            text.add(scriptName);// Name
            text.add(description);// Description
            if (webController.isElementVisible(jiraIDE))
            {
               JiraID = webController.getText(jiraIDE).trim();// JiraID
               text.add(JiraID);
            }
            else
            {
               JiraID = "Not available";// JiraID
               text.add(JiraID);
            }
            if (status.equalsIgnoreCase("SKIPPED"))
            {
               webController.click(failurenameE);
               String body = "//div[@ng-show='tree.testCases']/div[1]/div";
               webController.delay(2);
               int testScriptCountE = webController.getElementsCount(body);
               run: for (int j = 1; j <= testScriptCountE; j++)
               {
                  String nameScriptE = "//div[@id='accordion']/div[" + j + "]/div[1]/a";
                  String scriptsName = webController.getText(nameScriptE);
                  if (scriptsName.equals(scriptName))
                  {
                     webController.click(nameScriptE);
                     webController.delay(2);
                     String element = "//div[contains(text(), '" + scriptName + " Details')]";
                     if ( !webController.isElementVisible(element))
                     {
                        webController.click(nameScriptE);
                     }
                     String tableE = "//div[@id='accordion']/div[" + j + "]/div[2]/div[2]//table";
                     webController.waitForElementVisible(tableE, 90);
                     String reasonForFailureE = tableE + "//tr[2]/td[9]";
                     String reasonForFailure = webController.getText(reasonForFailureE).trim();
                     text.add(reasonForFailure);
                     break run;
                  }
               }
               webController.goBack();
               webController.waitForElementVisible(type.get(2), 90);
            }
            log.info(text);
            atfFailures.add(text);
         }
      }
   }


   private void waitforTab(String appName)
   {
      String wait1 = appName.replace("//a", "//div").replace("')]", " Failures')]");
      String wait2 = appName.replace("//a", "//div").replace("')]", " Errors')]");
      String wait3 = appName.replace("//a", "//div").replace("')]", " Skips')]");
      for (int i = 1; i <= 10; i++)
      {
         if (webController.isElementVisible(wait1))
         {
            break;
         }
         else if (webController.isElementVisible(wait2))
         {
            break;
         }
         else if (webController.isElementVisible(wait3))
         {
            break;
         }
         else
         {
            webController.delay(1);
         }
      }
   }
}
