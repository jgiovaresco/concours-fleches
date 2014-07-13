package fr.egiov.concoursfleches.domaine.dao.jpa;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.dao.IDao;
import fr.egiov.concoursfleches.domaine.dao.util.CritereTri;

/**
 * Classe abstraire définissant une implémentation JPA de {@link IDao}
 * 
 * @author giovarej
 * 
 * @param <T>
 *           le type de l'entité
 */
public class AbstractJpaDao<T> implements IDao<T>
{
   // ------------------------- Membres private -------------------------

   /** L'Entity Manager */
   private EntityManager m_EntityManager;

   /** la classe de l'entité */
   private Class<T> m_ClasseEntite;

   /** le logger */
   private final Logger m_Logger;

   // ------------------------- Constructeur -------------------------

   /**
    * Constructeur
    * 
    * @param p_ClasseEntite
    *           la classe de l'entité
    */
   public AbstractJpaDao(Class<T> p_ClasseEntite)
   {
      m_ClasseEntite = p_ClasseEntite;
      m_Logger = LoggerFactory.getLogger(AbstractJpaDao.class);
   }

   // ------------------------- Méthodes public -------------------------

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#count()
    */
   @Override
   public Integer count()
   {
      m_Logger.debug("{} : count()", m_ClasseEntite.getSimpleName());
      Query query = m_EntityManager.createNamedQuery(m_ClasseEntite
            .getSimpleName()
            + ".count");
      return ((Long) query.getSingleResult()).intValue();
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#find(java.lang.Long)
    */
   @Override
   public T find(Long p_EntiteId)
   {
      m_Logger.debug("{} : find({})", m_ClasseEntite.getSimpleName(),
            p_EntiteId);
      return this.m_EntityManager.find(m_ClasseEntite, p_EntiteId);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#find(java.lang.Integer,
    *      java.lang.Integer)
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<T> find(Integer p_IndexIntervalleDebut,
         Integer p_IndexIntervalleFin)
   {
      m_Logger.debug("{} find({}, {})", m_ClasseEntite.getSimpleName(),
            p_IndexIntervalleDebut);

      Query query = m_EntityManager.createNamedQuery(m_ClasseEntite
            .getSimpleName()
            + ".findAll");
      query.setFirstResult(p_IndexIntervalleDebut);
      query.setMaxResults(p_IndexIntervalleFin - p_IndexIntervalleDebut + 1);
      return query.getResultList();
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#findAll()
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<T> findAll()
   {
      m_Logger.debug("{} findAll()", m_ClasseEntite.getSimpleName());
      Query query = m_EntityManager.createNamedQuery(m_ClasseEntite
            .getSimpleName()
            + ".findAll");
      return query.getResultList();
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#findAndOrderByCriteria(java.lang.Integer,
    *      java.lang.Integer, java.util.List)
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<T> findAndOrderByCriteria(Integer p_IndexIntervalleDebut,
         Integer p_IndexIntervalleFin, List<CritereTri> p_Criteres)
   {
      // recuperation de la session Hibernate
      Session session = (Session) m_EntityManager.getDelegate();

      // Creation du critere
      Criteria critere = session.createCriteria(m_ClasseEntite);
      critere.setFirstResult(p_IndexIntervalleDebut);
      critere.setMaxResults(p_IndexIntervalleFin - p_IndexIntervalleDebut + 1);

      for (CritereTri critereTri : p_Criteres)
      {
         Order order = null;
         if (true == CritereTri.TRI_CROISSANT.equals(critereTri.getOrdreTri()))
         {
            order = Order.asc(critereTri.getNomColonne());
         }
         else
         {
            order = Order.desc(critereTri.getNomColonne());
         }
         critere.addOrder(order);
      }
      return critere.list();
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#save(java.lang.Object)
    */
   @Override
   public void save(T p_Entite)
   {
      m_Logger.debug("{} save({})", m_ClasseEntite.getSimpleName(), p_Entite);
      m_EntityManager.persist(p_Entite);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#update(java.lang.Object)
    */
   @Override
   public void update(T p_Entite)
   {
      m_Logger.debug("{} update({})", m_ClasseEntite.getSimpleName(), p_Entite);
      m_EntityManager.merge(p_Entite);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#delete(java.lang.Object)
    */
   @Override
   public void delete(T p_Entite)
   {
      m_Logger.debug("{} delete({})", m_ClasseEntite.getSimpleName(), p_Entite);
      m_EntityManager.remove(p_Entite);
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#getClasseEntite()
    */
   @Override
   public Class<T> getClasseEntite()
   {
      return m_ClasseEntite;
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.dao.IDao#findByQueryName(java.lang.String,
    *      java.util.Map))
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<T> findByQueryName(String p_NomRequete,
         Map<String, Object> p_Parametres)
   {
      Query query = m_EntityManager.createNamedQuery(p_NomRequete);

      for (Map.Entry<String, Object> parametre : p_Parametres.entrySet())
      {
         query.setParameter(parametre.getKey(), parametre.getValue());
      }
      return query.getResultList();
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * Affecte l'Entity Manager
    * 
    * @param p_EntityManager
    *           l'Entity Manager
    */
   @PersistenceContext
   public void setEntityManager(EntityManager p_EntityManager)
   {
      m_Logger.debug("{} setEntityManager()", m_ClasseEntite);
      this.m_EntityManager = p_EntityManager;
   }
}
