package fr.egiov.concoursfleches.exceptions.archer;

import fr.egiov.concoursfleches.services.ArcherService;

/**
 * Exception qui peut etre levée pendant l'exécution d'une méthode de
 * {@link ArcherService}
 * 
 * @author giovarej
 */
public class ArcherException extends Exception
{
   /** serial version UID */
   private static final long serialVersionUID = 1L;

   /**
    * Constructeur
    * 
    * @param p_Message
    *           le message de l'exception
    */
   public ArcherException(String p_Message)
   {
      super(p_Message);
   }

   /**
    * Constructeur
    * 
    * @param p_Message
    *           le message de l'exception
    * @param p_Cause
    *           la cause de l'exception
    */
   public ArcherException(String p_Message, Throwable p_Cause)
   {
      super(p_Message, p_Cause);
   }

   /**
    * Constructeur
    * 
    * @param p_Cause
    *           la cause de l'exception
    */
   public ArcherException(Throwable p_Cause)
   {
      super(p_Cause);
   }
}
