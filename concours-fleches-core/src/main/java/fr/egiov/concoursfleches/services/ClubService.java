package fr.egiov.concoursfleches.services;

import java.util.List;

import fr.egiov.concoursfleches.domaine.model.Club;

/**
 * Interface du Service Club
 * 
 * @author giovarej
 */
public interface ClubService extends IService<Club>
{
   /**
    * Créée un club
    * 
    * @param p_Club
    *           l'club
    */
   void createClub(Club p_Club);

   /**
    * Met à jour les informations d'un club
    * 
    * @param p_Club
    *           l'club
    */
   void updateClub(Club p_Club);

   /**
    * Compte le nombre de clubs de la base
    * 
    * @return le nombre de clubs de la base
    */
   Integer countClubs();

   /**
    * Récupère une club
    * 
    * @param p_ClubId
    *           l'id de l'club
    * @return {@link Club}
    */
   Club findClub(Long p_ClubId);

   /**
    * Récupère tous les clubs de la base
    * 
    * @return List<Club>
    */
   List<Club> findAllClubs();
}
