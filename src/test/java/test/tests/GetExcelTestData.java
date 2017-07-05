package test.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class GetExcelTestData
{
   protected static final Logger log = LoggerFactory.getLogger(GetExcelTestData.class);
   String excelReader = ("C:/IVR/IVR_WS_ICOMS_TestData.xls");
   ExcelJXL xl = new ExcelJXL();


   @Test
   public void getTestDataFromExcel()
   {
      log.info("Test data from excel {}", excelReader);
      xl.printExcelData(excelReader);
   }
}
