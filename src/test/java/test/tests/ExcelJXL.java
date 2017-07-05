package test.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelJXL
{
   protected static final Logger log = LoggerFactory.getLogger(ExcelJXL.class);
   ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
   ArrayList<String> rowData = new ArrayList<String>();
   ArrayList<String> headerName = new ArrayList<String>();


   private ArrayList<ArrayList<String>> readExcelCount(String fileName)
   {
      Workbook wb;
      try
      {
         wb = Workbook.getWorkbook(new File(fileName));
         Sheet sh = wb.getSheet(0);
         int totalNoOfRows = sh.getRows();
         sh.getColumns();
         for (int row = 0; row < totalNoOfRows; row++)
         {
            for (int col = 0; col < 4; col++)
            {
               headerName.add(sh.getCell(col, row).getContents().toString().trim());
            }
         }
         for (int row = 1; row < totalNoOfRows; row++)
         {
            for (int col = 0; col < 4; col++)
            {
               rowData.add(sh.getCell(col, row).getContents().toString());
            }
            data.add(rowData);
            rowData = new ArrayList<String>();
         }
      } catch (BiffException | IOException e)
      {}
      return data;
   }


   public void printExcelData(String fileName)
   {
      ArrayList<String> testData = new ArrayList<String>();
      data = readExcelCount(fileName);
      // System.out.println(data);
      for (ArrayList<String> l : data)
      {
         for (String z : l)
         {
            if (z.equals(""))
            {
               z = null;
            }
            testData.add(z);
         }
         testMethod(testData.get(0), testData.get(1), testData.get(2), testData.get(3));
         testData = new ArrayList<String>();
      }
   }


   private void testMethod(String a, String b, String c, String d)
   {
      log.info("===============================================");
      log.info("{}: {}, {}: {}, {}: {}, {}: {} ", headerName.get(0), a, headerName.get(1), b, headerName.get(2), c, headerName.get(3), d);
      log.info("===============================================");
      log.info("");
   }
}
