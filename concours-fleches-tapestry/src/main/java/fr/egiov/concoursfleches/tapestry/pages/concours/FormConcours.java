package fr.egiov.concoursfleches.tapestry.pages.concours;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.model.Club;
import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.exceptions.concours.ConcoursNotFoundException;
import fr.egiov.concoursfleches.services.ClubService;
import fr.egiov.concoursfleches.services.ConcoursService;
import fr.egiov.concoursfleches.tapestry.composants.InjectSelectionModel;
import fr.egiov.concoursfleches.tapestry.pages.AbstractFormPage;
import fr.egiov.concoursfleches.tapestry.pages.TypeFormulaire;
import fr.egiov.concoursfleches.tapestry.pages.club.ListeClubs;

/**
 * Page permettant de créer un concours
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/creer_concours.css" })
public class FormConcours extends AbstractFormPage
{
   // ------------------------- Constantes private -------------------------

   /** id du bouton créer à partir d'un autre concours */
   private static final String ID_BOUTON_CREER_A_PARTIR_AUTRE_CONCOURS = "creerBouton";

   /** id du bouton créer nouveau concours */
   private static final String ID_BOUTON_CREER_NOUVEAU = "creerNouveauBouton";

   /** id du bouton modifier */
   private static final String ID_BOUTON_MODIFIER = "modifierBouton";

   /** id du lien sélectionné Club */
   private static final String ID_LIEN_SELECTIONNER_CLUB = "selectionnerClubLien";

   // ------------------------- Membres private -------------------------

   /** le logger */
   private Logger m_Logger = LoggerFactory.getLogger(FormConcours.class);

   /** Service Concours */
   @Inject
   private ConcoursService m_ConcoursService;

   /** Service club */
   @Inject
   private ClubService m_ClubService;

   /** la page de sélection d'un club */
   @InjectPage
   private ListeClubs m_ListeClubsPage;

   /** le concours affiché */
   @Persist
   private Concours m_Concours;

   /** un ancien concours */
   private Concours m_AncienConcours;

   /** la date du concours à partir duquel on peut créer un autre concours */
   private Date m_Date;

   /** référence au formulaire de la page */
   @Component(id = "concoursForm")
   private Form m_ConcoursForm;

   /** DateField correspondant à dateAPartir */
   @Component(id = "dateAPartir")
   private DateField m_DateAPartirTextField;

   /** le club sélectioné */
   @Persist
   private Club m_ClubSelectionne;

   /** liste des club */
   @InjectSelectionModel(labelField = "nomComplet", idField = "id")
   private List<Club> clubs = new ArrayList<Club>();

   // ------------------------- Methodes package scoped ------------------------

   /**
    * Méthode appelée lors de l'activation de la page
    * 
    * @param p_Code
    *           le paramètre de requete permettant de savoir quel est le type du
    *           formulaire
    */
   void onActivate(String p_Code)
   {
      if (CODE_CREER.equals(p_Code))
      {
         m_Concours = new Concours();
         setTypeFormulaire(TypeFormulaire.CREATION);
         m_ClubSelectionne = null;
      }
      else
      {
         Long concoursId = null;
         try
         {
            concoursId = Long.parseLong(p_Code);
            // p_Code correspond à un nombre. Ce doit être un identifiant
            // d'archer
            m_Concours = m_ConcoursService.findConcours(concoursId);
            m_ClubSelectionne = m_Concours.getClubOrganisateur();
            setTypeFormulaire(TypeFormulaire.MODIFICATION);
         }
         catch (NumberFormatException e)
         {
            // Le code ne correspond pas à un nombre. Le formulaire sera de type
            // CREATION
            m_Concours = new Concours();
            setTypeFormulaire(TypeFormulaire.CREATION);
         }
         catch (ConcoursNotFoundException e)
         {
            // Ne peut pas arriver.
            m_Logger.error("Le concours n'existe pas");

            m_Concours = new Concours();
            setTypeFormulaire(TypeFormulaire.CREATION);
         }
      }
   }

   /**
    * Méthode appelée lors de la préparation de la page
    */
   void onPrepare()
   {
      clubs = m_ClubService.findAllClubs();
   }

   /**
    * Méthode appelée après la soumission du formulaire
    */
   @OnEvent(value = "submit", component = "concoursForm")
   Object onSubmit()
   {
      m_Logger.debug("onSubmit()");
      Class<?> pageSuivante = FormConcours.class;

      if (null != m_Concours.getId())
      {
         pageSuivante = ListeConcours.class;
      }

      setIdBoutonClique(null);
      return pageSuivante;
   }

   /**
    * Méthode appelée lors du clic sur le lien "Sélectionner un club".
    * 
    * @return {@link ListeClubs}
    */
   @OnEvent(component = ID_LIEN_SELECTIONNER_CLUB)
   Object onSelectionnerClub()
   {
      m_ListeClubsPage.setAction(ListeClubs.SELECTIONNER_CLUB_ACTION);
      return m_ListeClubsPage;
   }

   /**
    * Méthode appelée lors du clic sur le bouton créer nouveau concours
    */
   @OnEvent(value = "selected", component = ID_BOUTON_CREER_NOUVEAU)
   void onCreerNouveauBouton()
   {
      m_Logger.debug("onCreerNouveauBouton()");
      setIdBoutonClique(ID_BOUTON_CREER_NOUVEAU);
   }

   /**
    * Méthode appelée lors du clic sur le bouton créer
    */
   @OnEvent(value = "selected", component = ID_BOUTON_CREER_A_PARTIR_AUTRE_CONCOURS)
   void onCreerAPartirAutreConcoursBouton()
   {
      m_Logger.debug("onCreerAPartirAutreConcoursBouton()");
      setIdBoutonClique(ID_BOUTON_CREER_A_PARTIR_AUTRE_CONCOURS);

      if (null == m_Date)
      {
         m_ConcoursForm.recordError(m_DateAPartirTextField, getMessages().get(
               "dateAPartir-obligatoire"));
      }
      else
      {
         try
         {
            m_AncienConcours = m_ConcoursService.findConcoursByDate(m_Date);
         }
         catch (ConcoursNotFoundException e)
         {
            m_ConcoursForm.recordError(m_DateAPartirTextField, getMessages()
                  .get("concours-pastrouve"));
         }
      }
   }

   /**
    * Méthode appelée lors du clic sur le bouton modifier
    */
   @OnEvent(value = "selected", component = ID_BOUTON_MODIFIER)
   void onModifierBouton()
   {
      setIdBoutonClique(ID_BOUTON_MODIFIER);
   }

   /**
    * Méthode appelée lors du succes de la validation des champs du formulaire
    */
   void onSuccess()
   {
      m_Logger.debug("onSuccess()");
      m_Concours.setClubOrganisateur(m_ClubSelectionne);
      if (ID_BOUTON_CREER_NOUVEAU.equals(getIdBoutonClique()))
      {
         m_ConcoursService.creerConcours(m_Concours);
      }
      else if (ID_BOUTON_CREER_A_PARTIR_AUTRE_CONCOURS
            .equals(getIdBoutonClique()))
      {
         m_ConcoursService.creerConcours(m_Concours, m_AncienConcours);
      }
      else if (ID_BOUTON_MODIFIER.equals(getIdBoutonClique()))
      {
         m_ConcoursService.updateConcours(m_Concours);
      }
   }

   // ------------------------- Accesseurs public -------------------------

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
    * @return la date du concours à partir duquel on peut créer un autre
    *         concours
    */
   public Date getDate()
   {
      return m_Date;
   }

   /**
    * @param p_date
    *           la date du concours à partir duquel on peut créer un autre
    *           concours
    */
   public void setDate(Date p_date)
   {
      m_Date = p_date;
   }

   /**
    * @return la liste des clubs
    */
   public List<Club> getClubs()
   {
      return clubs;
   }

   /**
    * @param p_Clubs
    *           la liste des clubs
    */
   public void setClubs(List<Club> p_Clubs)
   {
      clubs = p_Clubs;
   }

   /**
    * @return le club sélectioné
    */
   public Club getClubSelectionne()
   {
      return m_ClubSelectionne;
   }

   /**
    * @param p_ClubSelectionne
    *           le club sélectioné
    */
   public void setClubSelectionne(Club p_ClubSelectionne)
   {
      m_ClubSelectionne = p_ClubSelectionne;
   }
}
