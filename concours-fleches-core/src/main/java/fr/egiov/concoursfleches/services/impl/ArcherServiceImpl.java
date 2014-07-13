package fr.egiov.concoursfleches.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.egiov.concoursfleches.domaine.dao.IDao;
import fr.egiov.concoursfleches.domaine.model.Archer;
import fr.egiov.concoursfleches.exceptions.archer.ArcherException;
import fr.egiov.concoursfleches.services.ArcherService;

/**
 * Implémentation du Service Archer
 * 
 * @author giovarej
 */
@Service("archerService")
public class ArcherServiceImpl implements ArcherService
{
   // ------------------------- Constantes private -------------------------

   /** Le logger */
   private static final Logger LOGGER = LoggerFactory
         .getLogger(ArcherServiceImpl.class);

   // ------------------------- Membres private -------------------------

   /** La Dao Archer */
   @Autowired
   @Qualifier("archerDao")
   private IDao<Archer> m_ArcherDao;

   // ------------------------- Méthodes public -------------------------

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ArcherService#createArcher(fr.egiov.concoursfleches.domaine.model.Archer)
    */
   @Override
   public void createArcher(Archer p_Archer)
   {
      LOGGER.debug("createArcher() - p_Archer : ", p_Archer);
      m_ArcherDao.save(p_Archer);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ArcherService#updateArcher(fr.egiov.concoursfleches.domaine.model.Archer)
    */
   @Override
   public void updateArcher(Archer p_Archer)
   {
      LOGGER.debug("updateArcher() - p_Archer : ", p_Archer);
      m_ArcherDao.update(p_Archer);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ArcherService#supprimerArcher(java.lang.Long)
    */
   @Override
   public void supprimerArcher(Long p_ArcherId) throws ArcherException
   {
      try
      {
         LOGGER.debug("supprimerArcher() - p_ArcherId : ", p_ArcherId);
         Archer archer = findArcher(p_ArcherId);
         m_ArcherDao.delete(archer);
      }
      catch (Exception e)
      {
         LOGGER.error("supprimerArcher() - EXCEPTION ", e);
         throw new ArcherException(MESSAGE_ERREUR_SUPPRESSION);
      }
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ArcherService#findAllArchers()
    */
   @Override
   public List<Archer> findAllArchers()
   {
      LOGGER.debug("findAllArchers()");
      return m_ArcherDao.findAll();
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ArcherService#findArcher(java.lang.Long)
    */
   @Override
   public Archer findArcher(Long p_ArcherId)
   {
      LOGGER.debug("findArcher() - p_ArcherId : ", p_ArcherId);
      return m_ArcherDao.find(p_ArcherId);
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * Affecte la Dao Archer
    * 
    * @param p_ArcherDao
    *           la Dao Archer
    */
   public void setArcherDao(IDao<Archer> p_ArcherDao)
   {
      m_ArcherDao = p_ArcherDao;
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.IService#getDao()
    */
   @Override
   public IDao<Archer> getDao()
   {
      return m_ArcherDao;
   }
}
