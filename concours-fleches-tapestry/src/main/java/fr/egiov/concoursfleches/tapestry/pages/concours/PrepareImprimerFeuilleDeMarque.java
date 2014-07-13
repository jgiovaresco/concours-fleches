package fr.egiov.concoursfleches.tapestry.pages.concours;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.model.Cible;
import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.services.CibleService;
import fr.egiov.concoursfleches.tapestry.model.CibleTapestry;
import fr.egiov.concoursfleches.tapestry.pages.AbstractPage;

/**
 * Page d'impression des feuilles de marque
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/selectionner_cibles_feuille_de_marque.css" })
public class PrepareImprimerFeuilleDeMarque extends AbstractPage
{

   // ------------------------- Constantes public -------------------------

   /** Lien Tout sélectionner */
   public static final String ID_LIEN_TOUT_SELECTIONNER = "toutselectionnerLien";

   /** Lien Tout désélectionner */
   public static final String ID_LIEN_TOUT_DESELECTIONNER = "toutdeselectionnerLien";

   // ------------------------- Membres private -------------------------

   /** le logger */
   private Logger m_Logger = LoggerFactory
         .getLogger(PrepareImprimerFeuilleDeMarque.class);

   /** Service Cible */
   @Inject
   private CibleService m_CibleService;

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

   /** Page ImprimerFeuilleDeMarque */
   @InjectPage
   private ImprimerFeuilleDeMarque m_ImprimerFeuilleDeMarque;

   // ------------------------- Methodes package scoped ------------------------

   /**
    * Méthode appelée après la soumission du formulaire concoursForm
    */
   Object onSubmitFromConcoursForm()
   {
      m_Logger.debug("onSubmitFromConcoursForm()");
      List<CibleTapestry> ciblesSelectionnees = new ArrayList<CibleTapestry>();
      for (CibleTapestry c : m_Cibles)
      {
         if (true == c.isSelectionnee())
         {
            m_Logger.debug("cible {} sélectionnée", c.getNom());
            ciblesSelectionnees.add(c);
         }
      }
      m_ImprimerFeuilleDeMarque.setConcours(m_Concours);
      m_ImprimerFeuilleDeMarque.setCibles(ciblesSelectionnees);
      return m_ImprimerFeuilleDeMarque;
   }

   /**
    * Méthode appelée lors du clic sur le lien "Tout Sélectionner".
    * 
    */
   @OnEvent(component = ID_LIEN_TOUT_SELECTIONNER)
   void onToutSelectionner()
   {
      m_Logger.debug("onToutSelectionner()");
      for (CibleTapestry c : m_Cibles)
      {
         c.setSelectionnee(true);
      }
   }

   /**
    * Méthode appelée lors du clic sur le lien "Tout Sélectionner".
    * 
    */
   @OnEvent(component = ID_LIEN_TOUT_DESELECTIONNER)
   void onToutDeSelectionner()
   {
      m_Logger.debug("onToutDeSelectionner()");
      for (CibleTapestry c : m_Cibles)
      {
         c.setSelectionnee(false);
      }
   }

   // ------------------------- Méthodes public ------------------------

   public void initialiser(Concours p_Concours)
   {
      m_Logger.debug("initialiser() - p_Concours : {}", p_Concours);
      m_Concours = p_Concours;
      m_Cibles = new ArrayList<CibleTapestry>();

      for (Cible c : m_CibleService.getCiblesConcours(m_Concours))
      {
         m_Cibles.add(new CibleTapestry(c));
      }
   }

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

   /**
    * @return le model de la grid
    */
   public BeanModel<CibleTapestry> getBeanModel()
   {
      BeanModel<CibleTapestry> model = getBeanModelSource().createDisplayModel(
            CibleTapestry.class, getMessages());

      model.getById("nom").sortable(false);
      return model;
   }
}
