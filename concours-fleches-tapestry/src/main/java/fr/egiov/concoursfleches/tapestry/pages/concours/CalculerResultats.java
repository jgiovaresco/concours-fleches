package fr.egiov.concoursfleches.tapestry.pages.concours;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.domaine.model.Resultat;
import fr.egiov.concoursfleches.domaine.model.Score;
import fr.egiov.concoursfleches.enumerations.CategorieAge;
import fr.egiov.concoursfleches.enumerations.CategorieArcher;
import fr.egiov.concoursfleches.exceptions.concours.ConcoursException;
import fr.egiov.concoursfleches.exceptions.concours.ConcoursNotFoundException;
import fr.egiov.concoursfleches.services.ConcoursService;

/**
 * Page affichant les résultats d'un concours
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/resultats.css" })
public class CalculerResultats
{
   // ------------------------- Constantes private -------------------------

   /** id du lien imprimer résultats */
   private static final String ID_LIEN_IMPRIMER_RESULTATS = "imprimerResultatsLien";

   // ------------------------- Membres private -------------------------

   /** le logger */
   private final Logger m_Logger = LoggerFactory
         .getLogger(CalculerResultats.class);

   /** Service Concours */
   @Inject
   private ConcoursService m_ConcoursService;

   /** Requete HTTP */
   @Inject
   private HttpServletRequest m_RequeteHttp;

   /** le concours du formulaire */
   @Persist
   private Concours m_Concours;

   /**
    * variable utilisée par Tapestry pour parcourir la liste des catégories
    * d'age
    */
   private CategorieAge m_CategorieAge;

   /**
    * variable utilisée par Tapestry pour parcourir la liste des catégories
    * d'archer
    */
   private CategorieArcher m_CategorieArcher;

   /** les résultats du concours */
   @Persist
   private Resultat m_Resultat;

   /** variable utilisée par Tapestry pour afficher les scores */
   private Score m_Score;

   /** la position dans le classement */
   private Integer m_Position;

   /** Page sélectionner les catégories d'age pour imprimer les résultats */
   @InjectPage
   private PrepareImprimerResultats m_PrepareImprimerResultats;

   // ------------------------- Méthodes public ------------------------

   /**
    * Initialise la page avec un Concours.
    * 
    * @param p_Concours
    *           Le concours dont on veut calculer les résultats
    */
   public void initialiser(Concours p_Concours)
   {
      m_Logger.debug("initialiser() - p_Concours : {}", p_Concours);
      m_Concours = p_Concours;

      try
      {
         m_Resultat = m_ConcoursService.calculerResultats(m_Concours.getId());
      }
      catch (ConcoursNotFoundException e)
      {
         // Ne devrait pas arriver
         m_Logger.error("Le concours n'existe pas", e);
         m_Resultat = null;
      }
      catch (ConcoursException e)
      {
         m_Logger.error("Erreur pendant le calcul du résulat", e);
         m_Resultat = null;
      }
   }

   // ------------------------- Methodes package scoped ------------------------

   /**
    * Méthode appelée lors du clic sur le lien "Imprimer Résultats".
    */
   @OnEvent(component = ID_LIEN_IMPRIMER_RESULTATS)
   Object onImprimerResultat()
   {
      m_Logger.debug("onImprimerResultat()");
      m_PrepareImprimerResultats.initialiser(m_Concours, m_Resultat);
      return m_PrepareImprimerResultats;
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
    * @param p_Concours
    *           le concours
    */
   public void setConcours(Concours p_Concours)
   {
      m_Concours = p_Concours;
   }

   /**
    * @return la liste des catégories d'age
    */
   public List<CategorieAge> getCategoriesAge()
   {
      return Arrays.asList(CategorieAge.values());
   }

   /**
    * @return la liste des catégories d'archer
    */
   public List<CategorieArcher> getCategoriesArcher()
   {
      return Arrays.asList(CategorieArcher.values());
   }

   /**
    * @return la catégorie d'age
    */
   public CategorieAge getCategorieAge()
   {
      return m_CategorieAge;
   }

   /**
    * @param p_CategorieAge
    *           la catégorie d'age
    */
   public void setCategorieAge(CategorieAge p_CategorieAge)
   {
      m_CategorieAge = p_CategorieAge;
   }

   /**
    * @return la catégorie d'archer
    */
   public CategorieArcher getCategorieArcher()
   {
      return m_CategorieArcher;
   }

   /**
    * @param p_CategorieArcher
    *           la catégorie d'archer
    */
   public void setCategorieArcher(CategorieArcher p_CategorieArcher)
   {
      m_CategorieArcher = p_CategorieArcher;
   }

   /**
    * @return les résultats
    */
   public Resultat getResultat()
   {
      return m_Resultat;
   }

   /**
    * @return la liste des scores
    */
   public List<Score> getScores()
   {
      return m_Resultat.getClassement().get(m_CategorieAge).get(
            m_CategorieArcher);
   }

   /**
    * @return le score
    */
   public Score getScore()
   {
      return m_Score;
   }

   /**
    * @param p_Score
    *           le score
    */
   public void setScore(Score p_Score)
   {
      m_Score = p_Score;
   }

   /**
    * @return la position
    */
   public Integer getPosition()
   {
      return m_Position;
   }

   /**
    * @param p_Position
    *           la position
    */
   public void setPosition(Integer p_Position)
   {
      m_Position = p_Position;
   }

   /**
    * @return le label de la position
    */
   public String getPositionLabel()
   {
      StringBuilder sb = new StringBuilder();

      if (0 == m_Position)
      {
         sb.append("1er");
      }
      else
      {
         sb.append(m_Position + 1).append("e");
      }
      return sb.toString();
   }

   /**
    * @return <code>true</code> si la catégorie d'age ne contient pas d'archer
    */
   public boolean isCategorieAgeNonVide()
   {
      boolean categorieNonVide = false;
      if (null != m_Resultat.getClassement().get(m_CategorieAge))
      {
         categorieNonVide = true;
      }
      return categorieNonVide;
   }

   /**
    * @return <code>true</code> si la catégorie d'archer ne contient pas
    *         d'archer
    */
   public boolean isCategorieArcherNonVide()
   {
      boolean categorieNonVide = false;
      if ((null != getScores()) && (getScores().size() > 0))
      {
         categorieNonVide = true;
      }
      return categorieNonVide;
   }

   /**
    * @return le context path
    */
   public String getContextPath()
   {
      return m_RequeteHttp.getContextPath();
   }
}
