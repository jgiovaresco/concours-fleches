package fr.egiov.concoursfleches.tapestry.pages;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Classe de page.
 */
@IncludeStylesheet( { "context:css/index.css" })
public class Index
{

   // ------------------------- Membres private -------------------------

   /** l'image du logo 1 */
   @Inject
   @Path("context:images/logo_ufolep_1.jpg")
   private Asset m_ImageLogo1;

   /** l'image du logo 2 */
   @Inject
   @Path("context:images/logo_ufolep_2.jpg")
   private Asset m_ImageLogo2;

   /** l'image du logo 3 */
   @Inject
   @Path("context:images/logo_ufolep_3.jpg")
   private Asset m_ImageLogo3;

   // ------------------------- Accesseurs public ------------------------

   /**
    * @return l'image du logo 1
    */
   public Asset getImageLogo1()
   {
      return m_ImageLogo1;
   }

   /**
    * @return l'image du logo 2
    */
   public Asset getImageLogo2()
   {
      return m_ImageLogo2;
   }

   /**
    * @return l'image du logo 3
    */
   public Asset getImageLogo3()
   {
      return m_ImageLogo3;
   }
}
