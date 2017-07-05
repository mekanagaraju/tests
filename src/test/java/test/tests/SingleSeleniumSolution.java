/**
 * Author: Nagaraju.Meka
 * File: SingleSeleniumSolution.java
 * Created: 11/9/2015
 *
 * Description: SingleSeleniumSolution contains most of the reusable methods. Please feel free to add the new methods
 * and let me know as well :) .
 * Which will help's you to decrease the time while creating the reusable methods and there by we can put more focus on
 * test cases
 *
 **/
package test.tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.SkipException;

public class SingleSeleniumSolution
{
   protected static final Logger log = LoggerFactory.getLogger(SingleSeleniumSolution.class);
   private final WebDriver webDriver;
   private static final String CSS_PREFIX = "css=", VALUE = "value";
   private static String sysEnv;
   private static final double RED_RATIO = 0.2126f, BLUE_RATIO = 0.0722f, GREEN_RATIO = 0.7152f, PIXEL_ERROR_THRESHOLD_RATIO = 0.2;
   private static final int BYTE_MASK = 0x000000FF, DEFAULT_KERNEL_RADIUS = 2, TEN = 10, FIVE = 5;
   private static final float PIXEL_ERROR_LIMIT = 1000;
   private static final double PIXEL_THRESHOLD_CUBE_LUM_DIFF = PIXEL_ERROR_THRESHOLD_RATIO * PIXEL_ERROR_LIMIT;


   public SingleSeleniumSolution(WebDriver weDriver)
   {
      this.webDriver = weDriver;
   }


   /**
    * This method build and return a webDriver instance by which one can use to control the automation of a specified
    * web browser and platform or Operating System.
    *
    * @param url - main test url
    * @param browserType - type of browser to automate
    * @param platform - operating system or platform type
    * @return
    * @return - Instance of WebBrowser
    */
   public static SingleSeleniumSolution buildWebDriver(String url, String browserName, Method method)
   {
      Annotation[] a = method.getAnnotations();
      String description = a[0].toString().split("description=")[1].split(", retryAnalyzer=")[0].split(",")[0];
      System.gc();
      log.info("");
      log.info("=========================================================");
      log.info("@Test : {}", method.getName());
      log.info("@Test(description : {})", description);
      log.info("=========================================================");
      WebDriver wd = buildWebDriver(browserName);
      SingleSeleniumSolution wDriver = new SingleSeleniumSolution(wd);
      wd.get(url);
      log.info("Starting WebDriver: { Browser: {} } { Version: {} } { Platform: {} } ", wDriver.getBrowserName().trim(), wDriver.getBrowserVersion().trim(), wDriver.discoverPlatform().toString());
      log.info("Navigating to: {}", url);
      wDriver.windowMaximize();
      return new SingleSeleniumSolution(wd);
   }


   /**
    * This method build and return a webDriver instance by which one can use to control the automation of a specified
    * web browser and platform or Operating System.
    *
    * @param url - main test url
    * @param browserType - type of browser to automate
    * @param platform - operating system or platform type
    * @param seleniumGridUrl - selenium Grid Url
    * @return
    * @return - Instance of WebBrowser
    */
   public static SingleSeleniumSolution buildRemoteWebDriver(String url, String browserName, Method method)
   {
      System.gc();
      log.info("=========================================================");
      log.info("Test Name: " + method.getName());
      log.info("");
      log.info("=========================================================");
      WebDriver wd = buildRemoteWebDriver(browserName);
      SingleSeleniumSolution wDriver = new SingleSeleniumSolution(wd);
      wd.get(url);
      log.info("Building Remote WebDriver: " + wDriver.getBrowsersNameVersion().trim() + " { Platform: " + wDriver.discoverPlatform().toString() + " }");
      log.info("Launching: " + url);
      wDriver.windowMaximize();
      return new SingleSeleniumSolution(wd);
   }


   /**
    * This method build a webDriver based on the passed in browser request
    *
    * @param browser
    * @return WebDriver
    * @throws MalformedURLException
    */
   private static WebDriver buildWebDriver(String browserName)
   {
      DesiredCapabilities capability = null;
      BrowserType browserType = BrowserType.getBrowserTypeFromString(browserName);
      switch (browserType)
      {
         case MARIONETTE:
            FirefoxProfile ffProfile = null;
            ffProfile = new FirefoxProfile();
            ffProfile.setAcceptUntrustedCertificates(true);
            ffProfile.setAssumeUntrustedCertificateIssuer(false);
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            cap.setCapability("marionette", true);
            cap.setCapability("firefox_profile", ffProfile);
            cap.setCapability("handlesAlerts", true);
            sysEnv = System.getenv("webdriver.gecko.driver");
            if (sysEnv == null)
            {
               sysEnv = findFileName("C:/selenium", "geckodriver.exe", FileSearchType.File);
               if (sysEnv == null)
               {
                  log.info("Please set the webdriver.gecko.driver in system environment variables and restart the PC OR copy all your webdrivers under 'C:/selenium' location");
                  throw new RuntimeException("Failed to instantiate a WebDriver instance for " + browserName);
               }
               else
               {
                  System.setProperty("webdriver.gecko.driver", sysEnv);
               }
            }
            return new MarionetteDriver(capability);
         case FIREFOX_DRIVER:
            capability = DesiredCapabilities.firefox();
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setAcceptUntrustedCertificates(true);
            firefoxProfile.setEnableNativeEvents(true);
            firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
            capability.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
            return new FirefoxDriver(capability);
         case CHROME_DRIVER:
            sysEnv = System.getenv("webdriver.chrome.driver");
            if (sysEnv == null)
            {
               sysEnv = findFileName("C:/selenium", "chromedriver.exe", FileSearchType.File);
               if (sysEnv == null)
               {
                  log.info("Please set the webdriver.chrome.driver in system environment variables and restart the PC OR copy all your webdrivers under 'C:/selenium' location");
                  throw new RuntimeException("Failed to instantiate a WebDriver instance for " + browserName);
               }
               else
               {
                  System.setProperty("webdriver.chrome.driver", sysEnv);
               }
            }
            capability = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            options.addArguments(new String[] { "--allow-running-insecure-content" });
            options.addArguments(new String[] { "--ignore-certificate-errors" });
            options.addArguments(new String[] { "--enable-npapi" });
            options.addArguments(new String[] { "--disable-extensions" });
            options.addArguments(new String[] { "--start-maximized" });
            capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capability.setCapability(ChromeOptions.CAPABILITY, options);
            return new ChromeDriver(capability);
         case INTERNET_EXPLORER:
            sysEnv = System.getenv("webdriver.ie.driver");
            if (sysEnv == null)
            {
               sysEnv = findFileName("C:/selenium", "IEDriverServer.exe", FileSearchType.File);
               if (sysEnv == null)
               {
                  log.info("Please set the webdriver.ie.driver in system environment variables and restart the PC");
                  throw new RuntimeException("Failed to instantiate a WebDriver instance for " + browserName);
               }
               else
               {
                  System.setProperty("webdriver.ie.driver", sysEnv);
               }
            }
            capability = DesiredCapabilities.internetExplorer();
            capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capability.setCapability("ignoreProtectedModeSettings", true);
            capability.setCapability("acceptSslCerts", true);
            capability.setCapability("ignoreZoomSetting", true);
            capability.setCapability("nativeEvents", true);
            capability.setCapability("ie.ensureCleanSession", true);
            return new InternetExplorerDriver(capability);
         case SAFARI:
            capability = DesiredCapabilities.safari();
            capability.setCapability("acceptSslCerts", true);
            capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capability.setCapability("ensureCleanSession", true);
            capability.setJavascriptEnabled(true);
            return new SafariDriver(capability);
         case OPERA_DRIVER:
            capability = DesiredCapabilities.opera();
            capability.setCapability("opera.host", "127.0.0.1");
            return new OperaDriver();
         case EDGE:
            capability = DesiredCapabilities.edge();
            EdgeOptions option = new EdgeOptions();
            capability.setCapability("edgeOptions", option);
            return new EdgeDriver(capability);
         default:
            log.info("Currenty support is there for Chrome, Firefox, Firefox Marionette, Internet Explorer, Edge, Safari & Opera. Support is not there for " + browserName);
            capability = DesiredCapabilities.firefox();
            FirefoxProfile firefoxProf = new FirefoxProfile();
            firefoxProf.setAcceptUntrustedCertificates(true);
            firefoxProf.setEnableNativeEvents(true);
            firefoxProf.setAssumeUntrustedCertificateIssuer(true);
            capability.setCapability(FirefoxDriver.PROFILE, firefoxProf);
            return new FirefoxDriver(capability);
      }
   }


