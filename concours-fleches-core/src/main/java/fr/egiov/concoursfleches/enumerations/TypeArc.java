package fr.egiov.concoursfleches.enumerations;

/**
 * Définit les types d'arc
 * 
 * @author giovarej
 */
public enum TypeArc implements EnumerationAvecLabel
{
   /** Arc classique sans viseur */
   ARC_CLASSIQUE_SANS_VISEUR("CLSV"),

   /** Arc classique avec viseur */
   ARC_CLASSIQUE_AVEC_VISEUR("CLAV"),

   /** Arc à poulie sans viseur */
   ARC_A_POULIE_SANS_VISEUR("AMPSV"),

   /** Arc à poulie avec viseur */
   ARC_A_POULIE_AVEC_VISEUR("AMPAV");

   /** le label du type d'arc */
   private String m_Label;

   /**
    * Constructeur
    * 
    * @param p_Code
    *           le code
    */
   private TypeArc(String p_Code)
   {
      m_Label = p_Code;
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
