package fr.egiov.concoursfleches.exceptions.helpers;

/**
 * Classe d'exception levée lors d'une erreur dans la détermination d'une
 * catégorie d'archer
 * 
 * @author giovarej
 */
public class CategorieArcherException extends Exception
{
   /** serial version UID */
   private static final long serialVersionUID = 1L;

   /**
    * Constructeur
    * 
    * @param p_Message
    *           le message de l'exception
    */
   public CategorieArcherException(String p_Message)
   {
      super(p_Message);
   }
}
