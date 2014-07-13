package fr.egiov.concoursfleches.tapestry.pages.concours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.domaine.model.Resultat;
import fr.egiov.concoursfleches.enumerations.CategorieAge;
import fr.egiov.concoursfleches.tapestry.model.CategorieAgeTapestry;
import fr.egiov.concoursfleches.tapestry.pages.AbstractPage;

/**
 * Page de préparation pour l'impression des résultats
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/selectionner_categorie_resultats.css" })
public class PrepareImprimerResultats extends AbstractPage
{
   // ------------------------- Constantes public -------------------------

   /** Lien Tout sélectionner */
   public static final String ID_LIEN_TOUT_SELECTIONNER = "toutselectionnerLien";

   /** Lien Tout désélectionner */
   public static final String ID_LIEN_TOUT_DESELECTIONNER = "toutdeselectionnerLien";

   // ------------------------- Membres private -------------------------

   /** le logger */
   private final Logger m_Logger = LoggerFactory
         .getLogger(PrepareImprimerResultats.class);

   /** le concours du formulaire */
   @Persist
   private Concours m_Concours;

   /** les catégories du concours */
   @Persist
   private List<CategorieAgeTapestry> m_Categories;

   /** variable utilisée par Tapestry pour afficher la liste des categories */
   private CategorieAgeTapestry m_Categorie;

   /** les résultats du concours */
   @Persist
   private Resultat m_Resultat;

   /** référence au formulaire de la page */
   @Component(id = "preparerImprimerResultatsForm")
   private Form m_Form;

   /** Page {@link ImprimerResultats} */
   @InjectPage
   private ImprimerResultats m_ImprimerResultats;

   // ------------------------- Methodes package scoped ------------------------

   /**
    * Méthode appelée après la soumission du formulaire.
    * <ul>
    * <li>Si au moins une catégorie sélectionnée, on renvoie vers la page
    * {@link ImprimerResultats}</li>
    * <li>Sinon on reste sur la même page</li>
    * </ul>
    */
   @OnEvent(value = "submit", component = "preparerImprimerResultatsForm")
   Object onSubmit()
   {
      Object pageARenvoyer = PrepareImprimerResultats.class;

      m_Logger.debug("onSubmit()");

      Resultat resultats = m_Resultat.cloner();
      for (CategorieAgeTapestry c : m_Categories)
      {
         if (false == c.isSelectionnee())
         {
            m_Logger.debug("categorie {} sélectionnée", c.getCategorie());
            resultats.getClassement().remove(c.getCategorie());
         }
      }

      if (0 == resultats.getClassement().size())
      {
         // Pas de catégorie sélectionnée, on affiche une erreur
         m_Form.recordError(getMessages().get("categorie-obligatoire"));
      }
      else
      {
         m_ImprimerResultats.setConcours(m_Concours);
         m_ImprimerResultats.setResultat(resultats);
         pageARenvoyer = m_ImprimerResultats;
      }
      return pageARenvoyer;
   }

   /**
    * Méthode appelée lors du clic sur le lien "Tout Sélectionner".
    */
   @OnEvent(component = ID_LIEN_TOUT_SELECTIONNER)
   void onToutSelectionner()
   {
      m_Logger.debug("onToutSelectionner()");
      for (CategorieAgeTapestry c : m_Categories)
      {
         c.setSelectionnee(true);
      }
   }

   /**
    * Méthode appelée lors du clic sur le lien "Tout Sélectionner".
    */
   @OnEvent(component = ID_LIEN_TOUT_DESELECTIONNER)
   void onToutDeSelectionner()
   {
      m_Logger.debug("onToutDeSelectionner()");
      for (CategorieAgeTapestry c : m_Categories)
      {
         c.setSelectionnee(false);
      }
   }

   // ------------------------- Méthodes public ------------------------

   /**
    * Initialise la page avec un Concours.
    * 
    * @param p_Concours
    *           Le concours dont on veut calculer les résultats
    * @param p_Resultat
    *           Les résultats du concours
    */
   public void initialiser(Concours p_Concours, Resultat p_Resultat)
   {
      m_Logger.debug("initialiser() - p_Concours : {}, p_Resultat : {}",
            p_Concours, p_Resultat);

      m_Concours = p_Concours;
      m_Resultat = p_Resultat;

      // Initialise la liste des catégories
      m_Categories = new ArrayList<CategorieAgeTapestry>();

      for (CategorieAge c : m_Resultat.getClassement().keySet())
      {
         m_Categories.add(new CategorieAgeTapestry(c));

         Collections.sort(m_Categories);
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
    * @return les catégories du concours
    */
   public List<CategorieAgeTapestry> getCategories()
   {
      return m_Categories;
   }

   /**
    * @return la catégorie
    */
   public CategorieAgeTapestry getCategorie()
   {
      return m_Categorie;
   }

   /**
    * @param p_Categories
    *           la catégorie
    */
   public void setCategorie(CategorieAgeTapestry p_Categorie)
   {
      m_Categorie = p_Categorie;
   }

   /**
    * @return le model de la grid
    */
   public BeanModel<CategorieAgeTapestry> getBeanModel()
   {
      BeanModel<CategorieAgeTapestry> model = getBeanModelSource()
            .createDisplayModel(CategorieAgeTapestry.class, getMessages());

      model.getById("categorie").sortable(false);
      return model;
   }
}
