package fr.egiov.concoursfleches.tapestry.pages.concours;

import java.util.List;

import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Zone;
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
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/detail_concours.css" })
@IncludeJavaScriptLibrary( { "context:scripts/detail_concours.js" })
public class DetailConcours extends AbstractPage
{
   // ------------------------- Constantes private -------------------------

   /** id du bouton ajouter une cible */
   private static final String ID_BOUTON_AJOUTER_CIBLE = "ajouterCibleBouton";

   /** id du bouton enregistrer */
   private static final String ID_BOUTON_ENREGISTRER = "enregistrerBouton";

   /** id du lien supprimer cible */
   private static final String ID_LIEN_SUPPRIMER_CIBLE = "supprimerCibleLien";

   /** id du lien calculer résultats */
   private static final String ID_LIEN_CALCULER_RESULTATS = "calculerResultatsLien";
   
   /** id du lien imprimer feuille de marque */
   private static final String ID_LIEN_IMPRIMER_FEUILLE_DE_MARQUE = "imprimerFeuilleDeMarqueLien";

   // ------------------------- Membres private -------------------------

   /** le logger */
   private final Logger m_Logger = LoggerFactory.getLogger(DetailConcours.class);

   /** Service Concours */
   @Inject
   private ConcoursService m_ConcoursService;

   /** l'id du concours affiché */
   private Long m_ConcoursId;

   /** Service Cible */
   @Inject
   private CibleService m_CibleService;

   /** Page ajouter cible */
   @InjectPage
   private FormCible m_AjouterCiblePage;

   /** Page calculant les résultats */
   @InjectPage
   private CalculerResultats m_CalculerResultats;

   /** Page sélectionner cible pour imprimer feuille de marque */
   @InjectPage
   private PrepareImprimerFeuilleDeMarque m_PrepareImprimerFeuilleDeMarque;

   /** le concours du formulaire */
   @Persist
   private Concours m_Concours;

   /** La zone */
   @InjectComponent("formZone")
   private Zone m_FormZone;

   /** les cibles du concours */
   private List<Cible> m_Cibles;

   /** variable utilisée par Tapestry pour afficher la liste des cibles */
   private Cible m_Cible;

   // ------------------------- Methodes package scoped ------------------------

   /**
    * Méthode appelée lors de l'activation de la page
    * @param p_Code le paramètre de requete permettant de savoir quel est le type du formulaire
    */
   void onActivate(Long p_ConcoursId)
   {
      m_ConcoursId = p_ConcoursId;
      try
      {
         m_Concours = m_ConcoursService.findConcours(m_ConcoursId);
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

   /**
    * Méthode appelée lors de la préparation de la page
    */
   void onPrepare()
   {
      m_Cibles = m_CibleService.getCiblesConcours(m_Concours);
   }

   /**
    * Méthode appelée lors du clic sur le bouton ajouter cible
    */
   @OnEvent(value = "selected", component = ID_BOUTON_AJOUTER_CIBLE)
   void onAjouterCibleBouton()
   {
      m_Logger.debug("onAjouterCibleBouton()");
      m_AjouterCiblePage.setConcours(m_Concours);
      m_AjouterCiblePage.onActivate(null);
      setIdBoutonClique(ID_BOUTON_AJOUTER_CIBLE);
   }

   /**
    * Méthode appelée lors du clic sur le bouton ajouter cible
    */
   @OnEvent(value = "selected", component = ID_BOUTON_ENREGISTRER)
   void onEnregistrerBouton()
   {
      m_Logger.debug("onEnregistrerBouton()");
      setIdBoutonClique(ID_BOUTON_ENREGISTRER);
   }

   /**
    * Méthode appelée après la soumission du formulaire concoursForm
    */
   Object onSubmitFromConcoursForm()
   {
      m_Logger.debug("onSubmitFromConcoursForm()");
      Object pageSuivante = m_AjouterCiblePage;
      if (ID_BOUTON_ENREGISTRER.equals(getIdBoutonClique()))
      {
         // pageSuivante = DetailConcours.class;
         pageSuivante = m_FormZone.getBody();
      }
      return pageSuivante;
   }

   /**
    * Méthode appelée lors du succes de la validation des champs du formulaire
    */
   void onSuccess()
   {
      m_Logger.debug("onSuccess() : m_IdBoutonClique = {}", getIdBoutonClique());
      if (ID_BOUTON_ENREGISTRER.equals(getIdBoutonClique()))
      {
         m_CibleService.updateCibles(m_Cibles);
      }
   }

   /**
    * Méthode appelée lors du clic sur le lien "Supprimer cible".
    * @param p_CibleId l'id de la cible
    */
   @OnEvent(component = ID_LIEN_SUPPRIMER_CIBLE)
   void onSupprimerCible(Long p_CibleId)
   {
      m_Logger.debug("onSupprimerCible() : cibleId = {}", p_CibleId);
      m_CibleService.supprimerCible(p_CibleId);
   }

   /**
    * Méthode appelée lors du clic sur le lien "Calculer Résultats".
    */
   @OnEvent(component = ID_LIEN_CALCULER_RESULTATS)
   Object onCalculerResultat()
   {
      m_Logger.debug("onCalculerResultats()");
      m_CalculerResultats.initialiser(m_Concours);
      return m_CalculerResultats;
   }
   
   /**
    * Méthode appelée lors du clic sur le lien "Imprimer feuilles de marque".
    */
   @OnEvent(component = ID_LIEN_IMPRIMER_FEUILLE_DE_MARQUE)
   Object onImprimerFeuilleDeMarque()
   {
      m_Logger.debug("onImprimerFeuilleDeMarque()");
      m_PrepareImprimerFeuilleDeMarque.initialiser(m_Concours);
      return m_PrepareImprimerFeuilleDeMarque;
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
    * @param p_Concours le concours
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
    * @return Le nombre de cibles
    */
   public int getNbCibles()
   {
      int nbCibles = 0;
      if (null != m_Cibles)
      {
         nbCibles = m_Cibles.size();
      }
      return nbCibles;
   }

   /**
    * @return la cible
    */
   public Cible getCible()
   {
      return m_Cible;
   }

   /**
    * @param p_Cible la cible
    */
   public void setCible(Cible p_Cible)
   {
      m_Cible = p_Cible;
   }

   /**
    * @param p_ConcoursId l'id du concours affiché
    */
   public void setConcoursId(Long p_ConcoursId)
   {
      m_ConcoursId = p_ConcoursId;
   }

   /**
    * @return le model de la grid
    */
   public BeanModel<Cible> getBeanModel()
   {
      BeanModel<Cible> model = getBeanModelSource().createDisplayModel(Cible.class, getMessages());

      model.getById("nom").sortable(false);
      model.getById("serie1").sortable(false);
      model.getById("serie2").sortable(false);
      model.getById("total").sortable(false);
      model.getById("departage").sortable(false);
      return model;
   }
}
