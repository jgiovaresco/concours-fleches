package fr.egiov.concoursfleches.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.egiov.concoursfleches.domaine.dao.IDao;
import fr.egiov.concoursfleches.domaine.model.Club;
import fr.egiov.concoursfleches.services.ClubService;

/**
 * Implémentation du Service Club
 * 
 * @author giovarej
 */
@Service("clubService")
public class ClubServiceImpl implements ClubService
{
   // ------------------------- Membres private -------------------------

   /** La Dao Club */
   @Autowired
   @Qualifier("clubDao")
   private IDao<Club> m_ClubDao;

   // ------------------------- Méthodes public -------------------------

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ClubService#createClub(fr.egiov.concoursfleches.domaine.model.Club)
    */
   @Override
   public void createClub(Club p_Club)
   {
      m_ClubDao.save(p_Club);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ClubService#updateClub(fr.egiov.concoursfleches.domaine.model.Club)
    */
   @Override
   public void updateClub(Club p_Club)
   {
      m_ClubDao.update(p_Club);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ClubService#countClubs()
    */
   @Override
   public Integer countClubs()
   {
      return m_ClubDao.count();
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ClubService#findAllClubs()
    */
   @Override
   public List<Club> findAllClubs()
   {
      return m_ClubDao.findAll();
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ClubService#findClub(java.lang.Long)
    */
   @Override
   public Club findClub(Long p_ClubId)
   {
      return m_ClubDao.find(p_ClubId);
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * Affecte la Dao Club
    * 
    * @param p_ClubDao
    *           la Dao Club
    */
   public void setClubDao(IDao<Club> p_ClubDao)
   {
      m_ClubDao = p_ClubDao;
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.IService#getDao()
    */
   @Override
   public IDao<Club> getDao()
   {
      return m_ClubDao;
   }
}
