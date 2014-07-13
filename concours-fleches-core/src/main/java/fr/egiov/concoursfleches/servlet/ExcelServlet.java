package fr.egiov.concoursfleches.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import fr.egiov.concoursfleches.helpers.ExcelHelper;

/**
 * Servlet générant le fichier Excel des résultats d'un concours
 * 
 * @author giovarej
 */
public class ExcelServlet extends HttpServlet
{

   /** serial version uid */
   private static final long serialVersionUID = 1L;

   /**
    * {@inheritDoc}
    * 
    * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */
   @Override
   protected void doGet(HttpServletRequest p_Requete,
         HttpServletResponse p_Reponse) throws ServletException, IOException
   {
      try
      {
         ExcelHelper excelHelper = null;
         Long coucoursId = null;
         String nomFichier = "resultats_concours.xls";

         if (null != p_Requete.getParameter("concoursId"))
         {
            excelHelper = getExcelHelper();
            coucoursId = Long.valueOf(p_Requete.getParameter("concoursId"));

            HSSFWorkbook wb = excelHelper.genererExcel(coucoursId);

            p_Reponse.setContentType("application/octet-stream");
            p_Reponse.setHeader("Content-Disposition",
                  "attachment; filename=\"" + nomFichier + "\"");
            // Write the output
            OutputStream out = p_Reponse.getOutputStream();
            wb.write(out);
            out.close();
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new ServletException(e);
      }
   }

   /**
    * @return une instance de {@link ExcelHelper}
    */
   private ExcelHelper getExcelHelper()
   {
      ApplicationContext context = WebApplicationContextUtils
            .getWebApplicationContext(getServletContext());

      return (ExcelHelper) context.getBean("excelHelper");
   }
}
