package fr.egiov.concoursfleches.tapestry.pages.concours;

import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.tapestry.model.CibleTapestry;
import fr.egiov.concoursfleches.tapestry.pages.AbstractPage;

/**
 * Page d'impression des feuilles de marque
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/imprimer_feuille_de_marque.css" })
public class ImprimerFeuilleDeMarque extends AbstractPage
{
   // ------------------------- Membres private -------------------------

   /** le concours du formulaire */
   @Persist
   private Concours m_Concours;

   /** les cibles du concours */
   @Persist
   private List<CibleTapestry> m_Cibles;

   /** variable utilisée par Tapestry pour afficher la liste des cibles */
   private CibleTapestry m_Cible;

   /** l'image du logo 1 */
   @Inject
   @Path("context:images/logo_ufolep_1.jpg")
   private Asset m_ImageLogo1;

   // ------------------------- Accesseurs public ------------------------

   /**
    * @return le concours
    */
   public Concours getConcours()
   {
      return m_Concours;
   }

   /**
    * @param p_Concours
    *           le concours
    */
   public void setConcours(Concours p_Concours)
   {
      m_Concours = p_Concours;
   }

   /**
    * @return les cibles du concours
    */
   public List<CibleTapestry> getCibles()
   {
      return m_Cibles;
   }

   /**
    * @param p_Cible
    *           la cible
    */
   public void setCibles(List<CibleTapestry> p_Cibles)
   {
      m_Cibles = p_Cibles;
   }

   /**
    * @return la cible
    */
   public CibleTapestry getCible()
   {
      return m_Cible;
   }

   /**
    * @param p_Cible
    *           la cible
    */
   public void setCible(CibleTapestry p_Cible)
   {
      m_Cible = p_Cible;
   }

   /**
    * @return l'image du logo 1
    */
   public Asset getImageLogo1()
   {
      return m_ImageLogo1;
   }
}