   /**
    * This method build a RemoteWebDriver based on the passed in browser request
    *
    * @param browser
    * @return RemoteWebDriver
    *
    */
   private static RemoteWebDriver buildRemoteWebDriver(String browserName)
   {
      DesiredCapabilities capability = null;
      BrowserType browserType = BrowserType.getBrowserTypeFromString(browserName);
      switch (browserType)
      {
         case MARIONETTE:
            FirefoxProfile ffProfile = null;
            ffProfile = new FirefoxProfile();
            ffProfile.setAcceptUntrustedCertificates(true);
            ffProfile.setAssumeUntrustedCertificateIssuer(false);
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            cap.setCapability("marionette", true);
            cap.setCapability("firefox_profile", ffProfile);
            cap.setCapability("handlesAlerts", true);
            sysEnv = System.getenv("webdriver.gecko.driver");
            if (sysEnv == null)
            {
               sysEnv = findFileName("C:/selenium", "geckodriver.exe", FileSearchType.File);
               if (sysEnv == null)
               {
                  log.info("Please set the webdriver.gecko.driver in system environment variables and restart the PC OR copy all your webdrivers under 'C:/selenium' location");
                  throw new RuntimeException("Failed to instantiate a WebDriver instance for " + browserName);
               }
               else
               {
                  System.setProperty("webdriver.gecko.driver", sysEnv);
               }
            }
            return new MarionetteDriver(capability);
         case FIREFOX_DRIVER:
            capability = DesiredCapabilities.firefox();
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setAcceptUntrustedCertificates(true);
            firefoxProfile.setEnableNativeEvents(true);
            capability.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
            capability.setPlatform(capability.getPlatform());
            capability.setVersion(capability.getVersion());
            return new FirefoxDriver(capability);
         case CHROME_DRIVER:
            sysEnv = System.getenv("webdriver.chrome.driver");
            if (sysEnv == null)
            {
               sysEnv = findFileName("C:/selenium", "chromedriver.exe", FileSearchType.File);
               if (sysEnv == null)
               {
                  log.info("Please set the webdriver.chrome.driver in system environment variables and restart the PC OR copy all your webdrivers under 'C:/selenium' location");
                  throw new RuntimeException("Failed to instantiate a WebDriver instance for " + browserName);
               }
               else
               {
                  System.setProperty("webdriver.chrome.driver", sysEnv);
               }
            }
            capability = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            options.addArguments(new String[] { "--allow-running-insecure-content" });
            options.addArguments(new String[] { "--ignore-certificate-errors" });
            options.addArguments(new String[] { "--enable-npapi" });
            options.addArguments(new String[] { "--disable-extensions" });
            options.addArguments(new String[] { "--start-maximized" });
            capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capability.setCapability(ChromeOptions.CAPABILITY, options);
            capability.setPlatform(capability.getPlatform());
            capability.setVersion(capability.getVersion());
            return new ChromeDriver(capability);
         case INTERNET_EXPLORER:
            sysEnv = System.getenv("webdriver.ie.driver");
            if (sysEnv == null)
            {
               sysEnv = findFileName("C:/selenium", "IEDriverServer.exe", FileSearchType.File);
               if (sysEnv == null)
               {
                  log.info("Please set the webdriver.ie.driver in system environment variables and restart the PC");
                  throw new RuntimeException("Failed to instantiate a WebDriver instance for " + browserName);
               }
               else
               {
                  System.setProperty("webdriver.ie.driver", sysEnv);
               }
            }
            capability = DesiredCapabilities.internetExplorer();
            capability.setCapability("ignoreProtectedModeSettings", true);
            String browserVersion = capability.getVersion();
            if (browserVersion != null && browserVersion.equals("10"))
            {
               capability.setPlatform(Platform.WINDOWS);
               capability.setVersion(browserVersion);
            }
            else if (browserVersion != null && browserVersion.equals("11"))
            {
               capability.setPlatform(Platform.WIN8_1);
               capability.setVersion(browserVersion);
            }
            capability.setBrowserName("internet explorer");
            capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            return new InternetExplorerDriver(capability);
         case SAFARI:
            capability = DesiredCapabilities.safari();
            capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capability.setCapability("ensureCleanSession", true);
            capability.setPlatform(capability.getPlatform());
            capability.setVersion(null);
            return new SafariDriver(capability);
         case OPERA_DRIVER:
            capability = DesiredCapabilities.opera();
            capability.setCapability("opera.profile", "/test");
            return new OperaDriver();
         case EDGE:
            capability = DesiredCapabilities.edge();
            EdgeOptions option = new EdgeOptions();
            capability.setCapability("edgeOptions", option);
            return new EdgeDriver(capability);
         default:
            log.info("Currenty support is there for Chrome, Firefox, Firefox Marionette, Internet Explorer, Edge, Safari & Opera. Support is not there for " + browserName);
            capability = DesiredCapabilities.firefox();
            firefoxProfile = new FirefoxProfile();
            firefoxProfile.setAcceptUntrustedCertificates(true);
            firefoxProfile.setEnableNativeEvents(true);
            capability.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
            capability.setPlatform(capability.getPlatform());
            capability.setVersion(capability.getVersion());
            return new FirefoxDriver(capability);
      }
   }
   /**
    * This method holds the data in order to configure proper testing environment
    *
    */
   private enum BrowserType
   {
    MARIONETTE("firefoxm"),
    FIREFOX_DRIVER("firefox"),
    CHROME_DRIVER("chrome"),
    SAFARI("safari"),
    INTERNET_EXPLORER("internetexplorer"),
    OPERA_DRIVER("opera"),
    EDGE("edge");
      private BrowserType(String stringName)
      {
         this.setBrowserName(stringName);
      }


      private static BrowserType getBrowserTypeFromString(String stringName)
      {
         String a = stringName.toLowerCase().replaceAll(" ", "").trim();
         if ((a.equals("ff")) || (a.equals("firefox")) || (a.startsWith("firefoxdriver")))
         {
            return BrowserType.FIREFOX_DRIVER;
         }
         else if ((a.equals("ffm")) || (a.equals("firefoxm")) || (a.contains("marionette")) || (a.equals("firefoxmarionette")))
         {
            return BrowserType.MARIONETTE;
         }
         else if ((a.equals("chrome")) || (a.equals("chromedriver")) || (a.equals("googlechrome")))
         {
            return BrowserType.CHROME_DRIVER;
         }
         else if ((a.equals("internetexplorer")) || (a.equals("ie")) || (a.equals("internet_explorer")) || (a.startsWith("ie")))
         {
            return BrowserType.INTERNET_EXPLORER;
         }
         else if (a.equals("safari"))
         {
            return BrowserType.SAFARI;
         }
         else if (a.equals("opera"))
         {
            return BrowserType.OPERA_DRIVER;
         }
         else if (a.equals("edge") || (a.contains("microsoftedge")))
         {
            return BrowserType.EDGE;
         }
         else
         {
            return BrowserType.FIREFOX_DRIVER;
         }
      }


      private void setBrowserName(String stringName)
      {}
   }


   /**
    * This method build the URL in defined browser and it will maximize the browser.
    *
    * @param URL - URL need to open
    */
   public void get(String url)
   {
      webDriver.get(url);
   }


   /**
    * This method will delete all cookies from the current instance.
    */
   public void deleteAllCookies()
   {
      webDriver.manage().deleteAllCookies();
   }


