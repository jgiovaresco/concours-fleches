package fr.egiov.concoursfleches.exceptions.dao;

/**
 * Classe d'exception levée lors d'une erreur lorsqu'une recherche d'un entité
 * abouti à aucun résultat
 * 
 * @author giovarej
 */
public class EntityNotFoundException extends Exception
{
   /** serial version UID */
   private static final long serialVersionUID = 1L;

   /**
    * Constructeur
    * 
    * @param p_Message
    *           le message de l'exception
    */
   public EntityNotFoundException(String p_Message)
   {
      super(p_Message);
   }

   /**
    * Constructeur
    * 
    * @param p_Cause
    *           la cause de l'exception
    */
   public EntityNotFoundException(Throwable p_Cause)
   {
      super(p_Cause);
   }
}
