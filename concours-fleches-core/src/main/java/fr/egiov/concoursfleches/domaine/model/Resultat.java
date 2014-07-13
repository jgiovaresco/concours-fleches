package fr.egiov.concoursfleches.domaine.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fr.egiov.collections.SortedList;
import fr.egiov.concoursfleches.enumerations.CategorieAge;
import fr.egiov.concoursfleches.enumerations.CategorieArcher;
import fr.egiov.concoursfleches.exceptions.concours.AjouterScoreException;
import fr.egiov.concoursfleches.exceptions.helpers.CategorieArcherException;
import fr.egiov.concoursfleches.helpers.CategorieArcherHelper;

/**
 * Définit le résultat d'un concours
 * 
 * @author giovarej
 */
public class Resultat implements Serializable
{
   /** serial version UID */
   private static final long serialVersionUID = 1L;

   // ------------------------- Membres private -------------------------

   /** le concours */
   private Concours m_Concours;

   /** le classement */
   private Map<CategorieAge, Map<CategorieArcher, SortedList<Score>>> m_Classement = new HashMap<CategorieAge, Map<CategorieArcher, SortedList<Score>>>();

   // ------------------------- Méthodes public -------------------------

   /**
    * Ajoute un score au classement.
    * 
    * @param p_Score
    *           le score
    * @throws AjouterScoreException
    *            en cas d'erreur lors de l'ajout d'un score
    */
   public void ajouterScore(Score p_Score) throws AjouterScoreException
   {
      try
      {
         Map<CategorieArcher, SortedList<Score>> listeCategorieArcher = m_Classement
               .get(p_Score.getParticipant().getCategorieAge());

         SortedList<Score> classement = null;

         CategorieArcher categorieArcher = CategorieArcherHelper.getCategorie(
               p_Score.getParticipant().getTypeArc(), p_Score.getParticipant()
                     .getArcher());

         // il n'y a pas encore de classement dans la catégorie de l'archer
         if (null == listeCategorieArcher)
         {
            listeCategorieArcher = new HashMap<CategorieArcher, SortedList<Score>>();
            classement = new SortedList<Score>();
            classement.add(p_Score);

            listeCategorieArcher.put(categorieArcher, classement);
            m_Classement.put(p_Score.getParticipant().getCategorieAge(),
                  listeCategorieArcher);
         }
         // Un classement existe on rajoute l'archer
         else
         {
            classement = listeCategorieArcher.get(categorieArcher);
            if (null == classement)
            {
               classement = new SortedList<Score>();
               classement.add(p_Score);

               listeCategorieArcher.put(categorieArcher, classement);
            }
            else
            {
               while (true == classement.contains(p_Score))
               {
                  p_Score.getCible().setDepartage(p_Score.getDepartage() + 1);
               }
               classement.add(p_Score);
            }
         }
      }
      catch (CategorieArcherException e)
      {
         throw new AjouterScoreException(e);
      }
   }

   /**
    * Duplique l'objet {@link Resultat}
    * 
    * @return Un duplicatat de {@link Resultat}
    */
   public Resultat cloner()
   {
      Resultat resultat = new Resultat();
      resultat.setConcours(m_Concours);
      resultat
            .setClassement(new HashMap<CategorieAge, Map<CategorieArcher, SortedList<Score>>>(
                  m_Classement));
      return resultat;
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
    * @return le classement
    */
   public Map<CategorieAge, Map<CategorieArcher, SortedList<Score>>> getClassement()
   {
      return m_Classement;
   }

   /**
    * @return le classement
    */
   protected void setClassement(
         Map<CategorieAge, Map<CategorieArcher, SortedList<Score>>> p_Classement)
   {
      m_Classement = p_Classement;
   }
}
