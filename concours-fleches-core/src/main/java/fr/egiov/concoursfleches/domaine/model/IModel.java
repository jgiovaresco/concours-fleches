package fr.egiov.concoursfleches.domaine.model;

import java.io.Serializable;

/**
 * Interface des objets appartement au modèle de donnée.
 * 
 * @author giovarej
 */
public interface IModel extends Serializable
{
   /**
    * @return l'id de l'entité
    */
   Long getId();

   /**
    * @param p_Id
    *           l'id de l'entité
    */
   void setId(Long p_Id);
}
