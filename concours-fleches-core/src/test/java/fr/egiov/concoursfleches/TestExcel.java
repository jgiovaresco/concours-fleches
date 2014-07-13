package fr.egiov.concoursfleches;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import fr.egiov.concoursfleches.helpers.ExcelHelper;

public class TestExcel
{
   public static final int PREMIERE_COLONNE = 0;
   public static final int PREMIERE_LIGNE = 0;
   public static final int NUMERO_LIGNE_DEPART = 0;

   public static final int NUMERO_CELLULE_POSITION = 0;
   public static final int NUMERO_CELLULE_ARCHER = 1;
   public static final int NUMERO_CELLULE_CLUB = 2;
   public static final int NUMERO_CELLULE_SERIE1 = 3;
   public static final int NUMERO_CELLULE_SERIE2 = 4;
   public static final int NUMERO_CELLULE_SCORE = 5;

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      try
      {
         new TestExcel().genererExcel();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   public void genererExcel() throws Exception
   {
      ApplicationContext ctx = new FileSystemXmlApplicationContext(
            "classpath:fr/egiov/concoursfleches/excel/contexteTestExcel.xml");

      ExcelHelper excelHelper = (ExcelHelper) ctx.getBean("excelHelper");

      // create a new file
      FileOutputStream out = new FileOutputStream("target/workbook.xls");
      // create a new workbook
      HSSFWorkbook wb = excelHelper.genererExcel(Long.valueOf(1));
      wb.write(out);
      out.close();
   }

}
