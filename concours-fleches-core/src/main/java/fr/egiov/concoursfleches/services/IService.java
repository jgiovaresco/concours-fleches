package fr.egiov.concoursfleches.services;

import fr.egiov.concoursfleches.domaine.dao.IDao;

/**
 * Définit l'interface générique d'un service.
 * 
 * @author giovarej
 */
public interface IService<T>
{
   /**
    * Retourne la Dao utilisé par le service
    * @return {@link IDao}
    */
   IDao<T> getDao();
}
