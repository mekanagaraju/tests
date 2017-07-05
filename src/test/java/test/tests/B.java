package test.tests;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;

public class B
{
   protected static final Logger log = LoggerFactory.getLogger(B.class);


   @BeforeMethod
   public void quit(Method method, ITestResult result)
   {
      String className = method.getDeclaringClass().getSimpleName();
      // String className = result.getClass().getSimpleName();
      log.info("Class Name: " + className);
   }
}
