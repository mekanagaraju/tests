package test.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;

import test.tests.SingleSeleniumSolution;

public class abcScreen
{
   public abcScreen(WebDriver webDriver)
   {
      this.webDriver = new SingleSeleniumSolution(webDriver);
   }
   protected Log log = LogFactory.getLog(this.getClass().getName());
   SingleSeleniumSolution webDriver;
   public static final String MAIN_ELEMENT = "css=table[class='OuterTable']";
   /*** TABS ***/
   public static final String TAB_SEARCH = "//tr//td[2]/ul/li[1]";
   public static final String TAB_SEARCH_SEARCH = "//tr//td[2]/ul/li[1]/ul[1]/li[1]";
   public static final String TAB_SEARCH_PUBLICSEARCH = "//tr//td[2]/ul/li[1]/ul[1]/li[2]";
   public static final String TAB_SEARCH_ADDITEM = "//tr//td[2]/ul/li[1]/ul[1]/li[3]";
   /*** ***/
   public static final String TAB_REPORTING = "//tr//td[2]/ul/li[2]";
   /*** ***/
   public static final String TAB_IMPORT = "//tr//td[2]/ul/li[3]";
   /*** ***/
   public static final String TAB_SENTRYID = "//tr//td[2]/ul/li[4]";
   /*** ***/
   public static final String TAB_ADMIN = "//tr//td[2]/ul/li[5]";
   /***  ***/
   public static final String SEARCHFOR_LABLE = "//label[contains(text(), 'Search For:')]";
   public static final String SEARCHIN_LABLE = "//label[contains(text(), 'Search in:')]";
   public static final String REGION_LABLE = "//label[contains(text(), 'Region:')]";
   public static final String INCLUDETERMED_LABLE = "//label[contains(text(), 'Include Termed:')]";
   public static final String SELECTTEAM_LABLE = "//label[contains(text(), 'Select Team:')]";
   public static final String SEARCH_BUTTON = "css=input[value='Search']";
   public static final String SEARCHFOR_INPUTBOX = "css=input[name='searchId']";
   public static final String SEARCHIN_DROPDOWN = "css=select[id*='searchIn']";
   public static final String SEARCHIN_ALL = SEARCHIN_DROPDOWN + " option[value*='All']";
   public static final String SEARCHIN_AVAYA = SEARCHIN_DROPDOWN + " option[value*='Avaya']";
   public static final String SEARCHIN_EID = SEARCHIN_DROPDOWN + " option[value*='EID']";
   public static final String SEARCHIN_FIRSTNAME = SEARCHIN_DROPDOWN + " option[value*='FirstName']";
   public static final String SEARCHIN_LASTNAME = SEARCHIN_DROPDOWN + " option[value*='LastName']";
   public static final String SEARCHIN_CSGID = SEARCHIN_DROPDOWN + " option[value*='CSGID']";
   public static final String SEARCHIN_PEOPLESOFTID = SEARCHIN_DROPDOWN + " option[value*='PeopleSoftID']";
   public static final String SEARCHIN_SALESREPID = SEARCHIN_DROPDOWN + " option[value*='SalesRepID']";
   public static final String SEARCHIN_TECHID = SEARCHIN_DROPDOWN + " option[value*='TechID']";
   public static final String SEARCHRESULT_NAME = "css=div[id='main'] >span";
   public static final String FIRSTNAME_TEXT = "//*[@id='main']/table//tr/td[1]/div[1]/div//tr[1]/td[2]";
   public static final String BUTTON_SEARCH = "css=input[value='Search']";
   public static final String REGION_DROPDOWN = "css=select[id*='region']";
   public static final String REGION_TEXAS = "//*[@id='region']/option[6]";
   public static final String SEARCH_TABLE = "//*[@id='searchResultsTable']";
   public static final String SEARCHIN_ALLFIELDS = "//*[@id='searchIn']/option[1]";
   public static final String SEARCH_ELEMENT = "//*[@id='main']/span";
   public static final String SEARCH_FOR = "//*[@id='main']/form/div/table/tbody/tr[1]/td[1]/label";
   public static final String SEARCH_IN = "//*[@id='main']/form/div/table/tbody/tr[2]/td[1]/label";
   public static final String REGION = "//*[@id='main']/form/div/table/tbody/tr[3]/td[1]/label";
   public static final String INCLUDE_TERMED = "//*[@id='main']/form/div/table/tbody/tr[4]/td[1]/label";
   public static final String SELECT_TEAM = "//*[@id='main']/form/div/table/tbody/tr[7]/td[1]/label";
   public static final String SEARCHIN_REPID = "//*[@id='searchIn']/option[8]";
   public static final String SEARCHIN_TECH_ID = "css=option[value='TechID']";
   public static final String REGION_DROPDOWN_EAST = "css=option[value='4']";
   public static final String MARKET_TEXT = "//*[@id='main']//td[1]/div[1]/div//tr[7]/td[2]";
   public static final String SEARCH_RESULT_FIRST = "//*[@id='searchResultsTable']/tbody/tr[1]/td[2]/a";
   public static final String SALES_REP_DIV_TEXT = "//*[@id='SalesTable']/tbody/tr/td[1]/div";
   public static final String AVAYA_ID = "//*[@id='main']/table/tbody/tr/td/table/tbody/tr/td[1]/div[5]/table/tbody/tr/td[1]/div";
   public static final String SEARCH_TERMED_TABLE = "//*[@id='searchResultsTable']";
   public static final String GET_COLOR = "//*[@id='searchResultsTable']/tbody/tr[1]/td[1]";
   // Sentry ID Elements
   public static final String SENTRY_ID = "//*[@id='listMenuRoot']/li[4]/a";
   public static final String SENTRY_ID_SEARCH = "//*[@id='listMenu-id-4']/li[1]/a";
   public static final String SENTRY_ID_SEARCHPAGE = "//span[contains(text(),'Search Sentry Id')]";
   public static final String SENTRY_ID_SEARCHFOR = "//label[contains(text(),'Search For:')]";
   public static final String SENTRY_ID_CLASS = "//label[contains(text(),'Class:')]";
   public static final String SENTRY_ID_BILLINGGROUP = "//label[contains(text(),'Billing Group:')]";
   public static final String SENTRY_ID_EDWGROUP = "//label[contains(text(),'Edw Group:')]";
   public static final String SENTRY_ID_RANGETYPE = "//label[contains(text(),'Range Type:')]";
   public static final String SENTRY_ID_INCLUDE = "//td[contains(text(),'Include:')]";
   public static final String SENTRY_ID_EDIT_EMPLOYEE = "//*[@class='tb_noborder']//tr[8]//a[contains(text(),'Edit Employee')]";
   public static final String SENTRY_ID_MANAGE_EMPLOYEE = "//*[contains(text(),'Manage Employee Data')]";
   public static final String SENTRY_ID_LAUNCH_CHANNELHELPER = "//div[4]/table/tbody/tr/td[6]/div/img";
   public static final String SENTRY_ID_SELECT_REGION = "css=select[id='bcRegion']";
   public static final String SENTRY_ID_SELECT_CHANNEL = "css=select[id='Channels']";
   public static final String SENTRY_ID_SELECT_RANGEGROUP = "css=select[id='Ranges']";
   public static final String SENTRY_ID_SELECT_MASTERRANGE = "css=select[id='Masters']";
   public static final String SENTRY_ID_SELECT_SUBRANGE = "css=select[id='Subs']";
   public static final String SENTRY_ID_SELECT_EMPLOYEETYPE = "css=select[id='EmpTypes']";
   public static final String SENTRY_ID_SELECT_AVAILABLE = "//div[3]/div/table/tbody/tr[4]/td[2]";
   // SENTRY CHANNEL MANAGER FLOW
   public static final String CHANNEL_MANAGER = "//*[@id='listMenu-id-4']/li[2]/a";
   public static final String SENTRYCHANNELLISTPAGE = "//span[contains(text(),'Channel List')]";
   public static final String SENTRYSEARCHFORINPUT = "css=input[id='searchId']";
   public static final String SENTRYRANGE = "css=a[href='/RangeGroup/List/126']";
   public static final String SENTRYMASTER = "css=a[href='/MasterRange/List/299']";
   public static final String SENTRYSUBRANGE = "css=a[href='/SubRange/List/1193']";
   public static final String SENTRYACTIVESUBRANGE = "css=a[href='/SubRange/Details/6206']";
   public static final String SENTRYSINGLESALEID = "css=tr[id='13']";
   public static final String SENTRYSINGLESALEIDUNQUARITINE = "css=tr[id='13'] td[title='UnQuarantine']";
   public static final String SENTRYSINGLESALEIDAVAILABLE = "css=tr[id='13'] td[title='Available']";
   // Manage Employee Data
   public static final String SENTRY_ID_MANAGE_ID = "css=input[id='salesIdtoAdd']";
   public static final String SENTRY_ID_MANAGE_GROUPID = "css=input[id='salesGroupId']";
   public static final String SENTRY_ID_MANAGE_ETID = "css=input[id='salesETId']";
   public static final String SENTRY_ID_MANAGE_EFFECTIVEDATE = "css=input[id='salesEffectiveDate']";
   public static final String SENTRY_ID_MANAGE_COMMIT = "css=input[value='Commit Changes']";
   public static final String SENTRY_ID_MANAGE_ADD_SALESID = "css=input[value='Add Sales ID']";
   public static final String SENTRY_ID_SELECT_SALESID = "//div[3]/div/table/tbody/tr[89]/td[1]";
   public static final String SENTRY_ID_MANAGE_FORWARDARROW = "css=span[class='ui-icon ui-icon-seek-next']";
   public static final String SENTRY_ID_MANAGE_EDIT_SALESROW = "//table[@id='SalesTable']//tr[2]/td[contains(text(),'3088')]";
   public static final String SENTRY_ID_MANAGE_EDIT_TECHROW = "//table[@id='TechTable']//tr[2]/td[contains(text(),'3088')]";
   public static final String SENTRY_ID_MANAGE_SALESROW = "//table[@id='SalesTable']//tbody/tr/td/div[contains(text(),'3088')]";
   public static final String SENTRY_ID_MANAGE_TECHROW = "//table[@id='TechTable']//tbody/tr/td/div[contains(text(),'3088')]";
   public static final String SEARCH_DROPDOWN_SEARCH = "css=ul[id='listMenu-id-1'] a[href='/Employee/Search']";
   public static final String SEARCH_DROPDOWN = "//*[@id='listMenuRoot']/li[1]/a";
   public static final String EDIT_EMPLOYEE = "//*[@id='main']/table//td//tr/td[1]/div[4]//tr[8]/td[2]/span/a";
   public static final String CREATE_TEAM = "//*[@id='main']/table//td//tr/td[1]/div[4]//tr[8]/td[3]/span/a";
   public static final String SEARCH_DROPDOWN_PUBLICSEARCH = "//label[contains(text(), 'Public Search')]";
   public static final String SEARCHIN_OPERATOR_ID = "//*[@id='searchIn']/option[6]";
   public static final String VERIFY_OPT_ID = "//*[@id='BillingTable']/tbody/tr/td[1]/div";
   public static final String SELECT_TEAM_DROPDOWN = "//*[@id='team']";
   public static final String SELECT_TEAM_96TH = "//html/body/div[2]/table//tr[1]//table//tr[7]/td[2]/select/option[12]";
   public static final String EMP_DETAIL_SCREEN_TEAM = "//*[@id='main']/table//tbody/tr/td[1]/div[1]/div//tr[18]/th";
   public static final String EMP_DETAIL_TEAM_NAME = "//*[@id='main']/table//table//td[1]/div[1]/div/table//tr[18]/td[2]";
   public static final String SEARCHIN_PEOPLESOFT_ID = "//*[@id='searchIn']/option[7]";
   public static final String EMP_DETAIL_SCREEN_PEOPLESOFT_NAME = "//*[@id='main']/table//tr/td[1]/div[3]//tr[2]/th";
   public static final String EMP_DETAIL_SCREEN_PEOPLESOFT_ID = "//*[@id='main']/table//td[1]/div[3]//tr[2]/td[2]";
   public static final String CREATE_NEW_ITEM_HEADER = "//*[@id='main']/span";
   public static final String CREATE_NEW_ITEM_LOGO = "//*[@id='CreateForm']/table//tr[1]/th";
   public static final String CREATE_NEW_ITEM_TEAMNAME = "//*[@id='CreateForm']/table//tr[2]/th";
   public static final String CREATE_NEW_ITEM_SUPERVISOR = "//*[@id='CreateForm']/table//tr[3]/th";
   public static final String OPEN_SUPERVISOR_RADIO_BUTTON = "//*[@id='rbSupervisorOpen']";
   public static final String OPEN_SUPERVISOR_HEADER = "//*[@id='divOpenSupervisor']/table/thead/tr/td";
   public static final String OPEN_SUPERVISOR_MANAGER_EID = "//*[@id='divOpenSupervisor']/table//tr[1]/th";
   public static final String OPEN_SUPERVISOR_LOCATION = "//*[@id='divOpenSupervisor']/table//tr[2]/th";
   public static final String OPEN_SUPERVISOR_MARKET = "//*[@id='divOpenSupervisor']/table//tr[3]/th";
   public static final String CREATE_TEAM_BUTTON = "//*[@id='CreateForm']/table//tr[5]/td[2]/input";
   public static final String TEAMNAME_INPUTBOX = "//*[@id='TeamName']";
   public static final String MANAGERS_EID_INPUTBOX = "//*[@id='ManagerEID']";
   public static final String LOCATION_DROPDOWN = "//*[@id='selectedAddress']";
   public static final String LOCATION_DROPDOWN_1279RT = "//*[@id='selectedAddress']/option[232]/text()";
   public static final String MARKET_DROPDOWN = "//*[@id='selectedMarket']";
   public static final String MARKET_DROPDOWN_MANHATTAN = "//*[@id='selectedMarket']/option[29]/text()";
   public static final String EMP_DETAIL_SCREEN_TEAMNAME = "//*[@id='main']/table//td[1]/div[1]//tr[18]/td[2]";
   public static final String EMP_DETAIL_SCREEN_SUP_EID = "//*[@id='main']//td[2]/div[2]//tr[1]/td[1]/div/a";
   public static final String LASTNAME_TEXT = "//*[@id='main']/table//td[1]/div[1]/div//tr[3]/td[2]";
   public static final String BUTTON_REGISTERNOW = "//*[@id='login-info']/a";


