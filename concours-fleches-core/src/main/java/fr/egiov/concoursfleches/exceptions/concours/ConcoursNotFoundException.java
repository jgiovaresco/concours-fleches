package fr.egiov.concoursfleches.exceptions.concours;

/**
 * Classe d'exception levée lors d'une erreur lorsqu'une recherche d'un concours
 * abouti à aucun résultat
 * 
 * @author giovarej
 */
public class ConcoursNotFoundException extends Exception
{
   /** serial version UID */
   private static final long serialVersionUID = 1L;

   /**
    * Constructeur
    * 
    * @param p_Message
    *           le message de l'exception
    */
   public ConcoursNotFoundException(String p_Message)
   {
      super(p_Message);
   }

   /**
    * Constructeur
    * 
    * @param p_Cause
    *           la cause de l'exception
    */
   public ConcoursNotFoundException(Throwable p_Cause)
   {
      super(p_Cause);
   }
}
