package fr.egiov.concoursfleches.exceptions.concours;

/**
 * Classe d'exception levée lors d'une erreur lors de l'ajout d'un score pendant
 * le calcul des résultats
 * 
 * @author giovarej
 */
public class AjouterScoreException extends Exception
{
   /** serial version UID */
   private static final long serialVersionUID = 1L;

   /**
    * Constructeur
    * 
    * @param p_Message
    *           le message de l'exception
    */
   public AjouterScoreException(String p_Message)
   {
      super(p_Message);
   }

   /**
    * Constructeur
    * 
    * @param p_Cause
    *           la cause de l'exception
    */
   public AjouterScoreException(Throwable p_Cause)
   {
      super(p_Cause);
   }
}
