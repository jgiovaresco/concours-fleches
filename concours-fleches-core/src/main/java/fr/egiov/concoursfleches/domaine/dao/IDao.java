package fr.egiov.concoursfleches.domaine.dao;

import java.util.List;
import java.util.Map;

import fr.egiov.concoursfleches.domaine.dao.util.CritereTri;

/**
 * Interface définissant une Dao
 * 
 * @author giovarej
 */
public interface IDao<T>
{
   // ------------------------- Constantes public -------------------------

   /** la requete permettant de récupérer des cibles à partir d'un concours */
   static final String REQUETE_FIND_CIBLES_BY_CONCOURS = "Cible.findByConcours";

   /** la requete permettant de récupérer un concours à partir d'une date */
   static final String REQUETE_FIND_CONCOURS_BY_DATE = "Concours.findConcoursByDate";

   // ------------------------- Méthodes public -------------------------

   /**
    * Créer l'entité en base
    * 
    * @param p_Entite
    *           l'entité
    */
   void save(T p_Entite);

   /**
    * Met à jour l'entité
    * 
    * @param p_Entite
    *           l'entité
    */
   void update(T p_Entite);

   /**
    * Supprime une entité
    * 
    * @param p_Entite
    *           l'entité
    */
   void delete(T p_Entite);

   /**
    * Compte le nombre d'entité de la base
    * 
    * @return le nombre d'entité de la base
    */
   Integer count();

   /**
    * Récupère une entité selon son identifiant
    * 
    * @param p_EntiteId
    *           l'id de l'entité
    * @return l'entité
    */
   T find(Long p_EntiteId);

   /**
    * Récupère toutes les entités de la base
    * 
    * @return List<T>
    */
   List<T> findAll();

   /**
    * Récupère un intervalle d'entité de la base trié selon les critères passés
    * en paramètre
    * 
    * @param p_Criteres
    *           les critères de tri
    * @return List<T>
    */
   List<T> findAndOrderByCriteria(Integer p_IndexIntervalleDebut,
         Integer p_IndexIntervalleFin, List<CritereTri> p_Criteres);

   /**
    * Récupère un intervalle d'entité de la base
    * 
    * @param p_IndexIntervalleDebut
    *           l'index du debut de l'interval
    * @param p_IndexIntervalleFin
    *           l'index de fin de l'interval
    * @return List<T>
    */
   List<T> find(Integer p_IndexIntervalleDebut, Integer p_IndexIntervalleFin);

   /**
    * Récupère les entités grace à une requete de l'entité
    * 
    * @param p_NomRequete
    *           le nom de la requete de l'entité
    * @param p_Parametres
    *           les paramètres de la requete
    * @return List<T>
    */
   List<T> findByQueryName(String p_NomRequete, Map<String, Object> p_Parametres);

   /**
    * Retourne la classe de l'entité géré par la Dao
    * 
    * @return Class<T>
    */
   Class<T> getClasseEntite();
}
