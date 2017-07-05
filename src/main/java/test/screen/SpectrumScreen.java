package test.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;

import test.tests.SingleSeleniumSolution;

public class SpectrumScreen
{
   protected Log log = LogFactory.getLog(this.getClass().getName());
   SingleSeleniumSolution sss;
   public static final String SIGN = "//a[contains(text(), 'Sign In')]";
   public static final String ZIPCODE = "css=input[id='zipcode']";
   public static final String CLOSE_FEEDBACKPOPUP = "css=button[id='expoBtnClose']";
   public static final String FEEDBACK_FRAME = "css=iframe[id='artEXPOiFrame']";
   public static final String ZIPCODE_SUBMIT = "css=button[type='submit']";


   public SpectrumScreen(WebDriver webDriver)
   {
      this.sss = new SingleSeleniumSolution(webDriver);
   }


   public void doSignIn()
   {
      sss.waitForElementVisible(SIGN, 90);
      sss.click(SIGN);
      log.info("Clicked on Sign in Button");
      sss.waitForElementVisible(ZIPCODE, 60);
      sss.type(ZIPCODE, "31901");
      closeCustomerFeedbackSurvey();
      sss.click(ZIPCODE_SUBMIT);
   }


   private void closeCustomerFeedbackSurvey()
   {
      if (sss.isElementVisible(FEEDBACK_FRAME))
      {
         sss.switchToFrame(FEEDBACK_FRAME);
         sss.click(CLOSE_FEEDBACKPOPUP);
         log.info("Closed the Customer Feedback Survey");
         sss.switchToDefaultContent();
      }
   }
}