   /**
    * This method will add cookies in the current running instance.
    *
    * @param name - cookie name
    * @param value - encoded value
    */
   public void addCookies(String name, String value)
   {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.DATE, 1);
      Date expiry = c.getTime();
      Cookie ck = new Cookie(name, value, "/", expiry);
      webDriver.manage().deleteAllCookies();
      webDriver.manage().addCookie(ck);
      log.info("Added Cookie " + ck);
   }


   /**
    * This method will clicks OK on a pop up alert
    */
   public void acceptAlert()
   {
      try
      {
         log.info("Accepting Alert ...");
         Alert a = webDriver.switchTo().alert();
         String text = a.getText();
         a.accept();
         log.info("Alert Text: " + text);
      } catch (Exception e)
      {}
   }


   /**
    * This method will switches to Alert context and dismisses if found
    */
   public void dismissAlert()
   {
      try
      {
         Alert a = webDriver.switchTo().alert();
         String text = a.getText();
         a.dismiss();
         log.info("Dismissing alert: " + text);
      } catch (Exception e)
      {}
   }


   public void setIEURLAuthentication(String userName, String password, String url)
   {
      if (getBrowserName().toLowerCase().contains("internet explorer"))
      {
         for (int i = 1; i < 5; i++)
         {
            delay(5);
            if (isAlertPresent())
            {
               delay(2);
               break;
            }
            else
            {
               reload();
            }
         }
         Alert alert = getWebDriver().switchTo().alert();
         delay(2);
         try
         {
            alert.authenticateUsing(new UserAndPassword(userName, password));
         } catch (Exception e)
         {
            setUserPassword(userName, password);
            delay(2);
         }
      }
   }


   public String setURLAuthentication(String userName, String password, String url)
   {
      String reg = null;
      if ( !getBrowserName().toLowerCase().contains("internet explorer"))
      {
         if (url.startsWith("http://"))
         {
            reg = "http://";
         }
         else if (url.startsWith("https://"))
         {
            reg = "https://";
         }
         if (userName == "" && password == "")
         {
            return url;
         }
         else
         {
            return reg + userName + ":" + password + "@" + url.replace(reg, "");
         }
      }
      else
      {
         return url;
      }
   }


   protected void setUserPassword(String userName, String password)
   {
      Robot rb;
      try
      {
         rb = new Robot();
         StringSelection username = new StringSelection(userName);
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(username, null);
         rb.keyPress(KeyEvent.VK_CONTROL);
         rb.keyPress(KeyEvent.VK_V);
         rb.keyRelease(KeyEvent.VK_CONTROL);
         rb.keyRelease(KeyEvent.VK_V);
         rb.keyPress(KeyEvent.VK_TAB);
         rb.keyRelease(KeyEvent.VK_TAB);
         delay(1);
         StringSelection pwd = new StringSelection(password);
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd, null);
         rb.keyPress(KeyEvent.VK_CONTROL);
         rb.keyPress(KeyEvent.VK_V);
         rb.keyRelease(KeyEvent.VK_CONTROL);
         rb.keyRelease(KeyEvent.VK_V);
         rb.keyPress(KeyEvent.VK_ENTER);
         rb.keyRelease(KeyEvent.VK_ENTER);
      } catch (AWTException e)
      {}
   }


   /**
    * This method will switch to the alert of current instance.
    */
   public boolean switchToAlert()
   {
      try
      {
         webDriver.switchTo().alert();
         return true;
      } catch (Exception e)
      {
         return false;
      }
   }


   /**
    * This method will Check/Click a toggle-button (check box/radio)
    */
   public void check(String locator)
   {
      WebElement e = findElement(locator);
      if ( !e.isSelected())
      {
         e.click();
      }
   }


   /**
    * This method will Un-check a toggle-button (check box/radio)
    */
   public void uncheck(String locator)
   {
      WebElement e = findElement(locator);
      if (e.isSelected())
      {
         e.click();
      }
   }


   /**
    * This method uses the Selenium webDriver object to click on the element; waits 10 seconds to find
    *
    * @param locator - the element the user want to click
    */
   public void click(String locator)
   {
      click(locator, TEN);
   }


   /**
    * This method uses the Selenium webDriver object to click on the element; waits timeout seconds to find
    *
    * @param locator - the element the user want to click
    * @param timeout in seconds
    */
   public void click(String locator, int timeOut)
   {
      log.debug("Clicking: " + locator);
      findElement(locator, timeOut).click();
   }


   /**
    * This method uses the action to click on a webelement
    *
    * @param locator - the element the user want to click
    */
   public void clickByAction(String locator)
   {
      Actions action = new Actions(webDriver);
      action.click(findElement(locator));
   }


   /**
    * This method uses the Selenium webDriver object to click on the element using javascript
    *
    * @param locator - the element the user want to click
    */
   public void clickUsingJavascript(String locator)
   {
      WebElement we = findElement(locator);
      String event = "arguments[0].click()";
      JavascriptExecutor executor = (JavascriptExecutor)webDriver;
      try
      {
         we.click();
      } catch (Exception e)
      {
         executor.executeScript(event, we);
      }
   }


   /**
    * This method uses action to doubleClick on the element
    *
    * @param locator - the element the user want to click
    */
   public void doubleClick(String locator)
   {
      WebElement element = elementToBeClickable(locator);
      Actions action = new Actions(webDriver);
      try
      {
         action.doubleClick(element);
         action.perform();
      } catch (Exception e)
      {
         log.info("Could not double click : " + e);
      }
   }


   /**
    * This method will clear the value if this element is a text entry element.
    * Text entry elements are INPUT and TEXTAREA elements.
    *
    * @param locator - input field to clear
    */
   public void clearField(String locator)
   {
      WebElement e = findElement(locator);
      e.clear();
   }


   /**
    * This method will Close the current window, quitting the browser if it's the last window currently open.
    */
   public void close()
   {
      if (webDriver != null)
      {
         webDriver.close();
      }
   }


   /**
    * This method will quitting the currently opened browser.
    */
   public void quit()
   {
      if (webDriver != null)
      {
         log.info("Disposing the WebDriver....");
         webDriver.quit();
      }
   }


   /**
    * This method will Switches to any child windows and closes them and then switches back to the parent window.
    */
   public void closeAllChildWindows()
   {
      Set<String> winHandles = webDriver.getWindowHandles();
      String handle = null;
      for (int i = winHandles.size(); i > 1; i--)
      {
         handle = (String)winHandles.toArray()[winHandles.size() - 1];
         webDriver.switchTo().window(handle).close();
      }
      switchToParentWindow();
   }


   /**
    * This method will blind wait (this method is useful while running in grid)
    *
    * @param seconds - seconds to wait
    */
   public void delay(long seconds)
   {
      try
      {
         TimeUnit.SECONDS.sleep(seconds);
      } catch (InterruptedException e)
      {}
   }


   /**
    * This method returns an element which is clickable by the Selenium webDriver; waits 5 seconds to find
    *
    * @param locator - element the user wants to click
    * @return WebElement - clickable element
    */
   public WebElement elementToBeClickable(final String locator)
   {
      WebDriverWait wait = new WebDriverWait(webDriver, FIVE);
      return wait.until(ExpectedConditions.elementToBeClickable(getSelector(locator)));
   }


   /**
    * This method returns a web element specified by locator; waits 5 seconds to find
    *
    * @param locator - element to return by various locator types (Ex. "css=div.name", xpath=//title, id=name)
    * @return WebElement - The first matching element on the current page
    */
   public WebElement findElement(String locator)
   {
      return findElement(locator, FIVE);
   }


   /**
    * This method returns a web element specified by locator
    *
    * @param locator - element to return by various locator types (Ex. "css=div.name", xpath=//title, id=name)
    * @return WebElement - The first matching element on the current page
    */
   public WebElement findElement(String locator, int timeOut)
   {
      log.debug("debug: Looking for element: " + locator);
      WebElement e = (new WebDriverWait(webDriver, timeOut)).until(ExpectedConditions.presenceOfElementLocated(getSelector(locator)));
      return e;
   }


   /**
    * This method returns all the elements specified by locator
    *
    * @param locator - element to return by various locator types (Ex. "css=div.name", xpath=//title, id=name)
    * @return List<WebElement> - all matching elements on the current page
    */
   public List<WebElement> findElements(String locator)
   {
      return webDriver.findElements(getSelector(locator));
   }


   /**
    * This method returns all inner texts contain by the locator
    *
    * @param locator - element that contains the texts you are looking for
    * @return List<String> - all text within the matching element
    */
   public List<String> findTextElements(String locator)
   {
      List<String> toReturn = new ArrayList<String>();
      for (WebElement w : webDriver.findElements(getSelector(locator)))
      {
         toReturn.add(w.getText());
      }
      return toReturn;
   }


   /**
    * This method will Fires a javascript event
    *
    * @param script - javascript to fire
    */
   public void fireJsEvent(String script)
   {
      ((JavascriptExecutor)webDriver).executeScript(script);
   }


   /**
    * This method will Fires a javascript event
    *
    * @param script - javascript to fire
    */
   public void fireJsEvent(String locator, String event)
   {
      String newLocator = locator.replace(CSS_PREFIX, "");
      String script = "$('" + newLocator + "')." + event + "();";
      ((JavascriptExecutor)webDriver).executeScript(script);
   }


   /**
    * This method will Fires a javascript event
    *
    * @param script - javascript to fire
    */
   public void fireJsEvent(WebElement element, String event)
   {
      String exec = event;
      if (getBrowserName().toLowerCase().contains("internet"))
      {
         exec = "on" + event;
      }
      ((JavascriptExecutor)webDriver).executeScript(exec, element);
   }


   /**
    * This method will Move the focus to the specified element; for example, if the element is an input field, move the
    * cursor
    * to that field.
    *
    * @param locator - the element the user want to focus
    */
   public void focus(String locator)
   {
      findElement(locator).sendKeys(Keys.TAB);
   }


   /**
    * Retrieves the message of a JavaScript alert generated during the previous action, or fail if there were
    * no alerts.
    *
    * <p>
    * Getting an alert has the same effect as manually clicking OK. If an alert is generated but you do not consume it
    * with getAlert, the next Selenium action will fail.
    * </p>
    * <p>
    * Under Selenium, JavaScript alerts will NOT pop up a visible alert dialog.
    * </p>
    * <p>
    * Selenium does NOT support JavaScript alerts that are generated in a page's onload() event handler. In this case a
    * visible dialog WILL be generated and Selenium will hang until someone manually clicks OK.
    * </p>
    *
    * @return The message of the most recent JavaScript alert
    */
   public String getAlert()
   {
      return webDriver.switchTo().alert().getText();
   }


   /**
    * This method Uses an xpath locator with an iterator ( [%s] ) and returns all text or attributes found using the
    * locator
    *
    * @param xpathLocator locator that contains the '%s' replacement character
    * @return a list of either text elements or attribute values found using the locator
    */
   public List<String> getAll(String xpathLocator)
   {
      ArrayList<String> ret = new ArrayList<String>();
      String attrName = "name";
      int row = 1;
      while (true)
      {
         try
         {
            String mod = String.format(xpathLocator, row++);
            try
            {
               String attr = webDriver.findElement(By.xpath(mod)).getAttribute(attrName);
               ret.add(attr);
            } catch (Exception e)
            {
               String text = this.getText(mod);
               ret.add(text);
            }
         } catch (Exception e)
         {
            log.debug("" + e);
            break;
         }
      }
      return ret;
   }


   /**
    * This method Uses the xpath locator and return all text returned by the locator;
    *
    * @param xpathLocator String - locator that meets all the requirements
    * @return List< String> a list of String value found using the locator
    */
   public List<String> getAllText(String xpathLocator)
   {
      List<String> textList = new ArrayList<String>();
      List<WebElement> elements = webDriver.findElements(getSelector(xpathLocator));
      for (WebElement element : elements)
      {
         try
         {
            textList.add(element.getText());
         } catch (Exception e)
         {}
      }
      return textList;
   }


   /**
    * Gets the value of an element attribute. The value of the attribute may differ across browsers (this is the case
    * for the "style" attribute, for example).
    *
    * @param locator an element locator followed by an @ sign and then the name of the attribute, e.g. "foo@bar"
    * @return the value of the specified attribute
    */
   public String getAttribute(String locator, String attribute)
   {
      return findElement(locator).getAttribute(attribute);
   }


   /**
    * This method gets the text of the web page
    *
    * @return String - text of the page
    */
   public String getBodyText()
   {
      return webDriver.findElement(By.tagName("body")).getText();
   }


   /**
    * This method returns the name of the currently used webdriver (Ex. Chrome, Firefox..)
    *
    * @return String - driver name for a particular web browser
    */
   public String getBrowserName()
   {
      Capabilities cap = ((RemoteWebDriver)webDriver).getCapabilities();
      return cap.getBrowserName().toString();
   }


   /**
    * @return browser information
    */
   public String getBrowserVersion()
   {
      Capabilities cap = ((RemoteWebDriver)webDriver).getCapabilities();
      return cap.getVersion().toString();
   }


   /**
    * This method returns the platform. (Ex. Windows, Mac..)
    *
    * @return Platform - system platform
    */
   public String getPlatform()
   {
      Capabilities cap = ((RemoteWebDriver)webDriver).getCapabilities();
      return cap.getPlatform().toString();
   }


   /**
    * @return browser version
    */
   public String getCurrentBrowserVersion()
   {
      String browserInfo = null;
      browserInfo = (String)((JavascriptExecutor)webDriver).executeScript("return navigator.platform");
      browserInfo += ", ";
      browserInfo += (String)((JavascriptExecutor)webDriver).executeScript("return navigator.userAgent");
      return browserInfo;
   }


   /**
    * Retrieves the message of a JavaScript confirmation dialog generated during the previous action.
    *
    * <p>
    * By default, the confirm function will return true, having the same effect as manually clicking OK. This can be
    * changed by prior execution of the chooseCancelOnNextConfirmation command.
    * </p>
    * <p>
    * If an confirmation is generated but you do not consume it with getConfirmation, the next Selenium action will
    * fail.
    * </p>
    * <p>
    * NOTE: under Selenium, JavaScript confirmations will NOT pop up a visible dialog.
    * </p>
    * <p>
    * NOTE: Selenium does NOT support JavaScript confirmations that are generated in a page's onload() event handler. In
    * this case a visible dialog WILL be generated and Selenium will hang until you manually click OK.
    * </p>
    *
    * @return the message of the most recent JavaScript confirmation dialog
    */
   public String getConfirmation()
   {
      return webDriver.switchTo().alert().getText();
   }


   /**
    * Gets option label (visible text) for selected option in the specified select element.
    *
    * @param selectLocator an <a href="#locators">element locator</a> identifying a drop-down menu
    * @return the selected option label in the specified select drop-down
    */
   public String getSelectedLabel(String selectLocator)
   {
      return new Select(findElement(selectLocator)).getFirstSelectedOption().getText();
   }


   /**
    * Gets all option labels (visible text) for selected options in the specified select or multi-select
    * element.
    *
    * @param selectLocator an <a href="#locators">element locator</a> identifying a drop-down menu
    * @return an array of all selected option labels in the specified select drop-down
    */
   public String[] getSelectedLabels(String selectLocator)
   {
      List<WebElement> options = new Select(findElement(selectLocator)).getAllSelectedOptions();
      String[] retArr = new String[options.size()];
      for (int i = 0; i < options.size(); i++)
      {
         retArr[i] = options.get(i).getText();
      }
      return retArr;
   }


   /**
    * This method returns a list of options from a select box
    *
    * @param locator - select box
    * @return List<WebElement> - options of the select box
    */
   public List<WebElement> getSelectOptions(String selectLocator)
   {
      Select select = new Select(findElement(selectLocator, TEN));
      return select.getOptions();
   }


   /**
    * This method will returns the selector type that need to use
    *
    * @param locator - locater type
    */
   private By getSelector(String locator)
   {
      String[] prefix = locator.split("=", 2);
      if (prefix[0].toLowerCase().equals("css"))
      {
         return By.cssSelector(prefix[1]);
      }
      else if (prefix[0].equals("id"))
      {
         return By.id(prefix[1]);
      }
      else if (prefix[0].equals("class"))
      {
         return By.className(prefix[1]);
      }
      else if (prefix[0].equals("xpath"))
      {
         return By.xpath(prefix[1]);
      }
      else if (prefix[0].equals("link"))
      {
         return By.linkText(prefix[1]);
      }
      else if (prefix[0].equals("name"))
      {
         return By.name(prefix[1]);
      }
      else if (locator.startsWith("//"))
      {
         return By.xpath(locator);
      }
      else if (locator.startsWith(".//"))
      {
         return By.xpath(locator);
      }
      else
      {
         return By.id(locator);
      }
   }


   /**
    * Gets the text of an element. This works for any element that contains text. This command uses either the
    * textContent (Mozilla-like browsers) or the innerText (IE-like browsers) of the element, which is the
    * rendered text shown to the user.
    *
    * @param locator an <a href="#locators">element locator</a>
    * @return the text of the element
    */
   public String getText(String locator)
   {
      try
      {
         return findElement(locator).getText();
      } catch (StaleElementReferenceException e)
      {
         return findElement(locator).getText();
      }
   }


   /**
    * Gets the title of the current page.
    *
    * @return the title of the current page
    */
   public String getTitle()
   {
      return webDriver.getTitle();
   }


   /**
    * Gets the (whitespace-trimmed) value of an input field (or anything else with a value parameter). For
    * checkbox/radio elements, the value will be "on" or "off" depending on whether the element is checked or
    * not.
    *
    * @param locator an <a href="#locators">element locator</a>
    * @return the element value, or "on/off" for checkbox/radio elements
    */
   public String getValue(String locator)
   {
      return findElement(locator).getAttribute(VALUE);
   }


   /**
    * Gets the webDriver type.
    *
    * @return the webDriver
    */
   public WebDriver getWebDriver()
   {
      return webDriver;
   }


   /**
    * Get the current browser's current running instance url
    *
    * @return String with current website url
    */
   public String getCurrentUrl()
   {
      return webDriver.getCurrentUrl();
   }


   /**
    * Returns the number of nodes that match the specified css/xpath, eg. "css=table" / "//table" would give the number
    * of tables.
    *
    * @param css/xpath the css/xpath expression to evaluate. do NOT wrap this expression in a 'count()' function; we
    *           will do that for you.
    * @return the integer value of nodes that match the specified locater
    */
   public int getElementsCount(String locator)
   {
      return webDriver.findElements(getSelector(locator)).size();
   }


   /**
    * This method uses the webDriver object to go back to previous page
    *
    */
   public void goBack()
   {
      if (getBrowserName().equals(BrowserType.SAFARI))
      {
         log.warn("This may not work in SAFARI due to known bug between Safari and webdriver. work around is to save the url of the previous page");
         jsGoBack();
      }
      else
      {
         webDriver.navigate().back();
      }
   }


   public void goForward()
   {
      if (getBrowserName().equals(BrowserType.SAFARI))
      {
         log.warn("regular webdriver navigate forward known bug between Safari and webdriver. using workaround java script command");
         jsGoForward();
      }
      else
      {
         this.webDriver.navigate().forward();
      }
   }


   public void jsGoBack()
   {
      ((JavascriptExecutor)this.webDriver).executeScript("history.back", new Object[0]);
   }


   public void jsGoForward()
   {
      ((JavascriptExecutor)this.webDriver).executeScript("history.go(-1)", new Object[0]);
   }


   /**
    * This method scrolls to the top of the web page.
    */
   public void goToTop()
   {
      scrollVertical(1, TEN);
   }


   /**
    * This method uses JavaScript to hide a given element
    *
    * @param locator - the element the user wants to hide
    */
   public void hideElement(String locator)
   {
      String newlocator = locator.replace(CSS_PREFIX, "");
      String script = "$('" + newlocator + "').style.visibility = 'hidden'";
      ((JavascriptExecutor)webDriver).executeScript(script);
   }


   /**
    * This method returns true if check box is checked, otherwise false
    *
    * @param locator - check box
    * @return boolean - true if checked, false otherwise
    */
   public boolean isChecked(String locator)
   {
      WebElement e = findElement(locator, 1);
      return e.isSelected();
   }


   /**
    * This method checks if a given element is present on web page
    *
    * @param locator - element the user wants to check existence of
    * @return boolean - returns true if element is present, false otherwise
    */
   public boolean isElementPresent(String locator)
   {
      return (findElements(locator).size() > 0);
   }


   /**
    * This methods waits ten seconds for the locator to appear if it doesn't appear by the end of the ten
    * seconds then it returns false
    *
    * @param locator - element the user wants to check existence of
    * @return boolean - returns true if element is present, false otherwise
    */
   public boolean isElementPresentWithWait(String locator)
   {
      return isElementPresentWithWait(locator, TEN);
   }


   /**
    * This methods waits ten seconds for the locator to appear if it doesn't appear by the end of the ten
    * seconds then it returns false
    *
    * @param locator - element the user wants to check existence of
    * @return boolean - returns true if element is present, false otherwise
    */
   public boolean isElementPresentWithWait(String locator, int timeout)
   {
      for (int i = 0; i < timeout; i++)
      {
         if (isElementPresent(locator))
         {
            return true;
         }
         delay(1);
      }
      return false;
   }


   /**
    * Determines if the specified element is visible. An element can be rendered invisible by setting the CSS
    * "visibility" property to "hidden", or the "display" property to "none", either for the element itself or
    * one if its ancestors. This method will fail if the element is not present.
    *
    * @param locator an <a href="#locators">element locator</a>
    * @param timeout int time value
    * @return true if the specified element is visible, false otherwise
    */
   public boolean isElementVisibleWithWait(String locator, int timeout)
   {
      try
      {
         (new WebDriverWait(webDriver, timeout)).until(ExpectedConditions.visibilityOfElementLocated(getSelector(locator)));
         return true;
      } catch (Exception e1)
      {
         return false;
      }
   }


   /**
    * Determines if the specified element is visible. An element can be rendered invisible by setting the CSS
    * "visibility" property to "hidden", or the "display" property to "none", either for the element itself or
    * one if its ancestors. This method will fail if the element is not present.
    *
    * @param locator an <a href="#locators">element locator</a>
    * @return true if the specified element is visible, false otherwise
    */
   public boolean isElementVisible(String locator)
   {
      try
      {
         (new WebDriverWait(webDriver, 1)).until(ExpectedConditions.visibilityOfElementLocated(getSelector(locator)));
         return true;
      } catch (Exception e1)
      {
         return false;
      }
   }


   /**
    * Is the element currently enabled or not? This will generally return true for everything but disabled
    * input elements.
    *
    * @return True if the element is enabled, false otherwise.
    */
   public boolean isEnabled(String locator)
   {
      return findElement(locator, 1).isEnabled();
   }


   /**
    * Verifies that the specified text pattern appears somewhere on the rendered page shown to the user.
    *
    * @param pattern a <a href="#patterns">pattern</a> to match with the text of the page
    * @return true if the pattern matches the text, false otherwise
    */
   public boolean isTextPresent(String pattern)
   {
      String bodyText = getBodyText();
      return bodyText.contains(pattern);
   }


   /**
    * Verifies that the specified text pattern appears in the specified element.
    *
    * @param pattern a <a href="#patterns">pattern</a> to match with the text of the element
    * @param locator - specifies the element where to look for text
    * @return true if the pattern matches the text, false otherwise
    */
   public boolean isTextElementPresent(String locator, String pattern)
   {
      String bodyText = findElement(locator).getText();
      return bodyText.contains(pattern);
   }


   /**
    * This method fires a javascript jquery click() event
    *
    * @param locator - element the user wants to fire the click event against
    */
   public void jsClick(String locator)
   {
      String newLocator = locator.replace(CSS_PREFIX, "");
      ((JavascriptExecutor)webDriver).executeScript("$('" + newLocator + "').click();");
   }


   /**
    * This method fires a javascript jquery click() event
    *
    * @param locator - element the user wants to fire the click event against
    */
   public void jsClick(WebElement locator)
   {
      ((JavascriptExecutor)webDriver).executeScript("onclick", locator);
   }


   /**
    * Simulates a user pressing and releasing a key.
    *
    * @param locator an <a href="#locators">element locator</a>
    * @param keySequence Either be a string(
    *           "\" followed by the numeric keycode of the key to be pressed, normally the ASCII value of that key), or
    *           a single character. For example: "
    *           w", "\119".
    */
   public void keyPress(String locator, String key)
   {
      findElement(locator).sendKeys(key);
   }


   /**
    * This method fires a javascript jquery show() event
    *
    * @param locator - element the user wants to fire the show event against
    */
   public void makeElementVisible(String locator)
   {
      WebElement elem = findElement(locator);
      String js = "arguments[0].style.height='1'; arguments[0].style.visibility='visible'; arguments[0].style.display='block';";
      ((JavascriptExecutor)webDriver).executeScript(js, elem);
   }


   /**
    * This method uses the webDriver object to mouse out of desired element
    *
    * @param locator - element the user wants to mouse out
    */
   public void mouseOut(String locator)
   {
      String newLocator = locator.replace(CSS_PREFIX, "");
      ((JavascriptExecutor)webDriver).executeScript("$('" + newLocator + "').mouseout();");
   }


   /**
    * This method uses the webDriver object to mouse Over By Action of desired element
    *
    * @param locator - element the user wants to mouse over
    */
   public Actions mouseOverByActions(String locator)
   {
      Actions action = new Actions(webDriver);
      action.moveToElement(findElement(locator));
      return action;
   }


   /**
    * This method uses the webDriver object to mouse Over By Action of desired element
    *
    * @param locator - webElement the user wants to mouse over
    */
   public Actions mouseOverByActions(WebElement locator)
   {
      Actions action = new Actions(webDriver);
      action.moveToElement(locator);
      return action;
   }


   /**
    * This method uses the webDriver object to mouse over the desired element using JavaScript
    *
    * @param locator - element the user wants to mouse over must be of css locator type
    */
   public void mouseOverByJavascript(String locator)
   {
      String newLocator = locator.replace(CSS_PREFIX, "");
      ((JavascriptExecutor)webDriver).executeScript("$('" + newLocator + "').mouseover();");
   }


   /**
    * This method uses the webDriver object to mouse over the desired element using JavaScript
    *
    * @param locator - element the user wants to mouse over must be of css locator type
    */
   public void mouseOverByJavascript(WebElement locator)
   {
      ((JavascriptExecutor)webDriver).executeScript("mouseover()", locator);
   }


   /**
    * This method uses the webDriver object to move over the desired element and click using action class
    *
    * @param locator
    */
   public void moveToAndClick(String locator)
   {
      log.info("Moving to and Clicking: " + locator);
      Actions action = new Actions(webDriver);
      action.moveToElement(findElement(locator)).click().build().perform();
   }


   /**
    * This method uses the webDriver object to move over the desired element and click using action class
    *
    * @param locator
    */
   public void moveToAndClick(WebElement locator)
   {
      log.info("Moving to and Clicking: " + locator);
      Actions action = new Actions(webDriver);
      action.moveToElement(locator).click().build().perform();
   }


   /**
    * This method uses the webDriver object to move over the desired child element and click using action class
    *
    * @param locator
    */
   public void moveToElementAndClick(String... locatores)
   {
      Actions action = new Actions(webDriver);
      String locator = null, bLocator = null;
      for (String b : locatores)
      {
         locator = b;
         delay(3);
         if ( !isElementVisible(locator))
         {
            action.moveToElement(findElement(bLocator)).build().perform();
            delay(3);
         }
         action.moveToElement(findElement(locator, 20)).build().perform();
         bLocator = locator;
      }
      log.info("Moving to and Clicking: " + locator);
      try
      {
         action.moveToElement(findElement(locator)).click().build().perform();
      } catch (Exception e)
      {
         click(locator);
      }
   }


   public void dragAndDropAnElement(WebElement findElement, WebElement destination)
   {
      new Actions(this.webDriver);
      Actions dragdrop = new Actions(this.webDriver);
      try
      {
         dragdrop.clickAndHold(findElement).moveToElement(destination).release(destination).build().perform();
      } catch (StaleElementReferenceException se)
      {
         log.error("Stale element:", se);
      } catch (NoSuchElementException ne)
      {
         log.error("no such element: source: {}, destination:{}", findElement, destination);
         log.error("message: {}", ne);
      } catch (Exception e)
      {
         log.error("unexpected error attempting to drag and drop", e);
      }
   }


   /**
    * This method uses the webDriver object to drag and drop the elements from source to destination
    *
    * @param locator
    */
   public void dragAndDropElement(String sourceElement, String destinationElement, int timeoutInSeconds)
   {
      Actions builder = new Actions(getWebDriver());
      WebElement sElement = findElement(sourceElement, timeoutInSeconds);
      WebElement dElement = findElement(destinationElement, timeoutInSeconds);
      builder.clickAndHold(sElement).moveToElement(dElement).release(dElement).build().perform();
   }


   /**
    * This method uses the webDriver object to drag and drop the elements from source to destination using javascript
    *
    * @param locator
    */
   public void dragAndDropElementUsingJavascript(String sourceElement, String destinationElement, int timeoutInSeconds)
   {
      Actions builder = new Actions(getWebDriver());
      WebElement src = findElement(sourceElement, timeoutInSeconds);
      WebElement des = findElement(destinationElement, timeoutInSeconds);
      String xto = Integer.toString(src.getLocation().x);
      String yto = Integer.toString(des.getLocation().y);
      JavascriptExecutor executor = (JavascriptExecutor)webDriver;
      String event = "function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " + "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ";
      try
      {
         src.click();
         des.click();
         builder.dragAndDrop(src, des);
      } catch (Exception e)
      {
         executor.executeScript(event, src, xto, yto);
      }
   }


   /**
    * This method uses the webDriver object to open the url
    *
    * @param url - user defined url will open
    */
   public void navigateTo(String url)
   {
      log.info("Navigating to: " + url);
      webDriver.navigate().to(url);
   }


   /**
    * This method overrides the IE Browser certificate error
    */
   public void overrideIECertificateError()
   {
      String CONTINUE = "css=body.securityError";
      if (isElementVisibleWithWait(CONTINUE, 10))
      {
         try
         {
            log.info("Security Warning Present ...");
            webDriver.get("javascript:document.getElementById('overridelink').click()");
            log.info("Skipping Certificate Warning !!! ...");
         } catch (WebDriverException e)
         {
            webDriver.navigate().to("javascript:document.getElementById('overridelink').click()");
            log.info("Skipping Certificate Warning !!! ...");
         }
      }
   }


   /**
    * This method overrides the Edge Browser certificate error
    */
   public void overrideEdgeCertificateError()
   {
      String CONTINUE = "css=id#invalidcert_continue";
      if (isElementVisibleWithWait(CONTINUE, 10))
      {
         try
         {
            log.info("Security Warning Present ...");
            click(CONTINUE);
            log.info("Skipping Certificate Warning !!! ...");
         } catch (Exception e)
         {
            log.info("Certificate Warning not Present");
         }
      }
   }


   /**
    * This method uses the webDriver object to refresh the page and waits up to 10s
    */
   public void reload()
   {
      webDriver.navigate().refresh();
   }


   /**
    * Scrolls the window horizontally
    *
    * @param xlocation - how far to scroll the window
    * @param scrollFrequency - scroll distance in pixels per one scroll click
    */
   public void scrollHorizontal(int xlocation, int scrollFrequency)
   {
      String jscript = "window.scrollTo(" + xlocation + ",0);";
      for (int i = 0; i < xlocation; i += scrollFrequency)
      {
         ((JavascriptExecutor)webDriver).executeScript(jscript);
      }
   }


   /**
    * Scrolls the window vertically
    *
    * @param ylocation - how far to scroll the window
    * @param scrollFrequency - scroll distance in pixels per one scroll click
    */
   public void scrollVertical(int ylocation, int scrollFrequency)
   {
      String jscript = "window.scrollTo(0," + ylocation + ");";
      for (int i = 0; i < ylocation; i += scrollFrequency)
      {
         ((JavascriptExecutor)webDriver).executeScript(jscript);
      }
   }


   public void scrollToEnd()
   {
      JavascriptExecutor js = (JavascriptExecutor)this.webDriver;
      js.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));", new Object[0]);
   }


   public void scrollTotop()
   {
      JavascriptExecutor js = (JavascriptExecutor)this.webDriver;
      js.executeScript("window.scrollTo(0,Math.min(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));", new Object[0]);
   }


   public void scroll(String divToScrollPath, String scrollDirection)
   {
      click(divToScrollPath);
      if (scrollDirection.toLowerCase().equals("up"))
      {
         robotScrollUp();
      }
      else if (scrollDirection.toLowerCase().equals("down"))
      {
         robotScrollDown();
      }
      else if (scrollDirection.toLowerCase().equals("left"))
      {
         robotScrollLeft();
      }
      else if (scrollDirection.toLowerCase().equals("right"))
      {
         robotScrollRight();
      }
      log.info("Scrolled the view");
   }


   private void robotScrollDown()
   {
      Robot robot = null;
      try
      {
         robot = new Robot();
         robot.keyPress(KeyEvent.VK_CONTROL);
         robot.keyPress(KeyEvent.VK_END);
         robot.keyRelease(KeyEvent.VK_CONTROL);
         robot.keyRelease(KeyEvent.VK_END);
      } catch (AWTException e)
      {}
   }


   private void robotScrollUp()
   {
      Robot robot = null;
      try
      {
         robot = new Robot();
         robot.keyPress(KeyEvent.VK_CONTROL);
         robot.keyPress(KeyEvent.VK_UP);
         robot.keyRelease(KeyEvent.VK_CONTROL);
         robot.keyRelease(KeyEvent.VK_UP);
      } catch (AWTException e)
      {}
   }


   private void robotScrollLeft()
   {
      for (int i = 1; i <= 25; i++)
      {
         Robot robot = null;
         try
         {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_LEFT);
         } catch (AWTException e)
         {}
         robot.keyRelease(KeyEvent.VK_LEFT);
      }
   }


   private void robotScrollRight()
   {
      for (int i = 1; i <= 25; i++)
      {
         Robot robot = null;
         try
         {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_RIGHT);
         } catch (AWTException e)
         {}
         robot.keyRelease(KeyEvent.VK_RIGHT);
      }
   }


   /**
    * Select an option from a drop-down using an option locator.
    *
    * <p>
    * Option locators provide different ways of specifying options of an HTML Select element (e.g. for selecting a
    * specific option, or for asserting that the selected option satisfies a specification). There are several forms of
    * Select Option Locator.
    * </p>
    * <ul>
    * <li><strong>label</strong>=<em>labelPattern</em>: matches options based on their labels, i.e. the visible text.
    * (This is the default.)
    * <ul class="first last simple">
    * <li>label=regexp:^[Oo]ther</li>
    * </ul>
    * </li>
    * <li><strong>value</strong>=<em>valuePattern</em>: matches options based on their values.
    * <ul class="first last simple">
    * <li>value=other</li>
    * </ul>
    * </li>
    * <li><strong>id</strong>=<em>id</em>:
    *
    * matches options based on their ids.
    * <ul class="first last simple">
    * <li>id=option1</li>
    * </ul>
    * </li>
    * <li><strong>index</strong>=<em>index</em>: matches an option based on its index (offset from zero).
    * <ul class="first last simple">
    * <li>index=2</li>
    * </ul>
    * </li>
    * </ul>
    * <p>
    * If no option locator prefix is provided, the default behaviour is to match on <strong>label</strong>.
    * </p>
    *
    * @param selectLocator an <a href="#locators">element locator</a> identifying a drop-down menu
    * @param optionLocator an option locator (a label by default)
    */
   public void select(String selectLocator, String optionLocator)
   {
      Select select = new Select(findElement(selectLocator));
      List<WebElement> options = select.getOptions();
      log.debug("options found " + options.size() + " elements");
      String[] locator = optionLocator.split("=");
      log.debug("Locator has " + locator.length + " elements");
      String find;
      if (locator.length > 1)
      {
         find = locator[1];
      }
      else
      {
         find = locator[0];
      }
      log.debug(" find is " + find);
      if (locator[0].contains(VALUE))
      {
         log.debug("checking value");
         for (WebElement we : options)
         {
            log.debug("Printing found options: " + we.getAttribute(VALUE));
            if (we.getAttribute(VALUE).equals(find))
            {
               we.click();
               break;
            }
         }
      }
      else if (locator[0].contains("id"))
      {
         log.debug("checking id");
         for (WebElement we : options)
         {
            log.debug("Printing found options: " + we.getAttribute("id"));
            if (we.getAttribute("id").equals(find))
            {
               we.click();
               break;
            }
         }
      }
      else if (locator[0].contains("index"))
      {
         log.debug("checking index");
         for (WebElement we : options)
         {
            log.debug("Printing found options: " + we.getAttribute("index"));
            if (we.getAttribute("index").equals(find))
            {
               we.click();
               break;
            }
         }
      }
      else
      {
         log.debug("checking text or label, default option");
         boolean flag = false;
         for (WebElement we : options)
         {
            log.debug("Printing found options: " + we.getText());
            if (we.getText().equals(find))
            {
               we.click();
               flag = true;
               break;
            }
         }
         if (flag == false)
         {
            click(optionLocator);
         }
      }
   }


   /**
    * Sees if an option in a select element is disable or not, see select(String selectLocator, String
    * optionLocator) doc for how it finds the option in the select element
    *
    * @param selectLocator an <a href="#locators">element locator</a> identifying a drop-down menu
    * @param optionLocator an option locator (a label by default)
    * @return True is the option is disable, true otherwise
    */
   public boolean isSelectOptionEnable(String selectLocator, String optionLocator)
   {
      Select select = new Select(findElement(selectLocator));
      List<WebElement> options = select.getOptions();
      log.debug("options found " + options.size() + " elements");
      String[] locator = optionLocator.split("=");
      log.debug("Locator has " + locator.length + " elements");
      String find;
      if (locator.length > 1)
      {
         find = locator[1];
      }
      else
      {
         find = locator[0];
      }
      log.debug(" find is " + find);
      if (locator[0].contains(VALUE))
      {
         log.debug("checking value");
         for (WebElement we : options)
         {
            log.debug("Printing found options: " + we.getAttribute(VALUE));
            if (we.getAttribute(VALUE).equals(find))
            {
               return we.isEnabled();
            }
         }
      }
      else if (locator[0].contains("id"))
      {
         log.debug("checking id");
         for (WebElement we : options)
         {
            log.debug("Printing found options: " + we.getAttribute("id"));
            if (we.getAttribute("id").equals(find))
            {
               return we.isEnabled();
            }
         }
      }
      else if (locator[0].contains("index"))
      {
         log.debug("checking index");
         for (WebElement we : options)
         {
            log.debug("Printing found options: " + we.getAttribute("index"));
            if (we.getAttribute("index").equals(find))
            {
               return we.isEnabled();
            }
         }
      }
      else
      {
         log.debug("checking text or label, default option");
         boolean flag = false;
         for (WebElement we : options)
         {
            log.debug("Printing found options: " + we.getText());
            if (we.getText().equals(find))
            {
               flag = true;
               return we.isEnabled();
            }
         }
         if (flag == false)
         {
            return findElement(optionLocator).isEnabled();
         }
      }
      return false;
   }


   /**
    * Select an option from a drop-down using an option locator and tab.
    *
    * @param selectLocator an <a href="#locators">element locator</a> identifying a drop-down menu
    * @param optionLocator an option locator (a label by default)
    */
   public void selectAndTab(String selectLocator, String optionLocator)
   {
      select(selectLocator, optionLocator);
      focus(selectLocator);
   }


   /**
    * This method returns moves the web driver to control a specific frame
    *
    * @param locator - element the user wants to control
    */
   public void selectFrame(String locator)
   {
      webDriver.switchTo().frame(locator);
   }


   /**
    * Selects a popup window using a window locator; once a popup window has been selected, all commands go to
    * that window. To select the main window again, use null as the target.
    *
    * @param windowID the JavaScript window ID of the window to select
    */
   public void selectWindow(String windowID)
   {
      webDriver.switchTo().window(windowID);
      log.info("Switched to " + webDriver.switchTo().window(windowID).getTitle() + " window");
   }


   /**
    * This method will returns the window handle
    */
   public String getWindowHandle()
   {
      return webDriver.getWindowHandle();
   }


   /**
    * Sets the value of an input field, as though you typed it in; like type() but without clearing the input
    * or doing a 'click' (needed for file uploads, since selenium won't work with native OS file browser
    * dialog)
    *
    * <p>
    * Can also be used to set the value of combo boxes, check boxes, etc. In these cases, value should be the value of
    * the option selected, not the visible text.
    * </p>
    *
    * @param locator an <a href="#locators">element locator</a>
    * @param value the value to type
    */
   public void sendKeys(String locator, String value)
   {
      try
      {
         waitForElementFound(locator, FIVE);
         WebElement we = findElement(locator);
         we.sendKeys(value);
      } catch (ElementNotVisibleException e)
      {
         throw new RuntimeException("Unable to send keys to element: " + locator, e);
      }
   }


   /**
    * Selenium can't handle the popup dialog for opening a file. SendKeys can select the patch to the file
    *
    * @param locator is String with the browse button
    * @param filePath is the path to the fileName
    */
   public void sendOpenSaveFile(String locator, String filePath)
   {
      findElement(locator).sendKeys(filePath);
   }


   /**
    * This method checks or uncheck a checkbox depending on its state
    *
    * @param String checkBoxLocator - check box to complete the action against
    * @param boolean isChecked - check box state
    */
   public void setCheckBoxState(String checkBoxLocator, boolean isChecked)
   {
      waitForElementFound(checkBoxLocator);
      if (isChecked(checkBoxLocator) != isChecked)
      {
         click(checkBoxLocator);
      }
   }


   /**
    * This method submits the form on the web page
    *
    * @param locator - input field
    */
   public void submit(String locator)
   {
      findElement(locator).submit();
   }


   /**
    * This method switches the web driver to control a specified frame.
    *
    * @param int index - index of the frame, where 0 is the parent window.
    */
   public TargetLocator switchTo()
   {
      return webDriver.switchTo();
   }


   /**
    * This method switches the web driver to control a specified frame.
    *
    * @param int index - index of the frame, where 0 is the parent window.
    */
   public void switchToFrame(int index)
   {
      webDriver.switchTo().frame(index);
   }


   /**
    * This method switches the web driver to control a specified frame.
    *
    * @param int index - index of the frame, where 0 is the parent window.
    */
   public void switchToFrame(String frameLocator)
   {
      webDriver.switchTo().frame(frameLocator);
   }


   /**
    * This method will Switches the webDriver to a newly opened web browser window.
    */
   public void switchToNewlyOpenedWindow()
   {
      Set<String> winHandles = webDriver.getWindowHandles();
      String handle = (String)winHandles.toArray()[winHandles.size() - 1];
      webDriver.switchTo().window(handle);
   }


   /**
    * This method will Switches the webDriver back to the parent window.
    */
   public void switchToParentWindow()
   {
      Set<String> winHandles = webDriver.getWindowHandles();
      String handle = (String)winHandles.toArray()[0];
      webDriver.switchTo().window(handle);
   }


   /**
    * This method will Switches the webDriver to the default content.
    */
   public void switchToDefaultContent()
   {
      try
      {
         webDriver.switchTo().defaultContent();
      } catch (final WebDriverException e)
      {
         log.error("Unable to switch to default content", e);
      }
   }


   public void switchToNewWindow(String mainWindow)
   {
      Set<String> handles = webDriver.getWindowHandles();
      if (handles.size() > 1)
      {
         handles.remove(mainWindow);
         webDriver.switchTo().window(handles.iterator().next());
      }
   }


   public void closeChildWindow(String mainWindow)
   {
      Set<String> handles = webDriver.getWindowHandles();
      if (handles.size() > 1)
      {
         handles.remove(mainWindow);
         webDriver.switchTo().window(handles.iterator().next());
         webDriver.close();
      }
      webDriver.switchTo().window(mainWindow);
      switchToDefaultContent();
   }


   /**
    * selects the correct item from the given select item in the GUI. If the value is not set (null or "")
    * this method does nothing, else it will try to select the item.<br>
    * <br>
    * If the value does not contain 'label=' it is added automatically.
    */
   public void selectItemFromDropDown(String selectLocator, String value)
   {
      delay(1);
      if (StringUtils.isNotEmpty(value))
      {
         if (value.startsWith("label="))
         {
            value = value.replace("label=", "");
         }
         select(selectLocator, value);
      }
   }


   /**
    * selects an item from a dropdown list that starts with the parameter stringStartsWith
    *
    * @param guiElementLocator - identifies the dropdown list
    * @param stringStartsWith - string to match starts with in elements of the dropdown list
    */
   public void selectItemStartWithFromDropDown(String guiElementLocator, String stringStartsWith)
   {
      waitForDropdownPopulated(guiElementLocator);
      List<WebElement> options = getSelectOptions(guiElementLocator);
      Iterator<WebElement> iter = options.iterator();
      while (iter.hasNext())
      {
         WebElement el = iter.next();
         String opt = el.getText();
         if (opt.toLowerCase().startsWith(stringStartsWith.toLowerCase()))
         {
            selectItemFromDropDown(guiElementLocator, opt);
         }
      }
   }


   private void waitForDropdownPopulated(String guiElementLocator)
   {
      List<WebElement> options = getSelectOptions(guiElementLocator);
      for (int l = 1; l <= 30; l++)
      {
         options = getSelectOptions(guiElementLocator);
         if (options.size() >= 2)
         {
            delay(2);
            break;
         }
         else
         {
            delay(1);
         }
      }
   }


   /**
    * selects the correct item from the given select item in the GUI. If the value is not set (null or "")
    * this method does nothing, else it will try to select the item.<br>
    * <br>
    * If the value does not contain 'label=' it is added automatically.
    */
   public void selectItemFromDropDown(String selectElementLocator, String item, boolean isRequired)
   {
      if (item == null || item.equals(""))
      {
         if (isRequired)
         {
            selectRandomItemFromDropDown(selectElementLocator);
         }
         return;
      }
      try
      {
         select(selectElementLocator, item);
      } catch (final Exception e)
      {
         log.warn(item + " not found in the drop down, finding a good match");
         List<WebElement> options = getSelectOptions(selectElementLocator);
         Iterator<WebElement> iter = options.iterator();
         while (iter.hasNext())
         {
            WebElement el = iter.next();
            String opt = el.getText();
            if (opt.contains(item))
            {
               select(selectElementLocator, opt);
               return;
            }
         }
         selectItemFromDropDown(selectElementLocator, item);
      }
   }


   /**
    * selects an item from a dropdown list that contains the parameter stringLike
    *
    * @param guiElementLocator - identifies the dropdown list
    * @param stringLike - string to match in the elements of the dropdown list
    * @return the text name of the "matching" item selected in the dropdown; empty string if no match found
    */
   public String selectItemsLikeFromDropDown(String elementLocator, String stringLike)
   {
      waitForDropdownPopulated(elementLocator);
      List<WebElement> options = getSelectOptions(elementLocator);
      Iterator<WebElement> iter = options.iterator();
      while (iter.hasNext())
      {
         WebElement el = iter.next();
         String opt = el.getText();
         if (opt.toLowerCase().contains(stringLike.toLowerCase()))
         {
            el.click();
            break;
         }
      }
      log.warn("Returning empty string, no Service name found in dropdown matching " + stringLike);
      return "";
   }


   /**
    * This method will selects a random element in the dropdown list
    *
    * @param selectElementLocator - identified dropdown list
    */
   public void selectRandomItemFromDropDown(String selectElementLocator)
   {
      waitForDropdownPopulated(selectElementLocator);
      List<WebElement> options = getSelectOptions(selectElementLocator);
      Random r = new Random();
      select(selectElementLocator, options.get(r.nextInt(options.size())).getText());
   }


   /**
    * This method will Returns the 1st iframe element found on the page
    *
    * @return iFrame name or ID.
    */
   public String getFirstFrame()
   {
      String nameOrId = null;
      List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));
      if (iframes.size() != 0)
      {
         nameOrId = iframes.get(0).getAttribute("id");
         if (nameOrId == null)
         {
            nameOrId = iframes.get(0).getAttribute("name");
            return nameOrId;
         }
         return nameOrId;
      }
      return null;
   }


   /**
    * Sets the value of an input field, as though you typed it in.
    *
    * <p>
    * Can also be used to set the value of combo boxes, check boxes, etc. In these cases, value should be the value of
    * the option selected, not the visible text.
    * </p>
    *
    * @param locator an <a href="#locators">element locator</a>
    * @param value the value to type
    */
   public void type(String locator, String value)
   {
      WebElement we = findElement(locator);
      try
      {
         we.click();
      } catch (Exception e)
      {}
      we.clear();
      we.sendKeys(value);
   }


   /**
    * Sets the value of an input field, as though you typed it in without clearing
    *
    * <p>
    * Can also be used to set the value of combo boxes, check boxes, etc. In these cases, value should be the value of
    * the option selected, not the visible text.
    * </p>
    *
    * @param locator an <a href="#locators">element locator</a>
    * @param value the value to type
    */
   public void typeWithoutClearing(String locator, String value)
   {
      WebElement we = findElement(locator);
      try
      {
         we.click();
      } catch (Exception e)
      {}
      we.sendKeys(value);
   }


   /**
    * Sets the value of an input field using javascript
    *
    * @param locator an <a href="#locators">element locator</a>
    * @param value the value to type
    */
   public void typeUsingJavascript(String locator, String value)
   {
      WebElement we = this.findElement(locator);
      String event = "arguments[0].value=\"" + value + "\";";
      JavascriptExecutor executor = (JavascriptExecutor)this.webDriver;
      executor.executeScript(event, new Object[] { we });
   }


   /**
    * This method will wait for 10 seconds for an alert
    */
   public void waitForAlert()
   {
      waitForAlert(10000);
   }


   /**
    * This method will wait for the seconds for an alert, that is defined by user
    *
    * @param timeOut in milliseconds
    */
   public void waitForAlert(int timeOut)
   {
      for (int i = 0; i < timeOut; i++)
      {
         try
         {
            String alertText = getAlert();
            if ( !alertText.isEmpty())
            {
               log.debug("Alert: " + alertText);
               return;
            }
         } catch (Exception e)
         {}
         delay(1);
      }
      throw new RuntimeException("Alert not found");
   }


   /**
    * This method Will wait for 10 seconds for the gui element to be displayed on screen.
    *
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws NoSuchElementException if timeout has been reached and GUI Object still not found
    */
   public void waitForElementFound(String guiElementDescription)
   {
      log.debug("debug waitForElementFound looking for " + guiElementDescription);
      waitForElementFound(guiElementDescription, TEN);
   }


   /**
    * This method Will wait for x seconds for the gui element to be displayed on screen.
    *
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws NoSuchElementException if timeout has been reached and GUI Object still not found
    */
   public void waitForElementFound(String guiElementDescription, int timeoutInSeconds)
   {
      String msg = "Wait %ss for [%s] on page [%s]";
      log.debug(String.format(msg, timeoutInSeconds, guiElementDescription, this.getClass()));
      try
      {
         WebElement e = (new WebDriverWait(webDriver, timeoutInSeconds)).until(ExpectedConditions.presenceOfElementLocated(getSelector(guiElementDescription)));
         log.debug("DEBUG: Element found");
         if (e != null)
         {
            return;
         }
         else
         {
            throw new NoSuchElementException(guiElementDescription + " did not load.");
         }
      } catch (TimeoutException toe)
      {
         throw new NoSuchElementException(guiElementDescription + " did not load with TimeoutException " + toe.getMessage());
      }
   }


   /**
    * This method Will wait for 10 seconds for the gui element to be hidden.
    *
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws ElementNotVisibleException if timeout has been reached and GUI Object still found
    */
   public void waitForElementHidden(String guiElementDescription)
   {
      log.debug("Waiting For Element Hidden looking for " + guiElementDescription);
      waitForElementHidden(guiElementDescription, TEN);
   }


   /**
    * This method Will wait for x seconds for the gui element to be hidden.
    *
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws ElementNotVisibleException if timeout has been reached and GUI Object still found
    */
   public void waitForElementHidden(String guiElementDescription, int timeoutInSeconds)
   {
      String msg = "Wait %ss for [%s] on page [%s]";
      log.debug(String.format(msg, timeoutInSeconds, guiElementDescription, this.getClass().getSimpleName()));
      boolean visible = true;
      for (int i = 0; i < timeoutInSeconds; i++)
      {
         visible = isElementVisible(guiElementDescription);
         if ( !visible)
         {
            return;
         }
         delay(1);
      }
      throw new ElementNotVisibleException(guiElementDescription + " is still visible on the Web Page.");
   }


   /**
    * This method Will wait for 10 seconds for the gui element to be removed on screen.
    *
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws NoSuchElementException if timeout has been reached and GUI Object still found
    */
   public void waitForElementNotFound(String guiElementDescription)
   {
      log.debug("Waiting For Element Found looking for " + guiElementDescription);
      waitForElementNotFound(guiElementDescription, TEN);
   }


   /**
    * This method Will wait for x seconds for the gui element to be removed on screen.
    *
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws NoSuchElementException if timeout has been reached and GUI Object still found
    */
   public void waitForElementNotFound(String guiElementDescription, int timeoutInSeconds)
   {
      String msg = "Wait %ss for [%s] on page [%s]";
      log.debug(String.format(msg, timeoutInSeconds, guiElementDescription, this.getClass().getSimpleName()));
      try
      {
         Boolean e = (new WebDriverWait(webDriver, timeoutInSeconds)).until(ExpectedConditions.invisibilityOfElementLocated(getSelector(guiElementDescription)));
         log.debug("Element found");
         if (e == true)
         {
            return;
         }
         else
         {
            throw new NoSuchElementException(guiElementDescription + " is still on the page.");
         }
      } catch (TimeoutException toe)
      {
         return;
      } catch (WebDriverException e)
      {
         return;
      }
   }


   /**
    * This method Will wait for 10 seconds for the gui element to be displayed on screen.
    *
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws ElementNotVisibleException if timeout has been reached and GUI Object still not found
    */
   public void waitForElementVisible(String guiElementDescription)
   {
      log.debug("Waiting For Element Visible looking for " + guiElementDescription);
      waitForElementVisible(guiElementDescription, TEN);
   }


   /**
    * This method Will wait for x seconds for the gui element to be displayed on screen.
    *
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws ElementNotVisibleException if timeout has been reached and GUI Object still not found
    */
   public void waitForElementVisible(String guiElementDescription, int timeoutInSeconds)
   {
      String msg = "Wait %ss for [%s] on page [%s]";
      log.debug("Waiting For Element Visible should time out in " + timeoutInSeconds);
      log.debug(String.format(msg, timeoutInSeconds, guiElementDescription, this.getClass().getSimpleName()));
      WebElement e = (new WebDriverWait(webDriver, timeoutInSeconds)).until(ExpectedConditions.visibilityOfElementLocated(getSelector(guiElementDescription)));
      log.debug("Element found");
      if (e != null)
      {
         return;
      }
      else
      {
         throw new ElementNotVisibleException(guiElementDescription + " did not become visible.");
      }
   }


   /**
    * Add a selection to the set of selected options in a multi-select element using an option locator.
    *
    * @see #doSelect for details of option locators
    * @param locator an <a href="#locators">element locator</a> identifying a multi-select box
    * @param optionLocator an option locator (a label by default)
    */
   public void addSelection(String locator, String optionLocator)
   {
      Select sel = new Select(findElement(locator));
      if (sel.isMultiple())
      {
         sel.selectByVisibleText(optionLocator);
      }
   }


   /**
    * This method Will wait for 10 seconds for the gui element to be displayed on screen.
    *
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws ElementNotVisibleException if timeout has been reached and GUI Object still not found
    */
   public void waitForElementVisible(WebElement element)
   {
      waitForElementVisible(element, TEN);
   }


   /**
    * This method Will wait for x seconds for the GUI element to be displayed on screen.
    *
    * @param timeoutInSeconds - number of seconds to wait
    * @param guiElementDescription ( xpath, id ,etc...)
    * @throws GUIElementNotFoundException if timeout has been reached and GUI Object can not be found.
    *
    */
   public void waitForElementVisible(WebElement element, int timeoutInSeconds)
   {
      WebElement e = null;
      log.debug("Wait for Element Visible should time out in " + timeoutInSeconds);
      e = (new WebDriverWait(webDriver, timeoutInSeconds)).until(ExpectedConditions.visibilityOf(element));
      log.debug("end wait for Element Visible");
      if (e != null)
      {
         return;
      }
      else
      {
         throw new ElementNotVisibleException(element.getTagName() + " did not become visible.");
      }
   }


   /**
    * This method Waits 10 seconds for the input text to be found on a GUI page
    *
    * @param text text to search for on page
    * @return True if text is found; false otherwise
    */
   public boolean waitForText(String text)
   {
      return waitForText(text, TEN);
   }


   /**
    * This method Waits x seconds for the input text to be found on a GUI page
    *
    * @param text text to search for on page
    * @return True if text is found; false otherwise
    */
   public boolean waitForText(String text, int timeOut)
   {
      for (int i = 0; i < timeOut; i++)
      {
         try
         {
            if (isTextPresent(text))
            {
               log.debug("Text " + text + " found.");
               return true;
            }
         } catch (Exception e)
         {}
         delay(1);
      }
      return false;
   }


   /**
    * This method maximizes browser window
    */
   public void windowMaximize()
   {
      webDriver.manage().window().maximize();
   }


   /**
    * This method will return the no of windows opend in the current instance.
    */
   public int getNumberOfOpenWindows()
   {
      return webDriver.getWindowHandles().size();
   }


   /**
    * Throws a SkipException with your string message added; your test method will stop executing at this point
    *
    * @param message appended to the SkipException
    * @throws SkipException with your message appended
    */
   public void skip(String message)
   {
      throw new SkipException(message);
   }


   /**
    * This method will return the current system time in user defined format.
    */
   public String getSystemTime(String timeFormat)
   {
      // SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
      SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
      return sdf.format(new Date());
   }


   /**
    * This method will return the current system date in user defined format.
    */
   public String getSystemDate(String dateFormat)
   {
      // SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
      SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
      return sdf.format(new Date());
   }


   /**
    * This method will return the boolean value of Registry Value Exist or Not
    */
   public boolean doesRegistryValueExist()
   {
      String key = "HKEY_CURRENT_USER\\Software\\Policies\\Google\\Chrome\\ExtensionInstallBlacklist\\1";
      boolean answer;
      answer = WindowsUtils.doesRegistryValueExist(key);
      log.info("{}", answer);
      return answer;
   }


   /**
    * This method will clean the Registry Value
    *
    */
   public void cleanRegistry()
   {
      String key = "HKEY_CURRENT_USER\\Software\\Policies\\Google\\Chrome\\ExtensionInstallBlacklist\\1";
      WindowsUtils.deleteRegistryValue(key);
   }


   /**
    * This method will returns the boolean value if aleart is present
    */
   public boolean isAlertPresent()
   {
      ExpectedCondition<Alert> alert = ExpectedConditions.alertIsPresent();
      if (alert != null)
      {
         webDriver.switchTo().alert();
         return true;
      }
      else
      {
         return false;
      }
   }


   /**
    * This method will delete the files in a folder
    */
   public void deleteFolder(File folderPath)
   {
      File[] files = folderPath.listFiles();
      if (files != null)
      {
         for (File file : files)
         {
            if (file.isDirectory())
            {
               deleteFolder(file);
            }
            else
            {
               file.delete();
            }
         }
      }
      folderPath.delete();
   }


   /**
    * Assert the element is present
    *
    * @param locator
    * @param message - print the elementName which is defined by the user
    */
   public void assertElementExist(String locator)
   {
      Assert.assertTrue(isElementPresentWithWait(locator, 120), "Could not locate " + locator + " on Screen");
      log.info(locator + " Present on the screen");
   }


   /**
    * Assert the expected value equals the actual value
    *
    * @param expectedValue String
    * @param actualValue String
    */
   public void assertTrue(String expectedValue, String actualValue, String message)
   {
      Assert.assertTrue(expectedValue.trim().equals(actualValue.trim()), " Expected Value: " + expectedValue + " Actual Value: " + actualValue);
      log.info("Passed: --> expected { " + expectedValue + " } Matches actual --> { " + actualValue + " }");
   }


   /**
    * Assert the expected value equals the actual value
    *
    * @param expectedValue int
    * @param actualValue int
    * @param message String
    */
   public void assertSame(int expectedValue, int actualValue, String message)
   {
      Assert.assertSame(expectedValue, actualValue, message + " Expected Value: " + expectedValue + " Actual Value: " + actualValue);
      log.info("Passed: --> expected { " + expectedValue + " } Matches actual --> { " + actualValue + " }");
   }


   /**
    * Assert the expected value equals the actual value <br>
    * message is added to describe what is being compared for logs
    *
    * @param expectedValue String
    * @param actualValue String
    * @param message String
    */
   public void assertEquals(String expectedValue, String actualValue, String message)
   {
      Assert.assertEquals(expectedValue.trim(), actualValue.trim(), message);
      log.info("Passed: --> expected { " + expectedValue + " } Equals actual --> { " + actualValue + " }");
   }


   /**
    * Assert the expected value equals the actual value <br>
    * message is added to describe what is being compared for logs
    *
    * @param expectedValue int
    * @param actualValue int
    * @param message String
    */
   public void assertEquals(int expectedValue, int actualValue, String message)
   {
      Assert.assertEquals(expectedValue, actualValue, message);
      log.info("Passed: --> expected { " + expectedValue + " } Equals actual --> { " + actualValue + " }");
   }


   /**
    * Assert if condition is true.
    *
    * Message can be concatenated with comma delimited
    *
    * Example: assertTrue(10==2,"10 does not equal ", "2") will throw error message "10 does not equal 2"
    *
    * @param condition boolean
    * @param message String[]
    */
   public void assertTrue(boolean condition, String... message)
   {
      StringBuilder sb = new StringBuilder();
      for (String each : message)
      {
         sb.append(each);
      }
      Assert.assertTrue(condition, sb.toString());
   }


   /**
    * This method tries to retrieve the system platform and return if it fails, it logs the exception and returns null
    *
    * @return the system platform, or null
    */
   public String discoverPlatform()
   {
      String version = System.getProperty("os.version");
      String os = System.getProperty("os.name").toLowerCase();
      if (os.contains("win"))
      {
         return "Windows " + version;
      }
      else if (os.contains("nux") || os.contains("nix"))
      {
         return "Linux " + version;
      }
      else if (os.contains("mac"))
      {
         return "Mac " + version;
      }
      else
      {
         return "Other " + version;
      }
   }


   /**
    * This method will return the current Browser Name and Browser Version
    */
   public String getBrowserNameVersion()
   {
      String browserName = getBrowserName().toLowerCase();
      if (browserName.contains("chrome"))
      {
         browserName = "Chrome";
      }
      else if (browserName.contains("firefox"))
      {
         browserName = "Firefox";
      }
      else if (browserName.contains("internet explorer"))
      {
         browserName = "Internet Explorer";
      }
      else if (browserName.contains("safari"))
      {
         browserName = "Safari";
      }
      else if (browserName.contains("opera"))
      {
         browserName = "Opera";
      }
      else
      {
         browserName = getBrowserName();
      }
      return browserName + ", Version: " + getBrowserVersion();
   }


   /**
    * This method will Takes a screenshot and saves it as a .PNG file in the defined directory.
    *
    * @param fileName - String, user need to define the file name with out extention
    *
    **/
   public void takeScreenshot(String fileLocation, String fileName)
   {
      String location = fileLocation + fileName + ".png";
      try
      {
         File scrFile = ((TakesScreenshot)getWebDriver()).getScreenshotAs(OutputType.FILE);
         FileUtils.copyFile(scrFile, new File(location));
         log.info("Screenshot..... Located at " + location);
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }


   /**
    * Compares two images, returning true if they are similar enough to constitute a match.
    *
    * @param img1 The first image to compare
    * @param img2 The second image to compare
    * @param errorsPerThousandThreshold The acceptable errors per thousand (pixels); i.e. haw many mismatched
    *           pixels are there allowed to be before the images are considered different. The lower the
    *           number the more strict the comparison will be
    * @return
    * @throws IOException
    */
   public static boolean compareImages(String image1, String image2, long errorsPerThousandThreshold)
   {
      try
      {
         BufferedImage img1 = ImageIO.read(ClassLoader.getSystemResourceAsStream(image1));
         BufferedImage img2 = ImageIO.read(ClassLoader.getSystemResourceAsStream(image2));
         if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight())
         {
            log.trace("The two images are different sizes, automatically returning false");
            return false;
         }
         long ept = compareRGB24Images(img1, img2, 0, 0, img1.getWidth(), img1.getHeight(), DEFAULT_KERNEL_RADIUS);
         boolean match = ept <= errorsPerThousandThreshold;
         log.info("Match:[{}]  Err/1K:[{}]  Limit:[{}]  PixErrLim:[{}]", match, ept, errorsPerThousandThreshold, PIXEL_ERROR_LIMIT);
         return match;
      } catch (Exception ex)
      {
         log.error("Error", ex);
      }
      return false;
   }


   /**
    * Compares a given region of two RGB24 type buffered image objects, returning the errors per thousand
    * (pixels) between the two.
    *
    * @param image1 The first image to compare
    * @param image2 The second image to compare
    * @param xOffset The starting x coordinate of the region that will be compared
    * @param yOffset The starting y coordinate of the region that will be compared
    * @param width The width of the comparison region
    * @param height The height of the comparison region
    * @param kernelRadius The radius used in the local average filter, the larger the number, the fuzzier the
    *           comparison will be
    * @return The errors per thousand (pixels) that was found between the two images
    */
   private static long compareRGB24Images(BufferedImage image1, BufferedImage image2, int xOffset, int yOffset, int width, int height, int kernelRadius)
   {
      double[] luminance1 = computeLuminance(image1, xOffset, yOffset, width, height);
      double[] luminance2 = computeLuminance(image2, xOffset, yOffset, width, height);
      applyLuminanceAdjustment(luminance1, luminance2);
      luminance1 = performLocalAvgFilter(luminance1, width, height, kernelRadius);
      luminance2 = performLocalAvgFilter(luminance2, width, height, kernelRadius);
      double lumDiffSumCubes = 0;
      int pixelMismatchCntr = 0;
      for (int i = 0; i < luminance1.length && i < luminance2.length; i++)
      {
         double lumDiff3 = Math.pow(Math.abs(luminance2[i] - luminance1[i]), 3);
         lumDiffSumCubes += lumDiff3;
         if (lumDiff3 > PIXEL_THRESHOLD_CUBE_LUM_DIFF)
         {
            pixelMismatchCntr++;
         }
      }
      int npixels = width * height;
      int errorsPerThou = pixelMismatchCntr * (int)PIXEL_ERROR_LIMIT / npixels;
      double avgCubeLumDiff = lumDiffSumCubes / npixels;
      log.trace("Mismatched Pixels:[{}]  TotalPixels:[{}]  Errors Per Thousand:[{}]  " + "Total Difference Lum^3:[{}]  Average Difference Lum^3:[{}]  Pixel Error Threshol:[{}]", pixelMismatchCntr, npixels, errorsPerThou, lumDiffSumCubes, avgCubeLumDiff, PIXEL_ERROR_LIMIT);
      return errorsPerThou;
   }


   private static double[] computeLuminance(BufferedImage image, int xStart, int yStart, int width, int height)
   {
      checkBounds(image, xStart, yStart, width, height);
      double[] luminance = new double[width * height];
      for (int y = yStart; y < height; y++)
      {
         for (int x = xStart; x < width; x++)
         {
            int pixel = image.getRGB(x, y);
            int red = (pixel >> 16) & BYTE_MASK;
            int green = (pixel >> 8) & BYTE_MASK;
            int blue = (pixel >> 0) & BYTE_MASK;
            luminance[x * y] = RED_RATIO * red + GREEN_RATIO * green + BLUE_RATIO * blue;
         }
      }
      return luminance;
   }


   private static void checkBounds(BufferedImage image, int xStart, int yStart, int width, int height)
   {
      int imgWidth = image.getWidth();
      int imgHeight = image.getHeight();
      if (xStart > imgWidth || width > imgWidth || (xStart + width) > imgWidth)
      {
         throw new RuntimeException("image does not fully contain the comparison region");
      }
      if (yStart > imgHeight || height > imgHeight || (yStart + height) > imgHeight)
      {
         throw new RuntimeException("image does not fully contain the comparison region");
      }
   }


   private static void applyLuminanceAdjustment(double[] luminance1, double[] luminance2)
   {
      double avgLum1 = averageLuminance(luminance1);
      double avgLum2 = averageLuminance(luminance2);
      double diffAvgLum = avgLum1 - avgLum2;
      for (int i = 0; i < luminance2.length; i++)
      {
         luminance2[i] += diffAvgLum;
      }
   }


   private static double averageLuminance(double[] luminance)
   {
      double avgLum = 0;
      for (int i = 0; i < luminance.length; i++)
      {
         avgLum += luminance[i];
      }
      avgLum = avgLum / luminance.length;
      return avgLum;
   }


   protected static double[] performLocalAvgFilter(double[] data, int width, int height, int kernelRadius)
   {
      double filteredData[] = new double[data.length];
      for (int ii = 0; ii < data.length; ii++)
      {
         int c = 0;
         for (int i = -kernelRadius; i <= kernelRadius; i++)
         {
            for (int j = -kernelRadius; j <= kernelRadius; j++)
            {
               int pixIndex = ii + (i * width) + j;
               if (pixIndex >= 0 && pixIndex < data.length)
               {
                  filteredData[ii] += data[pixIndex];
                  c++;
               }
            }
         }
         filteredData[ii] = filteredData[ii] / c;
      }
      return filteredData;
   }


   private String getBrowsersNameVersion()
   {
      String browserName = getBrowserName().toLowerCase();
      if (browserName.contains("chrome"))
      {
         browserName = "Chrome";
      }
      else if (browserName.contains("firefox"))
      {
         browserName = "Firefox";
      }
      else if (browserName.contains("internet explorer"))
      {
         browserName = "Internet Explorer";
      }
      else if (browserName.contains("safari"))
      {
         browserName = "Safari";
      }
      else if (browserName.contains("opera"))
      {
         browserName = "Opera";
      }
      else
      {
         browserName = getBrowserName();
      }
      browserName = " { Browser: " + browserName + " } { Version: " + getBrowserVersion().toString() + " } ";
      return browserName;
   }


   /**
    * This method will perform the right click action
    *
    * @param element - WebElement
    */
   public void rightClick(String element)
   {
      WebElement e1 = elementToBeClickable(element);
      Actions action = new Actions(webDriver);
      action.contextClick(e1).build().perform();
      delay(FIVE);
   }


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


   public String getComputerName()
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
      return computername;
   }


   public String getComputerUserName()
   {
      String userName = "";
      userName = System.getProperty("user.name");
      if (userName.equals(""))
      {
         userName = System.getenv("USERNAME");
      }
      return userName;
   }


   public static final String findFileName(String path, String name, FileSearchType fileSearchType)
   {
      String systemSeperator = System.getProperty("file.separator");
      log.debug("Starting search for file: {}{}{}", new Object[] { path, systemSeperator, name });
      String myPath = setFileDelimiter(path);
      File file = new File(myPath);
      if ( !file.isDirectory())
      {
         log.debug("Search path for file is not a directory: {}", path);
         return "";
      }
      if (name == null || name.isEmpty())
      {
         log.error("the file name for the search is empty: {}", name);
         return "";
      }
      File[] files = file.listFiles();
      try
      {
         for (File subFile : files)
         {
            String findPath;
            if ((fileSearchType == FileSearchType.Both || fileSearchType == FileSearchType.Directory && subFile.isDirectory() || fileSearchType == FileSearchType.File && subFile.isFile()) && subFile.getName().toLowerCase().matches(name.toLowerCase()))
            {
               log.debug("Search ok, " + subFile.getCanonicalPath());
               return subFile.getCanonicalPath();
            }
            if ( !subFile.isDirectory() || (findPath = findFileName(subFile.getCanonicalPath(), name, fileSearchType)).isEmpty())
            {
               continue;
            }
            return findPath;
         }
      } catch (IOException e)
      {
         log.error("Can't find file: {},{}", path, name);
         log.info("Can't find file " + path + name);
      }
      return "";
   }
   public static enum FileSearchType
   {
    Directory,
    File,
    Both;
      private FileSearchType()
      {}
   }


   private static String setFileDelimiter(String aPath)
   {
      String systemFileSeparator = System.getProperty("file.separator");
      String wrongFileSeparator = systemFileSeparator.equals("/") ? "\\" : "/";
      char s = '\\';
      if (aPath.contains(wrongFileSeparator) || wrongFileSeparator.contains(String.valueOf(s)) && aPath.contains(String.valueOf(s)))
      {
         log.debug("replacing (wrong fileSeparator) " + wrongFileSeparator.toString() + " with SystemFileSeparator " + systemFileSeparator + " in " + aPath);
         return new String(aPath.replace(wrongFileSeparator, systemFileSeparator));
      }
      return aPath;
   }
}