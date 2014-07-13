package fr.egiov.concoursfleches.tapestry.pages.club;

import java.util.List;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.egiov.concoursfleches.domaine.model.Club;
import fr.egiov.concoursfleches.services.ClubService;
import fr.egiov.concoursfleches.tapestry.datasource.GenericDaoSource;
import fr.egiov.concoursfleches.tapestry.pages.archer.FormArcher;

/**
 * Page affichant la liste des clubs
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/liste_clubs.css" })
public class ListeClubs
{
   // ------------------------- Constantes public -------------------------

   /** Action de sélectionner un club */
   public static final String SELECTIONNER_CLUB_ACTION = "selectionner";

   // ------------------------- Membres private -------------------------

   /** Service club */
   @Inject
   private ClubService m_ClubService;

   /** variable utilisée par Tapestry pour construire la liste des clubs */
   private Club m_Club;

   /** Page CreerArcher */
   @InjectPage
   private FormArcher m_PageCreerArcher;

   /** Action a ajouter à la page (sélection...) */
   @Persist
   private String m_Action;

   // ------------------------- Methodes package scoped ------------------------

   /**
    * Méthode appelée lors du clic sur le lien "Sélectionner".
    * 
    * @return {@link FormArcher}
    */
   @OnEvent(component = "selectionnerLien")
   Object onSelectionnerClub(Long p_ClubId)
   {
      Club club = m_ClubService.findClub(p_ClubId);
      m_PageCreerArcher.setClubSelectionne(club);
      m_Action = null;
      return m_PageCreerArcher;
   }

   // ------------------------- Accesseur public -------------------------

   /**
    * @return la liste des clubs
    */
   public List<Club> getClubs()
   {
      return m_ClubService.findAllClubs();
   }

   /**
    * @return le club
    */
   public Club getClub()
   {
      return m_Club;
   }

   /**
    * @param p_Club
    *           le club
    */
   public void setClub(Club p_Club)
   {
      m_Club = p_Club;
   }

   /**
    * @return la source permettant de récupérer les clubs
    */
   public GridDataSource getClubSource()
   {
      return new GenericDaoSource<Club>(m_ClubService.getDao());
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
    * @return <code>true</code> si l'action correspond à sélectionner
    */
   public boolean isSelectionnerAction()
   {
      return (true == SELECTIONNER_CLUB_ACTION.equals(m_Action));
   }
}
