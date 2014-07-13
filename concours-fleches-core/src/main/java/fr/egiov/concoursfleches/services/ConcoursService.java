package fr.egiov.concoursfleches.services;

import java.util.Date;
import java.util.List;

import fr.egiov.concoursfleches.domaine.model.Cible;
import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.domaine.model.Resultat;
import fr.egiov.concoursfleches.exceptions.concours.ConcoursException;
import fr.egiov.concoursfleches.exceptions.concours.ConcoursNotFoundException;

/**
 * Interface du Service Concours
 * 
 * @author giovarej
 */
public interface ConcoursService extends IService<Concours>
{
   // ------------------------- Constantes -------------------------

   /** Message d'erreur lors d'une suppression */
   String MESSAGE_ERREUR_SUPPRESSION = "supprimer-erreur";

   // ------------------------- Méthodes -------------------------

   /**
    * Créer un concours
    * 
    * @param p_Concours
    *           le concours
    */
   void creerConcours(Concours p_Concours);

   /**
    * Met à jour un concours
    * 
    * @param p_Concours
    *           le concours
    */
   void updateConcours(Concours p_Concours);

   /**
    * Créer un concours à partir d'un autre concours
    * 
    * @param p_Concours
    *           le nouveau concours
    * @param p_AncienConcours
    *           un ancien concours à partir duquel on créer un autre concours
    */
   void creerConcours(Concours p_Concours, Concours p_AncienConcours);

   /**
    * Supprimer un concours
    * 
    * @param p_ConcoursId
    *           l'id du concours à supprimer
    */
   void supprimerConcours(Long p_ConcoursId) throws ConcoursException;

   /**
    * Ajoute une cible au concours
    * 
    * @param p_Cible
    *           la cible
    * @throws ConcoursNotFoundException
    *            si aucun concours n'a été trouvé
    */
   void ajouterCible(Cible p_Cible) throws ConcoursNotFoundException;

   /**
    * Récupère tout les concours de la base.
    * 
    * @return List<Concours>
    */
   List<Concours> findAllConcours();

   /**
    * Récupère un concours
    * 
    * @param p_ConcoursId
    *           l'id du concours
    * @return {@link Concours}
    * @throws ConcoursNotFoundException
    *            si aucun concours n'a été trouvé
    */
   Concours findConcours(Long p_ConcoursId) throws ConcoursNotFoundException;

   /**
    * Récupère un concours grace à la date
    * 
    * @param p_Date
    *           l'id du concours
    * @return {@link Concours}
    * @throws ConcoursNotFoundException
    *            si aucun concours n'a été trouvé
    */
   Concours findConcoursByDate(Date p_Date) throws ConcoursNotFoundException;

   /**
    * Calcule les résultats du concours
    * 
    * @param p_ConcoursId
    *           l'id du concours
    * @return les résultats
    * @throws ConcoursException
    *            en cas d'erreur lors du calcul des résultats
    * @throws ConcoursNotFoundException
    *            si aucun concours n'a été trouvé
    */
   Resultat calculerResultats(Long p_ConcoursId) throws ConcoursException,
         ConcoursNotFoundException;
}
