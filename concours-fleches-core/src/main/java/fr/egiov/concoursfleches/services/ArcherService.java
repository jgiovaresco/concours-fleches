package fr.egiov.concoursfleches.services;

import java.util.List;

import fr.egiov.concoursfleches.domaine.model.Archer;
import fr.egiov.concoursfleches.exceptions.archer.ArcherException;

/**
 * Interface du Service Archer
 * 
 * @author giovarej
 */
public interface ArcherService extends IService<Archer>
{
   // ------------------------- Constantes -------------------------

   /** Message d'erreur lors d'une suppression */
   String MESSAGE_ERREUR_SUPPRESSION = "supprimer-erreur";

   // ------------------------- Méthodes -------------------------

   /**
    * Créée un archer
    * 
    * @param p_Archer
    *           l'archer
    */
   void createArcher(Archer p_Archer);

   /**
    * Met à jour un archer
    * 
    * @param p_Archer
    *           l'archer
    */
   void updateArcher(Archer p_Archer);

   /**
    * Supprimer un archer
    * 
    * @param p_ArcherId
    *           l'id de l'archer à supprimer
    */
   void supprimerArcher(Long p_ArcherId) throws ArcherException;
   
   /**
    * Récupère une archer
    * 
    * @param p_ArcherId
    *           l'id de l'archer
    * @return {@link Archer}
    */
   Archer findArcher(Long p_ArcherId);

   /**
    * Récupère tous les archers de la base
    * 
    * @return List<Archer>
    */
   List<Archer> findAllArchers();
}