   public void getGoogleURL()
   {
      webDriver.get("https://myacom-staging-bravo.dev-webapps.timewarnercable.com/login/index");
      // webDriver.getURL("https://www.google.co.in");
   }


   public void clickRegisterNow()
   {
      webDriver.delay(5);
      webDriver.waitForElementVisible(BUTTON_REGISTERNOW, 120);
      log.info("Clicking on Register now button");
      webDriver.click(BUTTON_REGISTERNOW);
      webDriver.delay(10);
   }


   public void getRegisterURL()
   {
      String url = webDriver.getCurrentUrl();
      /* url = url.replace("https://", "");
       * String[] newURL = url.split("/");
       * log.info(newURL[0]);
       * log.info(newURL[1]); */
      String[] newURL = url.split("=");
      log.info(newURL[0]);
      log.info(newURL[1]);
   }


   public void searchWithRepIDValidation(String repID)
   {
      webDriver.waitForElementVisible(MAIN_ELEMENT, 120);
      log.info("Logged in into EMS Application Successfully");
      webDriver.waitForElementVisible(SEARCHFOR_INPUTBOX, 220);
      log.info("Setting the Search For as :" + repID);
      webDriver.type(SEARCHFOR_INPUTBOX, repID);
      webDriver.waitForElementVisible(SEARCHIN_DROPDOWN, 120);
      log.info("Select the Search In Drop down as Sales Rep ID");
      webDriver.click(SEARCHIN_REPID);
      webDriver.waitForElementVisible(REGION_DROPDOWN, 120);
      webDriver.select(REGION_DROPDOWN, REGION_DROPDOWN_EAST);
      log.info("Select the Region Drop down as East");
      webDriver.click(REGION_DROPDOWN_EAST);
      webDriver.waitForElementVisible(BUTTON_SEARCH, 220);
      log.info("Click the Search button");
      webDriver.click(BUTTON_SEARCH);
      webDriver.waitForElementVisible(SEARCH_RESULT_FIRST, 220);
      log.info("Click the EID present in the Table");
      webDriver.click(SEARCH_RESULT_FIRST);
      webDriver.waitForElementVisible(SEARCH_ELEMENT, 120);
   }


   public void clickSentryIDSearch()
   {
      webDriver.moveToAndClick(SENTRY_ID);
      webDriver.waitForElementVisible(SENTRY_ID_SEARCH, 30);
      webDriver.click(SENTRY_ID_SEARCH);
      webDriver.waitForElementVisible(SENTRY_ID_SEARCHPAGE, 30);
      log.info("Sentry is Search page is displayed.");
   }
}
