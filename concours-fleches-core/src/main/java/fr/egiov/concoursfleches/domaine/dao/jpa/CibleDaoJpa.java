package fr.egiov.concoursfleches.domaine.dao.jpa;

import org.springframework.stereotype.Repository;

import fr.egiov.concoursfleches.domaine.model.Cible;

/**
 * Implémentation JPA de la Dao Cible
 * 
 * @author giovarej
 */
@Repository("cibleDao")
public class CibleDaoJpa extends AbstractJpaDao<Cible>
{
   /**
    * Constructeur
    */
   public CibleDaoJpa()
   {
      super(Cible.class);
   }
}
