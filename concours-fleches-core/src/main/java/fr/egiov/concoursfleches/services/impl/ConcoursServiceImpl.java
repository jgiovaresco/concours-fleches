package fr.egiov.concoursfleches.services.impl;

import java.util.Date;
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
import fr.egiov.concoursfleches.domaine.model.Resultat;
import fr.egiov.concoursfleches.domaine.model.Score;
import fr.egiov.concoursfleches.exceptions.concours.AjouterScoreException;
import fr.egiov.concoursfleches.exceptions.concours.ConcoursException;
import fr.egiov.concoursfleches.exceptions.concours.ConcoursNotFoundException;
import fr.egiov.concoursfleches.helpers.ResultatsHelper;
import fr.egiov.concoursfleches.services.ConcoursService;

/**
 * Implémentation du Service Concours
 * 
 * @author giovarej
 */
@Service("concoursService")
public class ConcoursServiceImpl implements ConcoursService
{
   // ------------------------- Constantes private -------------------------

   /** Le logger */
   private static final Logger LOGGER = LoggerFactory
         .getLogger(ConcoursServiceImpl.class);

   // ------------------------- Membres private -------------------------

   /** La Dao Concours */
   @Autowired
   @Qualifier("concoursDao")
   private IDao<Concours> m_ConcoursDao;

   /** Helper de {@link Resultat} */
   @Autowired
   @Qualifier("resultatsHelper")
   private ResultatsHelper m_ResultatsHelper;

   // ------------------------- Méthodes public-------------------------

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ConcoursService#ajouterCible(fr.egiov.concoursfleches.domaine.model.Cible)
    */
   @Override
   public void ajouterCible(Cible p_Cible) throws ConcoursNotFoundException

   {
      LOGGER.debug("ajouterCible() - cible : {}", p_Cible);
      Concours concours = findConcours(p_Cible.getConcours().getId());
      concours.getCibles().add(p_Cible);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ConcoursService#creerConcours(fr.egiov.concoursfleches.domaine.model.Concours)
    */
   @Override
   public void creerConcours(Concours p_Concours)
   {
      LOGGER.debug("creerConcours() - concours : {}", p_Concours);
      m_ConcoursDao.save(p_Concours);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ConcoursService#updateConcours(fr.egiov.concoursfleches.domaine.model.Concours)
    */
   @Override
   public void updateConcours(Concours p_Concours)
   {
      LOGGER.debug("updateConcours() - concours : {}", p_Concours);
      m_ConcoursDao.update(p_Concours);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ConcoursService#creerConcours(fr.egiov.concoursfleches.domaine.model.Concours,
    *      fr.egiov.concoursfleches.domaine.model.Concours)
    */
   @Override
   public void creerConcours(Concours p_Concours, Concours p_AncienConcours)

   {
      LOGGER.debug("creerConcours() - concours : {}, {}", p_Concours,
            p_AncienConcours);
      try
      {
         Concours ancienConcours = findConcours(p_AncienConcours.getId());

         Cible cible = null;
         for (Cible c : ancienConcours.getCibles())
         {
            cible = new Cible();
            cible.setNom(c.getNom());
            cible.setConcours(p_Concours);
            cible.setParticipant(c.getParticipant());

            p_Concours.getCibles().add(cible);
         }
         m_ConcoursDao.save(p_Concours);
      }
      catch (ConcoursNotFoundException e)
      {
         // n'arrivera pas
         LOGGER.error("Le concours d'id {} n'a pas été trouvé",
               p_AncienConcours.getId());
      }
   }


   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ConcoursService#supprimerConcours(java.lang.Long)
    */
   @Override
   public void supprimerConcours(Long p_ConcoursId) throws ConcoursException
   {
      try
      {
         LOGGER.debug("supprimerArcher() - p_ConcoursId : ", p_ConcoursId);
         Concours concours = findConcours(p_ConcoursId);
         m_ConcoursDao.delete(concours);
      }
      catch (Exception e)
      {
         LOGGER.error("supprimerConcours() - EXCEPTION ", e);
         throw new ConcoursException(MESSAGE_ERREUR_SUPPRESSION);
      }
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ConcoursService#findConcours(java.lang.Long)
    */
   @Override
   public Concours findConcours(Long p_ConcoursId)
         throws ConcoursNotFoundException

   {
      LOGGER.debug("findConcours() - concoursId : {}", p_ConcoursId);
      Concours concours = m_ConcoursDao.find(p_ConcoursId);

      if (null == concours)
      {
         throw new ConcoursNotFoundException("Aucun concours d'id : "
               + p_ConcoursId);
      }
      return concours;
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ConcoursService#findConcoursByDate(java.util.Date)
    */
   @Override
   public Concours findConcoursByDate(Date p_Date)
         throws ConcoursNotFoundException

   {
      LOGGER.debug("findConcoursByDate() - Date : {}", p_Date);
      Concours concours = null;

      Map<String, Object> parametres = new HashMap<String, Object>();
      parametres.put("dateConcours", p_Date);

      List<Concours> resultats = m_ConcoursDao.findByQueryName(
            IDao.REQUETE_FIND_CONCOURS_BY_DATE, parametres);
      if (1 == resultats.size())
      {
         concours = resultats.get(0);
      }
      else
      {
         throw new ConcoursNotFoundException("Aucun concours à la date : "
               + p_Date);
      }

      return concours;
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ConcoursService#findAllConcours()
    */
   @Override
   public List<Concours> findAllConcours()

   {
      LOGGER.debug("findAllConcours()");
      return m_ConcoursDao.findAll();
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.ConcoursService#calculerResultats(java.lang.Long)
    */
   @Override
   public Resultat calculerResultats(Long p_ConcoursId)
         throws ConcoursException, ConcoursNotFoundException
   {
      LOGGER.debug("calculerResultats() - ConcoursId : {}", p_ConcoursId);
      try
      {
         Concours concours = findConcours(p_ConcoursId);

         Resultat resultat = new Resultat();
         resultat.setConcours(concours);

         Score score = null;
         for (Cible cible : concours.getCibles())
         {
            score = new Score();
            score.setCible(cible);

            m_ResultatsHelper.ajouterScore(resultat, score);
         }

         return resultat;
      }
      catch (AjouterScoreException e)
      {
         LOGGER.error("Erreur lors du calcul du resultat", e);
         throw new ConcoursException(e);
      }
   }

   // ------------------------- Accesseurs public-------------------------

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.services.IService#getDao()
    */
   @Override
   public IDao<Concours> getDao()
   {
      return m_ConcoursDao;
   }

   /**
    * Affecte la Dao Concours
    * 
    * @param p_ConcoursDao
    *           la Dao Concours
    */
   public void setConcoursDao(IDao<Concours> p_ConcoursDao)
   {
      m_ConcoursDao = p_ConcoursDao;
   }
}
