package fr.egiov.concoursfleches.services;

import java.util.List;

import fr.egiov.concoursfleches.domaine.model.Cible;
import fr.egiov.concoursfleches.domaine.model.Concours;

/**
 * Interface du Service Concours
 * 
 * @author giovarej
 */
public interface CibleService extends IService<Cible>
{
   /**
    * Créer une cible
    * 
    * @param p_Cible
    *           la cible
    */
   void createCible(Cible p_Cible);

   /**
    * Met à jour une cible
    * 
    * @param p_Cible
    *           la cible
    */
   void updateCible(Cible p_Cible);

   /**
    * Met à jour une liste de cible
    * 
    * @param p_Cibles
    *           liste de cibles à mettre à jour
    */
   void updateCibles(List<Cible> p_Cibles);

   /**
    * Retourne la liste des cibles d'un concours
    * 
    * @param p_Concours
    *           le concours
    * @return List<Cible>
    */
   List<Cible> getCiblesConcours(Concours p_Concours);

   /**
    * Récupère une cible
    * 
    * @param p_CibleId
    *           l'id de la cible
    * @return {@link Cible}
    */
   Cible findCible(Long p_CibleId);

   /**
    * Supprimer une cible
    * 
    * @param p_CibleId
    *           l'id de la cible à supprimer
    */
   void supprimerCible(Long p_CibleId);
}
