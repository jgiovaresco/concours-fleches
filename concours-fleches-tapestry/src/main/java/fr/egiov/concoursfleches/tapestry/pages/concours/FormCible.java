package fr.egiov.concoursfleches.tapestry.pages.concours;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.model.Archer;
import fr.egiov.concoursfleches.domaine.model.Cible;
import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.domaine.model.Participant;
import fr.egiov.concoursfleches.services.ArcherService;
import fr.egiov.concoursfleches.services.CibleService;
import fr.egiov.concoursfleches.tapestry.composants.InjectSelectionModel;
import fr.egiov.concoursfleches.tapestry.pages.AbstractFormPage;
import fr.egiov.concoursfleches.tapestry.pages.TypeFormulaire;
import fr.egiov.concoursfleches.tapestry.pages.archer.ListeArchers;

/**
 * Page permettant d'ajouter une {@link Cible}
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/ajouter_cible.css" })
public class FormCible extends AbstractFormPage
{
   // ------------------------- Constantes private -------------------------

   /** id du lien sélectionné Archer */
   private static final String ID_LIEN_SELECTIONNER_ARCHER = "selectionnerArcherLien";

   /** id du bouton ajouter */
   private static final String ID_BOUTON_AJOUTER = "ajouterBouton";

   /** id du bouton modifier */
   private static final String ID_BOUTON_MODIFIER = "modifierBouton";

   /** id du bouton reset */
   private static final String ID_BOUTON_REINITIALISER = "reinitialiserBouton";

   // ------------------------- Membres private -------------------------

   private final Logger m_Logger = LoggerFactory.getLogger(FormCible.class);

   /** Service archer */
   @Inject
   private ArcherService m_ArcherService;

   /** Service cible */
   @Inject
   private CibleService m_CibleService;

   /** la cible du formulaire */
   @Persist
   private Cible m_Cible;

   /** le concours dont on veut ajouter une cible */
   @Persist
   private Concours m_Concours;

   /** Page liste des archers */
   @InjectPage
   private ListeArchers m_ListeArchersPage;

   /** référence au formulaire de la page */
   @Component(id = "cibleForm")
   private Form m_CibleForm;

   /** liste des club */
   @InjectSelectionModel(labelField = "nomComplet", idField = "id")
   private List<Archer> archers = new ArrayList<Archer>();

   /** l'archer sélectionné */
   @Persist
   private Archer m_ArcherSelectionne;

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
      m_Logger.debug("onActivate() - p_Code : {}", p_Code);
      archers = m_ArcherService.findAllArchers();
      if (null != p_Code)
      {
         Long cibleId = null;
         try
         {
            cibleId = Long.parseLong(p_Code);
            // p_Code correspond à un nombre. Ce doit être un identifiant
            // de cible
            m_Cible = m_CibleService.findCible(cibleId);
            m_ArcherSelectionne = m_ArcherService.findArcher(m_Cible
                  .getParticipant().getArcher().getId());
            setTypeFormulaire(TypeFormulaire.MODIFICATION);
         }
         catch (NumberFormatException e)
         {
            // Le code ne correspond pas à un nombre. Le formulaire sera de type
            // CREATION
            m_Cible = new Cible();
            setTypeFormulaire(TypeFormulaire.CREATION);
         }
      }
      else
      {
         m_Cible = new Cible();
         m_ArcherSelectionne = null;
         setTypeFormulaire(TypeFormulaire.CREATION);
      }
   }

   /**
    * Méthode appelée lors de la préparation de la page
    */
   void onPrepare()
   {
      archers = m_ArcherService.findAllArchers();
   }

   /**
    * Méthode appelée après la soumission du formulaire
    */
   Object onSubmitFromCibleForm()
   {
      m_Logger.debug("onSubmitFromCibleForm()");
      Class<?> pageSuivante = FormCible.class;
      if (null != m_Cible.getId())
      {
         pageSuivante = DetailConcours.class;
         m_Cible = null;
      }
      return pageSuivante;
   }

   /**
    * Méthode appelée lors du clic sur le bouton créer
    */
   @OnEvent(value = "selected", component = ID_BOUTON_AJOUTER)
   void onAjouterButton()
   {
      m_Logger.debug("onAjouterButton()");
      setIdBoutonClique(ID_BOUTON_AJOUTER);
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
   void onReinitialiserButton()
   {
      m_Logger.debug("onReinitialiserButton()");
      setIdBoutonClique(ID_BOUTON_REINITIALISER);
      m_Cible.setNom(null);
      m_ArcherSelectionne = null;
   }

   /**
    * Méthode appelée lors du succes de la validation des champs du formulaire
    */
   void onSuccess()
   {
      m_Logger
            .debug("onSuccess() : m_IdBoutonClique = {}", getIdBoutonClique());

      Participant p = new Participant(m_ArcherSelectionne);

      if (ID_BOUTON_AJOUTER.equals(getIdBoutonClique()))
      {
         m_Cible.setParticipant(p);
         m_Cible.setConcours(m_Concours);
         m_CibleService.createCible(m_Cible);
      }
      else if (ID_BOUTON_MODIFIER.equals(getIdBoutonClique()))
      {
         if (false == m_Cible.getParticipant().getArcher()
               .equals(p.getArcher()))
         {
            m_Cible.setParticipant(p);
         }
         m_CibleService.updateCible(m_Cible);
      }

      setIdBoutonClique(null);
   }

   /**
    * Méthode appelée en cas d'échec de la validation des champs du formulaire.
    */
   void onFailure()
   {
      m_Logger.debug("onFailure()");
      // Si l'user à cliquer sur le bouton réinitialisé alors on vide les
      // erreurs.
      if (ID_BOUTON_REINITIALISER.equals(getIdBoutonClique()))
      {
         m_CibleForm.clearErrors();
      }
      setIdBoutonClique(null);
   }

   /**
    * Méthode appelée lors du clic sur le lien "Sélectionner un archer".
    * 
    * @return {@link ListeArchers}
    */
   @OnEvent(component = ID_LIEN_SELECTIONNER_ARCHER)
   Object onSelectionnerArcher()
   {
      m_ListeArchersPage.setAction(ID_LIEN_SELECTIONNER_ARCHER);
      return m_ListeArchersPage;
   }

   // ------------------------- Accesseurs public -------------------------

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

   /**
    * @return la liste des archers
    */
   public List<Archer> getArchers()
   {
      return archers;
   }

   /**
    * @return l'archer sélectionné
    */
   public Archer getArcherSelectionne()
   {
      return m_ArcherSelectionne;
   }

   /**
    * @param p_ArcherSelectionne
    *           l'archer sélectionné
    */
   public void setArcherSelectionne(Archer p_ArcherSelectionne)
   {
      m_ArcherSelectionne = p_ArcherSelectionne;
   }

   /**
    * @param p_Concours
    *           le concours dont on veut ajouter une cible
    */
   public void setConcours(Concours p_Concours)
   {
      m_Concours = p_Concours;
   }
}
