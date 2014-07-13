package fr.egiov.concoursfleches.helpers;

import fr.egiov.concoursfleches.domaine.model.Resultat;
import fr.egiov.concoursfleches.domaine.model.Score;
import fr.egiov.concoursfleches.exceptions.concours.AjouterScoreException;

/**
 * Interface de l'helper fournissant des méthodes pour gérer des
 * {@link Resultat}
 * 
 * @author giovarej
 */
public interface ResultatsHelper
{
   /**
    * Ajoute un score à un résultat
    * 
    * @param p_Resultat
    *           le résultat
    * @param p_Score
    *           le score
    * @throws AjouterScoreException
    *            en cas d'erreur
    */
   void ajouterScore(Resultat p_Resultat, Score p_Score)
         throws AjouterScoreException;
}
