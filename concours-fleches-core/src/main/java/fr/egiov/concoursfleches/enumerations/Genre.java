package fr.egiov.concoursfleches.enumerations;

/**
 * Définit le genre d'un archer
 * 
 * @author giovarej
 */
public enum Genre implements EnumerationAvecLabel
{
   /** Femme */
   FEMME("F"),

   /** Homme */
   HOMME("H");

   /** le label du genre */
   private String m_Label;

   /**
    * Constructeur
    * 
    * @param p_Label
    *           le label
    */
   private Genre(String p_Label)
   {
      m_Label = p_Label;
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.enumerations.EnumerationAvecLabel#getLabel()
    */
   @Override
   public String getLabel()
   {
      return m_Label;
   }
}
