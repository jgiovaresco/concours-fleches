package fr.egiov.concoursfleches.tapestry.pages.concours;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Persist;

import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.domaine.model.Resultat;
import fr.egiov.concoursfleches.domaine.model.Score;
import fr.egiov.concoursfleches.enumerations.CategorieAge;
import fr.egiov.concoursfleches.enumerations.CategorieArcher;

/**
 * Page affichant les résultats d'un concours
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/imprimer_resultats.css" })
public class ImprimerResultats
{
   // ------------------------- Constantes private -------------------------

   // ------------------------- Membres private -------------------------

   /** le concours du formulaire */
   @Persist
   private Concours m_Concours;

   /**
    * variable utilisée par Tapestry pour parcourir la liste des catégories d'age
    */
   private CategorieAge m_CategorieAge;

   /**
    * variable utilisée par Tapestry pour parcourir la liste des catégories d'archer
    */
   private CategorieArcher m_CategorieArcher;

   /** les résultats du concours */
   @Persist
   private Resultat m_Resultat;

   /** variable utilisée par Tapestry pour afficher les scores */
   private Score m_Score;

   /** la position dans le classement */
   private Integer m_Position;

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
    * @return la liste des catégories d'age
    */
   public List<CategorieAge> getCategoriesAge()
   {
      return new ArrayList<CategorieAge>(Arrays.asList(CategorieAge.values()));
   }

   /**
    * @return la liste des catégories d'archer
    */
   public List<CategorieArcher> getCategoriesArcher()
   {
      return new ArrayList<CategorieArcher>(Arrays.asList(CategorieArcher.values()));
   }

   /**
    * @return la catégorie d'age
    */
   public CategorieAge getCategorieAge()
   {
      return m_CategorieAge;
   }

   /**
    * @param p_CategorieAge la catégorie d'age
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
    * @param p_CategorieArcher la catégorie d'archer
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
    * @param p_Resultat Les résultats du concours
    */
   public void setResultat(Resultat p_Resultat)
   {
      m_Resultat = p_Resultat;
   }

   /**
    * @return la liste des scores
    */
   public List<Score> getScores()
   {
      return m_Resultat.getClassement().get(m_CategorieAge).get(m_CategorieArcher);
   }

   /**
    * @return le score
    */
   public Score getScore()
   {
      return m_Score;
   }

   /**
    * @param p_Score le score
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
    * @param p_Position la position
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
    * @return <code>true</code> si la catégorie d'archer ne contient pas d'archer
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
}
