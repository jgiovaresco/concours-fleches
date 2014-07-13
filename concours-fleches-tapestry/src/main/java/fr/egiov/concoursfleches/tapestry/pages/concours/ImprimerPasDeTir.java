package fr.egiov.concoursfleches.tapestry.pages.concours;

import java.util.List;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.model.Cible;
import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.exceptions.concours.ConcoursNotFoundException;
import fr.egiov.concoursfleches.services.CibleService;
import fr.egiov.concoursfleches.services.ConcoursService;
import fr.egiov.concoursfleches.tapestry.pages.AbstractPage;

/**
 * Page affichant un concours
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/imprimer_pas_de_tir.css" })
public class ImprimerPasDeTir extends AbstractPage
{
   // ------------------------- Constantes private -------------------------

   // ------------------------- Membres private -------------------------

   /** le logger */
   private final Logger m_Logger = LoggerFactory
         .getLogger(ImprimerPasDeTir.class);

   /** Service Concours */
   @Inject
   private ConcoursService m_ConcoursService;

   /** l'id du concours affiché */
   private Long m_ConcoursId;

   /** Service Cible */
   @Inject
   private CibleService m_CibleService;

   /** le concours du formulaire */
   @Persist
   private Concours m_Concours;

   /** les cibles du concours */
   private List<Cible> m_Cibles;

   /** variable utilisée par Tapestry pour afficher la liste des cibles */
   private Cible m_Cible;

   // ------------------------- Methodes package scoped ------------------------

   /**
    * Méthode appelée lors de l'activation de la page
    * 
    * @param p_Code
    *           le paramètre de requete permettant de savoir quel est le type du
    *           formulaire
    */
   void onActivate(Long p_ConcoursId)
   {
      m_ConcoursId = p_ConcoursId;
      try
      {
         m_Concours = m_ConcoursService.findConcours(m_ConcoursId);
         m_Cibles = m_CibleService.getCiblesConcours(m_Concours);
      }
      catch (ConcoursNotFoundException e)
      {
         // Ne peut pas arriver.
         m_Logger.error("Le concours n'existe pas");
      }
   }

   /**
    * Méthode appelée lors de la passivation de la page
    */
   Long onPassivate()
   {
      return m_ConcoursId;
   }

   // ------------------------- Accesseurs public-------------------------

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
   public List<Cible> getCibles()
   {
      return m_Cibles;
   }

   /**
    * @return la cible
    */
   public Cible getCible()
   {
      return m_Cible;
   }

   /**
    * @param p_Cible
    *           la cible
    */
   public void setCible(Cible p_Cible)
   {
      m_Cible = p_Cible;
   }
}
