package test.tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class Json
{
   protected static final Logger log = LoggerFactory.getLogger(one.class);
   public TestName name = new TestName();
   WebDriver driver = null;
   SingleSeleniumSolution sss = new SingleSeleniumSolution(driver);


   @Test
   public void createJson()
   {
      JSONObject obj = new JSONObject();
      obj.put("name", "mkyong.com");
      obj.put("age", new Integer(100));
      JSONArray list = new JSONArray();
      list.add("msg 1");
      list.add("msg 2");
      list.add("msg 3");
      obj.put("messages", list);
      try
      {
         FileWriter file = new FileWriter("c:/Works/test.json");
         file.write(obj.toJSONString());
         file.flush();
         file.close();
      } catch (IOException e)
      {
         e.printStackTrace();
      }
      log.info(" {}", obj);
   }


   @Test
   public void readJson()
   {
      JSONParser parser = new JSONParser();
      try
      {
         Object obj = parser.parse(new FileReader("c:/Works/test.json"));
         JSONObject jsonObject = (JSONObject)obj;
         String name = jsonObject.get("name").toString();
         log.info(name);
         String age = jsonObject.get("age").toString();
         log.info(age);
         // loop array
         JSONArray msg = (JSONArray)jsonObject.get("messages");
         Iterator<String> iterator = msg.iterator();
         while (iterator.hasNext())
         {
            log.info(iterator.next());
         }
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      } catch (IOException e)
      {
         e.printStackTrace();
      } catch (ParseException e)
      {
         e.printStackTrace();
      }
   }
}