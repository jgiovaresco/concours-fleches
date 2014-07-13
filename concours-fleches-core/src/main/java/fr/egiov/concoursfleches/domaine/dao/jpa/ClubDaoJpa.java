package fr.egiov.concoursfleches.domaine.dao.jpa;

import org.springframework.stereotype.Repository;

import fr.egiov.concoursfleches.domaine.model.Club;

/**
 * Implémentation JPA de la Dao Club
 * 
 * @author giovarej
 */
@Repository("clubDao")
public class ClubDaoJpa extends AbstractJpaDao<Club>
{
   /**
    * Constructeur
    */
   public ClubDaoJpa()
   {
      super(Club.class);
   }
}
