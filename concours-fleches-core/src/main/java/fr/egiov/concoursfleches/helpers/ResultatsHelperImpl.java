package fr.egiov.concoursfleches.helpers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import fr.egiov.collections.SortedList;
import fr.egiov.concoursfleches.domaine.model.Resultat;
import fr.egiov.concoursfleches.domaine.model.Score;
import fr.egiov.concoursfleches.enumerations.CategorieAge;
import fr.egiov.concoursfleches.enumerations.CategorieArcher;
import fr.egiov.concoursfleches.exceptions.concours.AjouterScoreException;
import fr.egiov.concoursfleches.exceptions.helpers.CategorieArcherException;

/**
 * Helper fournissant des méthodes pour gérer des {@link Resultat}
 * 
 * @author giovarej
 */
@Component("resultatsHelper")
public class ResultatsHelperImpl implements ResultatsHelper
{
   // ------------------------- Membres private -------------------------

   // ------------------------- Méthodes public -------------------------

   /**
    * {@inheritDoc}
    * 
    * @see ResultatsHelper#ajouterScore(Resultat, Score)
    */
   @Override
   public void ajouterScore(Resultat p_Resultat, Score p_Score)
         throws AjouterScoreException
   {
      try
      {
         Map<CategorieAge, Map<CategorieArcher, SortedList<Score>>> classement = p_Resultat
               .getClassement();
         Map<CategorieArcher, SortedList<Score>> listeCategorieArcher = classement
               .get(p_Score.getParticipant().getCategorieAge());

         SortedList<Score> classementScore = null;

         CategorieArcher categorieArcher = CategorieArcherHelper.getCategorie(
               p_Score.getParticipant().getTypeArc(), p_Score.getParticipant()
                     .getArcher());

         // il n'y a pas encore de classement dans la catégorie de l'archer
         if (null == listeCategorieArcher)
         {
            listeCategorieArcher = new HashMap<CategorieArcher, SortedList<Score>>();
            classementScore = new SortedList<Score>();
            classementScore.add(p_Score);

            listeCategorieArcher.put(categorieArcher, classementScore);
            classement.put(p_Score.getParticipant().getCategorieAge(),
                  listeCategorieArcher);
         }
         // Un classement existe on rajoute l'archer
         else
         {
            classementScore = listeCategorieArcher.get(categorieArcher);
            if (null == classementScore)
            {
               classementScore = new SortedList<Score>();
               classementScore.add(p_Score);

               listeCategorieArcher.put(categorieArcher, classementScore);
            }
            else
            {
               while (true == classementScore.contains(p_Score))
               {
                  p_Score.getCible().setDepartage(p_Score.getDepartage() + 1);
               }

               classementScore.add(p_Score);
            }
         }
      }
      catch (CategorieArcherException e)
      {
         throw new AjouterScoreException(e);
      }
   }

}
