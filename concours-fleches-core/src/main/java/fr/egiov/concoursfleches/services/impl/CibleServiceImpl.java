package fr.egiov.concoursfleches.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.egiov.concoursfleches.domaine.dao.IDao;
import fr.egiov.concoursfleches.domaine.model.Cible;
import fr.egiov.concoursfleches.domaine.model.Concours;
import fr.egiov.concoursfleches.services.CibleService;

/**
 * Implémentation du Service Cible
 * 
 * @author giovarej
 */
@Service("cibleService")
public class CibleServiceImpl implements CibleService
{
   // ------------------------- Membres private -------------------------

   /** le logger */
   private static final Logger LOGGER = LoggerFactory
         .getLogger(CibleServiceImpl.class);

   /** La Dao Cible */
   @Autowired
   @Qualifier("cibleDao")
   private IDao<Cible> m_CibleDao;

   // ------------------------- Méthodes public -------------------------

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.CibleService#createCible(fr.egiov.concoursfleches.domaine.model.Cible)
    */
   @Override
   public void createCible(Cible p_Cible)
   {
      LOGGER.debug("createCible( ) - p_Cible : ", p_Cible);
      p_Cible.setDepartage(0);
      m_CibleDao.save(p_Cible);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.CibleService#getCiblesConcours(fr.egiov.concoursfleches.domaine.model.Concours)
    */
   @Override
   public List<Cible> getCiblesConcours(Concours p_Concours)
   {
      LOGGER.debug("getCiblesConcours( ) - p_Concours : ", p_Concours);
      Map<String, Object> parametres = new HashMap<String, Object>();
      parametres.put("concours", p_Concours);

      return m_CibleDao.findByQueryName(IDao.REQUETE_FIND_CIBLES_BY_CONCOURS,
            parametres);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.CibleService#updateCible(fr.egiov.concoursfleches.domaine.model.Cible)
    */
   @Override
   public void updateCible(Cible p_Cible)
   {
      LOGGER.debug("updateCible( ) - p_Cible : ", p_Cible);
      m_CibleDao.update(p_Cible);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.CibleService#updateCibles(java.util.List)
    */
   @Override
   public void updateCibles(List<Cible> p_Cibles)
   {
      LOGGER.debug("updateCibles( ) - p_Cibles : ", p_Cibles);
      for (Cible cible : p_Cibles)
      {
         m_CibleDao.update(cible);
      }
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.CibleService#findCible(java.lang.Long)
    */
   @Override
   public Cible findCible(Long p_CibleId)
   {
      LOGGER.debug("findCible( ) - p_CibleId : ", p_CibleId);
      return m_CibleDao.find(p_CibleId);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.CibleService#supprimerCible(java.lang.Long)
    */
   @Override
   public void supprimerCible(Long p_CibleId)
   {
      LOGGER.debug("supprimerCible( ) - p_CibleId : ", p_CibleId);
      Cible cible = findCible(p_CibleId);
      m_CibleDao.delete(cible);
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.IService#getDao()
    */
   @Override
   public IDao<Cible> getDao()
   {
      LOGGER.debug("getDao( )");
      return m_CibleDao;
   }

   /**
    * @param p_CibleDao
    *           la dao Cible
    */
   public void setCibleDao(IDao<Cible> p_CibleDao)
   {
      m_CibleDao = p_CibleDao;
   }
}
