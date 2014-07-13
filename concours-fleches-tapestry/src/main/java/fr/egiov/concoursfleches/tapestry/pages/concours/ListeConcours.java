package fr.egiov.concoursfleches.tapestry.pages.concours;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.exceptions.concours.ConcoursException;
import fr.egiov.concoursfleches.services.ConcoursService;
import fr.egiov.concoursfleches.tapestry.datasource.GenericDaoSource;
import fr.egiov.concoursfleches.tapestry.pages.AbstractPage;
import fr.egiov.concoursfleches.tapestry.pages.archer.ListeArchers;

/**
 * Page affichant la liste des concours
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/liste_concours.css" })
public class ListeConcours extends AbstractPage
{
   // ------------------------- Constantes private -------------------------

   /** id du lien supprimer concours */
   private static final String ID_LIEN_SUPPRIMER_CONCOURS = "supprimerConcoursLien";

   /** Le logger */
   private static final Logger LOGGER = LoggerFactory
         .getLogger(ListeArchers.class);

   // ------------------------- Membres private -------------------------

   /** Service club */
   @Inject
   private ConcoursService m_ConcoursService;

   /** variable utilisée par Tapestry pour construire la liste des concours */
   private Concours m_Concours;

   /** Exception levée par une action */
   @Persist
   private Exception m_Exception;

   // ------------------------- Méthodes public -------------------------

   /**
    * Méthode appelée lors du clic sur le lien "Supprimer Concours".
    * 
    * @param p_ConcoursId
    *           l'id du concours
    */
   @OnEvent(component = ID_LIEN_SUPPRIMER_CONCOURS)
   void onSupprimerConcours(Long p_ConcoursId)
   {
      try
      {
         LOGGER.debug("onSupprimerConcours() : concours = {}", p_ConcoursId);
         m_ConcoursService.supprimerConcours(p_ConcoursId);
      }
      catch (ConcoursException e)
      {
         m_Exception = e;
         LOGGER.error("ArcherException recue", e);
      }
      catch (Exception e)
      {
         m_Exception = new ConcoursException(
               ConcoursService.MESSAGE_ERREUR_SUPPRESSION, e);
         LOGGER.error("Exception recue", e);
      }
   }

   // ------------------------- Accesseur public -------------------------

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
    * @return la source permettant de récupérer les clubs
    */
   public GridDataSource getConcoursSource()
   {
      return new GenericDaoSource<Concours>(m_ConcoursService.getDao());
   }

   /**
    * @return Le message de l'erreur
    */
   public String getMessageErreur()
   {
      String message = null;
      if (null != m_Exception)
      {
         message = getMessages().get(m_Exception.getMessage());
      }
      m_Exception = null;
      return message;
   }

   /**
    * @return <code>true</code> si une erreur existe
    */
   public boolean isErreur()
   {
      LOGGER.debug("isErreur {}", m_Exception);
      return (null != m_Exception);
   }
}
