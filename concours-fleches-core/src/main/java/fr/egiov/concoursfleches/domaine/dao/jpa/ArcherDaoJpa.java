package fr.egiov.concoursfleches.domaine.dao.jpa;

import org.springframework.stereotype.Repository;

import fr.egiov.concoursfleches.domaine.model.Archer;

/**
 * Implémentation JPA de la Dao Archer
 * 
 * @author giovarej
 */
@Repository("archerDao")
public class ArcherDaoJpa extends AbstractJpaDao<Archer>
{
   /**
    * Constructeur
    */
   public ArcherDaoJpa()
   {
      super(Archer.class);
   }
}
