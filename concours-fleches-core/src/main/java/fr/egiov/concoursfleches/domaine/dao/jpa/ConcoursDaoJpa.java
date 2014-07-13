package fr.egiov.concoursfleches.domaine.dao.jpa;

import org.springframework.stereotype.Repository;

import fr.egiov.concoursfleches.domaine.model.Concours;

/**
 * Implémentation JPA de la Dao Concours
 * 
 * @author giovarej
 */
@Repository("concoursDao")
public class ConcoursDaoJpa extends AbstractJpaDao<Concours>
{
   /**
    * Constructeur
    */
   public ConcoursDaoJpa()
   {
      super(Concours.class);
   }
}
