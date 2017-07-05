package test.tests;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class ScreenMaxmize
{
   protected static final Logger log = LoggerFactory.getLogger(ScreenMaxmize.class);


   @Test
   public void testMaxmizeScreen()
   {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice display = ge.getDefaultScreenDevice();
      DisplayMode[] availableModes = display.getDisplayModes();
      ArrayList<String> mylist = new ArrayList<String>();
      for (DisplayMode mode : availableModes)
      {
         log.info(mode.getWidth() + "x" + mode.getHeight());
         mylist.add(mode.getWidth() + "x" + mode.getHeight());
      }
      ArrayList<String> unique = removeDuplicates(mylist);
      for (String element : unique)
      {
         log.info(element);
      }
   }


   public ArrayList<String> removeDuplicates(ArrayList<String> list)
   {
      ArrayList<String> result = new ArrayList<>();
      HashSet<String> set = new HashSet<>();
      for (String item : list)
      {
         if ( !set.contains(item))
         {
            result.add(item);
            set.add(item);
         }
      }
      return result;
   }
}
