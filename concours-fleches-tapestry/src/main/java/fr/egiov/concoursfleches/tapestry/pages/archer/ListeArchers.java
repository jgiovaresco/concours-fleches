package fr.egiov.concoursfleches.tapestry.pages.archer;

import java.util.List;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.model.Archer;
import fr.egiov.concoursfleches.exceptions.archer.ArcherException;
import fr.egiov.concoursfleches.services.ArcherService;
import fr.egiov.concoursfleches.tapestry.datasource.GenericDaoSource;
import fr.egiov.concoursfleches.tapestry.pages.AbstractPage;
import fr.egiov.concoursfleches.tapestry.pages.concours.FormCible;

/**
 * Page affichant la liste des archers
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/liste_archer.css" })
public class ListeArchers extends AbstractPage
{
   // ------------------------- Constantes private -------------------------

   /** Id du lien sélectionner un archer */
   private static final String SELECTIONNER_ARCHER_ACTION = "selectionnerArcherLien";

   /** id du lien supprimer archer */
   private static final String ID_LIEN_SUPPRIMER_ARCHER = "supprimerArcherLien";

   /** Le logger */
   private static final Logger LOGGER = LoggerFactory
         .getLogger(ListeArchers.class);

   // ------------------------- Membres private -------------------------

   /** Service archer */
   @Inject
   private ArcherService m_ArcherService;

   /** liste des archers */
   private List<Archer> m_Archers;

   /** variable utilisée par Tapestry pour construire la liste des archers */
   private Archer m_Archer;

   /** Page ajouter cible */
   @InjectPage
   private FormCible m_FormCiblePage;

   /** Action a ajouter à la page (sélection...) */
   @Persist
   private String m_Action;

   /** Composant affichant la liste des archers */
   @InjectComponent(value = "grid")
   private Grid m_Grid;

   /** Exception levée par une action */
   @Persist
   private Exception m_Exception;

   // ------------------------- Methodes package scoped ------------------------

   /**
    * Méthode appelée pour préparer l'affichage de la page
    */
   void setupRender()
   {
      m_Archers = m_ArcherService.findAllArchers();

      LOGGER.debug("setupRender() - "
            + m_Grid.getSortModel().getSortConstraints());
      if (m_Grid.getSortModel().getSortConstraints().isEmpty())
      {
         m_Grid.getSortModel().updateSort("nom");
      }
   }

   /**
    * Méthode appelée lors du clic sur le lien "Sélectionner".
    * 
    * @param p_ArcherId
    *           l'id de l'archer sélectionné
    * 
    * @return {@link FormArcher}
    */
   @OnEvent(component = SELECTIONNER_ARCHER_ACTION)
   Object onSelectionnerArcher(Long p_ArcherId)
   {
      Archer archer = m_ArcherService.findArcher(p_ArcherId);
      m_FormCiblePage.setArcherSelectionne(archer);
      m_Action = null;
      return m_FormCiblePage;
   }

   /**
    * Méthode appelée lors du clic sur le lien "Supprimer Archer".
    * 
    * @param p_ArcherId
    *           l'id de l'archer
    */
   @OnEvent(component = ID_LIEN_SUPPRIMER_ARCHER)
   void onSupprimerArcher(Long p_ArcherId)
   {
      try
      {
         LOGGER.debug("onSupprimerArcher() : archer = {}", p_ArcherId);
         m_ArcherService.supprimerArcher(p_ArcherId);
      }
      catch (ArcherException e)
      {
         m_Exception = e;
         LOGGER.error("ArcherException recue", e);
      }
      catch (Exception e)
      {
         m_Exception = new ArcherException(
               ArcherService.MESSAGE_ERREUR_SUPPRESSION, e);
         LOGGER.error("Exception recue", e);
      }
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * @return la liste des archers
    */
   public List<Archer> getArchers()
   {
      return m_Archers;
   }

   /**
    * @return l'archer
    */
   public Archer getArcher()
   {
      return m_Archer;
   }

   /**
    * @param p_archer
    *           l'archer
    */
   public void setArcher(Archer p_archer)
   {
      m_Archer = p_archer;
   }

   /**
    * @return la source permettant de récupérer les archers
    */
   public GridDataSource getArcherSource()
   {
      return new GenericDaoSource<Archer>(m_ArcherService.getDao());
   }

   /**
    * @return le model de la grid
    */
   public BeanModel<Archer> getBeanModel()
   {
      BeanModel<Archer> model = getBeanModelSource().createDisplayModel(
            Archer.class, getMessages());

      model.getById("categorieAge").sortable(false);
      return model;
   }

   /**
    * @param p_Action
    *           l'action
    */
   public void setAction(String p_Action)
   {
      m_Action = p_Action;
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
    * @return <code>true</code> si l'action correspond à sélectionner
    */
   public boolean isSelectionnerAction()
   {
      return (true == SELECTIONNER_ARCHER_ACTION.equals(m_Action));
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
