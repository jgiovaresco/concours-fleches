package fr.egiov.concoursfleches.enumerations;

/**
 * Définit les catégories d'archer
 * 
 * @author giovarej
 */
public enum CategorieArcher implements EnumerationAvecLabel
{
   /** Femme avec arc classique sans viseur */
   CLSV_F("CLSV F"),

   /** Femme avec arc classique avec viseur */
   CLAV_F("CLAV F"),

   /** Femme avec arc à poulie sans viseur */
   AMPSV_F("AMPSV F"),

   /** Femme avec arc à poulie avec viseur */
   AMPAV_F("AMPAV F"),

   /** Femme handicapée avec arc classique sans viseur */
   HANDI_SPORT_CLSV_F("Handi-sport CLSV F"),

   /** Femme handicapée avec arc classique avec viseur */
   HANDI_SPORT_CLAV_F("Handi-sport CLAV F"),

   /** Femme handicapée avec arc à poulie sans viseur */
   HANDI_SPORT_AMPSV_F("Handi-sport AMPSV F"),

   /** Femme handicapée avec arc à poulie avec viseur */
   HANDI_SPORT_AMPAV_F("Handi-sport AMPAV F"),

   /** Homme avec arc classique sans viseur */
   CLSV_H("CLSV H"),

   /** Homme avec arc classique avec viseur */
   CLAV_H("CLAV H"),

   /** Homme avec arc à poulie sans viseur */
   AMPSV_H("AMPSV H"),

   /** Homme avec arc à poulie avec viseur */
   AMPAV_H("AMPAV H"),

   /** Homme handicapé avec arc classique sans viseur */
   HANDI_SPORT_CLSV_H("Handi-sport CLSV H"),

   /** Homme handicapé avec arc classique avec viseur */
   HANDI_SPORT_CLAV_H("Handi-sport CLAV H"),

   /** Homme handicapé avec arc à poulie sans viseur */
   HANDI_SPORT_AMPSV_H("Handi-sport AMPSV H"),

   /** Homme handicapé avec arc à poulie avec viseur */
   HANDI_SPORT_AMPAV_H("Handi-sport AMPAV H");

   /** le label de la catégorie d'archer */
   private String m_Label;

   /**
    * Constructeur
    * 
    * @param p_Label
    *           le label
    */
   private CategorieArcher(String p_Label)
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
