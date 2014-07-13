package fr.egiov.concoursfleches.enumerations;

/**
 * Définit les catégories d'age
 * 
 * @author giovarej
 */
public enum CategorieAge implements EnumerationAvecLabel
{
   /** Moins de 11 ans */
   C_MOINS_11_ANS("-11"),

   /** Entre 11 et 12 ans */
   C_11_12_ANS("11/12"),

   /** Entre 13 et 14 ans */
   C_13_14_ANS("13/14"),

   /** Entre 15 et 16 ans */
   C_15_16_ANS("15/16"),

   /** Entre 17 et 18 ans */
   C_17_18_ANS("17/18"),

   /** Entre 19 et 49 ans */
   C_19_49_ANS("19/49"),

   /** Plus de 50 ans */
   C_50_ANS_ET_PLUS("50+");

   /** le label de la catégorie d'age */
   private String m_Label;

   /**
    * Constructeur
    * 
    * @param p_Label
    *           le label
    */
   private CategorieAge(String p_Label)
   {
      m_Label = p_Label;
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Enum#toString()
    */
   @Override
   public String toString()
   {
      return m_Label;
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
