package fr.egiov.concoursfleches.exceptions.concours;

/**
 * Classe d'exception levée lors d'une erreur lors de
 * 
 * <ul>
 * <li>l'ajout d'un score pendant le calcul des résultats</li>
 * <li>de la suppression d'un concours</li>
 * </ul>
 * 
 * @author giovarej
 */
public class ConcoursException extends Exception
{
   /** serial version UID */
   private static final long serialVersionUID = 1L;

   /**
    * Constructeur
    * 
    * @param p_Message
    *           le message de l'exception
    */
   public ConcoursException(String p_Message)
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
   public ConcoursException(String p_Message, Throwable p_Cause)
   {
      super(p_Message, p_Cause);
   }

   /**
    * Constructeur
    * 
    * @param p_Cause
    *           la cause de l'exception
    */
   public ConcoursException(Throwable p_Cause)
   {
      super(p_Cause);
   }
}
