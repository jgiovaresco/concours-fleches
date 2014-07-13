package fr.egiov.concoursfleches.tapestry.pages.archer;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.RadioGroup;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

import fr.egiov.concoursfleches.domaine.model.Archer;
import fr.egiov.concoursfleches.domaine.model.Club;
import fr.egiov.concoursfleches.enumerations.Genre;
import fr.egiov.concoursfleches.enumerations.TypeArc;
import fr.egiov.concoursfleches.services.ArcherService;
import fr.egiov.concoursfleches.services.ClubService;
import fr.egiov.concoursfleches.tapestry.composants.InjectSelectionModel;
import fr.egiov.concoursfleches.tapestry.pages.AbstractFormPage;
import fr.egiov.concoursfleches.tapestry.pages.TypeFormulaire;
import fr.egiov.concoursfleches.tapestry.pages.club.ListeClubs;

/**
 * Page permettant de créer ou modifier un {@link Archer}
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/form_archer.css" })
public class FormArcher extends AbstractFormPage
{
   // ------------------------- Constantes private -------------------------

   /** id du lien sélectionné Club */
   private static final String ID_LIEN_SELECTIONNER_CLUB = "selectionnerClubLien";

   /** id du bouton créer */
   private static final String ID_BOUTON_CREER = "creerBouton";

   /** id du bouton modifier */
   private static final String ID_BOUTON_MODIFIER = "modifierBouton";

   /** id du bouton reset */
   private static final String ID_BOUTON_REINITIALISER = "reinitialiserBouton";

   // ------------------------- Membres private -------------------------

   /** Service archer */
   @Inject
   private ArcherService m_ArcherService;

   /** Service club */
   @Inject
   private ClubService m_ClubService;

   /** la page de sélection d'un club */
   @InjectPage
   private ListeClubs m_ListeClubsPage;

   /** référence au formulaire de la page */
   @Component(id = "archerForm")
   private Form m_ArcherForm;

   /** Radiogroup correspond au Genre */
   @Component(id = "genre")
   private RadioGroup m_GenreRadioGroup;

   /** liste des club */
   @InjectSelectionModel(labelField = "nomComplet", idField = "id")
   private List<Club> clubs = new ArrayList<Club>();

   /** le genre sélectioné */
   @Persist
   private Genre m_GenreSelectionne;

   /** le club sélectioné */
   @Persist
   private Club m_ClubSelectionne;

   /** le type d'arc sélectionné */
   @Persist
   private TypeArc m_TypeArcSelectionne;

   /** l'archer du formulaire */
   @Persist
   private Archer m_Archer;

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
         m_Archer = new Archer();
         m_ClubSelectionne = null;
         m_TypeArcSelectionne = null;
         m_GenreSelectionne = null;
         setTypeFormulaire(TypeFormulaire.CREATION);
      }
      else
      {
         Long archerId = null;
         try
         {
            archerId = Long.parseLong(p_Code);
            // p_Code correspond à un nombre. Ce doit être un identifiant
            // d'archer
            m_Archer = m_ArcherService.findArcher(archerId);
            m_ClubSelectionne = m_Archer.getClub();
            m_TypeArcSelectionne = m_Archer.getTypeArc();
            m_GenreSelectionne = m_Archer.getGenre();
            setTypeFormulaire(TypeFormulaire.MODIFICATION);
         }
         catch (NumberFormatException e)
         {
            // Le code ne correspond pas à un nombre. Le formulaire sera de type
            // CREATION
            m_Archer = new Archer();
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
    * Méthode appelée lors de la validation du champ Genre
    */
   void onValidateFromGenre()
   {
      if (null == m_GenreSelectionne)
      {
         m_ArcherForm.recordError(m_GenreRadioGroup, getMessages().get(
               "genre-obligatoire"));
      }
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
    * Méthode appelée après la soumission du formulaire
    */
   Object onSubmitFromArcherForm()
   {
      Class<?> pageSuivante = FormArcher.class;
      if (null != m_Archer.getId())
      {
         pageSuivante = ListeArchers.class;
      }
      return pageSuivante;
   }

   /**
    * Méthode appelée lors du clic sur le bouton créer
    */
   @OnEvent(value = "selected", component = ID_BOUTON_CREER)
   void onCreerBouton()
   {
      setIdBoutonClique(ID_BOUTON_CREER);
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
    * Méthode appelée lors du clic sur le bouton réinitiliser
    */
   @OnEvent(value = "selected", component = ID_BOUTON_REINITIALISER)
   void onReinitialiserBouton()
   {
      setIdBoutonClique(ID_BOUTON_REINITIALISER);
      m_Archer.setNom(null);
      m_Archer.setPrenom(null);
      m_Archer.setAge(null);
      m_Archer.setAdresse(null);
      m_Archer.setCodePostal(null);
      m_Archer.setVille(null);
      m_Archer.setNumeroLicence(null);
      m_ClubSelectionne = null;
      m_TypeArcSelectionne = null;
      m_GenreSelectionne = null;
   }

   /**
    * Méthode appelée lors du succes de la validation des champs du formulaire
    */
   void onSuccess()
   {
      m_Archer.setGenre(m_GenreSelectionne);
      m_Archer.setClub(m_ClubSelectionne);
      m_Archer.setTypeArc(m_TypeArcSelectionne);

      if (ID_BOUTON_CREER.equals(getIdBoutonClique()))
      {
         m_ArcherService.createArcher(m_Archer);
      }
      else if (ID_BOUTON_MODIFIER.equals(getIdBoutonClique()))
      {
         m_ArcherService.updateArcher(m_Archer);
      }
      setIdBoutonClique(null);
   }

   /**
    * Méthode appelée en cas d'échec de la validation des champs du formulaire.
    */
   void onFailure()
   {
      // Si l'user à cliquer sur le bouton réinitialisé alors on vide les
      // erreurs.
      if (ID_BOUTON_REINITIALISER.equals(getIdBoutonClique()))
      {
         m_ArcherForm.clearErrors();
      }
      setIdBoutonClique(null);
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * @return le genre sélectionné
    */
   public Genre getGenreSelectionne()
   {
      return m_GenreSelectionne;
   }

   /**
    * @param p_GenreSelectionne
    *           le genre sélectionné
    */
   public void setGenreSelectionne(Genre p_GenreSelectionne)
   {
      m_GenreSelectionne = p_GenreSelectionne;
   }

   /**
    * @return {@link Sexe#HOMME>}
    */
   public Genre getHomme()
   {
      return Genre.HOMME;
   }

   /**
    * @return {@link Sexe#FEMME>}
    */
   public Genre getFemme()
   {
      return Genre.FEMME;
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

   /**
    * @return le select model pour le Tir à l'arc
    */
   public SelectModel getTypeArcSelectModel()
   {
      return new EnumSelectModel(TypeArc.class, getMessages());
   }

   /**
    * @return le type d'arc sélectionné
    */
   public TypeArc getTypeArcSelectionne()
   {
      return m_TypeArcSelectionne;
   }

   /**
    * @param p_TypeArcSelectionne
    *           le type d'arc sélectionné
    */
   public void setTypeArcSelectionne(TypeArc p_TypeArcSelectionne)
   {
      m_TypeArcSelectionne = p_TypeArcSelectionne;
   }

   /**
    * @return l'archer du formulaire
    */
   public Archer getArcher()
   {
      return m_Archer;
   }
}
