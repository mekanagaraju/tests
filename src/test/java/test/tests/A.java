package test.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class A extends B
{
   protected static final Logger log = LoggerFactory.getLogger(A.class);


   @Test
   public void setUp()
   {
      log.info("Test ");
   }
}
