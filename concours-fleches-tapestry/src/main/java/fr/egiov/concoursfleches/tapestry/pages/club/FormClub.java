package fr.egiov.concoursfleches.tapestry.pages.club;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.model.Club;
import fr.egiov.concoursfleches.services.ClubService;
import fr.egiov.concoursfleches.tapestry.pages.AbstractFormPage;
import fr.egiov.concoursfleches.tapestry.pages.TypeFormulaire;

/**
 * Page permettant de créer ou modifier un {@link Club}
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/form_club.css" })
public class FormClub extends AbstractFormPage
{
   // ------------------------- Constantes private -------------------------

   /** code pour creer un nouvel archer */
   private static final String CODE_CREER = "new";

   /** id du bouton créer */
   private static final String ID_BOUTON_CREER = "creerBouton";

   /** id du bouton modifier */
   private static final String ID_BOUTON_MODIFIER = "modifierBouton";

   /** id du bouton reset */
   private static final String ID_BOUTON_REINITIALISER = "reinitialiserBouton";

   // ------------------------- Membres private -------------------------

   private static final Logger LOGGER = LoggerFactory.getLogger(FormClub.class);

   /** Service club */
   @Inject
   private ClubService m_ClubService;

   /** le club du formulaire */
   @Persist
   private Club m_Club;

   /** référence au formulaire de la page */
   @Component
   private Form clubForm;

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
      LOGGER.debug("onActivate : p_Code = {}", p_Code);

      if (CODE_CREER.equals(p_Code))
      {
         m_Club = new Club();
         setTypeFormulaire(TypeFormulaire.CREATION);
      }
      else
      {
         Long clubId = null;
         try
         {
            clubId = Long.parseLong(p_Code);
            // p_Code correspond à un nombre. Ce doit être un identifiant
            m_Club = m_ClubService.findClub(clubId);
            setTypeFormulaire(TypeFormulaire.MODIFICATION);
         }
         catch (NumberFormatException e)
         {
            // Le code ne correspond pas à un nombre. Le formulaire sera de type
            // CREATION
            m_Club = new Club();
            setTypeFormulaire(TypeFormulaire.CREATION);
         }
      }
   }

   /**
    * Méthode appelée après la soumission du formulaire
    */
   Object onSubmitFromClubForm()
   {
      LOGGER.debug("onSubmitFromClubForm()");

      Class<?> pageSuivante = FormClub.class;
      if (null != m_Club.getId())
      {
         pageSuivante = ListeClubs.class;
      }
      return pageSuivante;
   }

   /**
    * Méthode appelée lors du clic sur le bouton créer
    */
   @OnEvent(value = "selected", component = ID_BOUTON_CREER)
   void onCreerButton()
   {
      LOGGER.debug("onCreerButton()");
      setIdBoutonClique(ID_BOUTON_CREER);
   }

   /**
    * Méthode appelée lors du clic sur le bouton réinitiliser
    */
   @OnEvent(value = "selected", component = ID_BOUTON_REINITIALISER)
   void onReinitialiserButton()
   {
      setIdBoutonClique(ID_BOUTON_REINITIALISER);
      m_Club.setNom(null);
      m_Club.setVille(null);
      m_Club.setDepartement(null);
   }

   /**
    * Méthode appelée lors du clic sur le bouton modifier
    */
   @OnEvent(value = "selected", component = ID_BOUTON_MODIFIER)
   void onModifierBouton()
   {
      LOGGER.debug("onModifierBouton()");
      setIdBoutonClique(ID_BOUTON_MODIFIER);
   }

   /**
    * Méthode appelée lors du succes de la validation des champs du formulaire
    */
   void onSuccess()
   {
      LOGGER.debug("onSuccess() - {}", getIdBoutonClique());
      if (ID_BOUTON_CREER.equals(getIdBoutonClique()))
      {
         m_ClubService.createClub(m_Club);
      }
      else if (ID_BOUTON_MODIFIER.equals(getIdBoutonClique()))
      {
         m_ClubService.updateClub(m_Club);
      }
      setIdBoutonClique(null);
   }

   /**
    * Méthode appelée en cas d'échec de la validation des champs du formulaire.
    */
   void onFailure()
   {
      LOGGER.debug("onFailure()");
      // Si l'user à cliquer sur le bouton réinitialisé alors on vide les
      // erreurs.
      if (ID_BOUTON_REINITIALISER.equals(getIdBoutonClique()))
      {
         clubForm.clearErrors();
      }
      setIdBoutonClique(null);
   }

   // ------------------------- Accesseurs public -------------------------

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
}
