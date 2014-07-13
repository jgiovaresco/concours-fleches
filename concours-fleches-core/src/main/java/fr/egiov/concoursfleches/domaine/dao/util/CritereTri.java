package fr.egiov.concoursfleches.domaine.dao.util;

/**
 * Classe définissant un critère de tri
 * 
 * @author giovarej
 */
public class CritereTri
{
   // ------------------------- Constantes public -------------------------

   /** Tri croissant */
   public static final Boolean TRI_CROISSANT = true;

   /** Tri décroissant */
   public static final Boolean TRI_DECROISSANT = false;

   // ------------------------- Membres private -------------------------

   /** Nom de la colonne */
   private String m_NomColonne;

   /** Ordre de tri */
   private Boolean m_OrdreTri;

   // ------------------------- Constructeur -------------------------

   /**
    * Constructeur vide
    */
   public CritereTri()
   {
      super();
   }

   /**
    * Constructeur
    * 
    * @param p_NomColonne
    *           le nom de la colonne
    * @param p_OrdreTri
    *           l'ordre de tri
    */
   public CritereTri(String p_NomColonne, Boolean p_OrdreTri)
   {
      super();
      m_NomColonne = p_NomColonne;
      m_OrdreTri = p_OrdreTri;
   }

   // ------------------------- Accesseurs public -------------------------
   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      StringBuffer chaine = new StringBuffer();
      chaine.append("[ colonne = ").append(m_NomColonne).append(" , ordre = ")
            .append(m_OrdreTri).append(" ]");
      return chaine.toString();
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * @return le nom de la colonne
    */
   public String getNomColonne()
   {
      return m_NomColonne;
   }

   /**
    * @param p_NomColonne
    *           le nom de la colonne
    */
   public void setNomColonne(String p_NomColonne)
   {
      m_NomColonne = p_NomColonne;
   }

   /**
    * @return l'ordre de tri
    */
   public Boolean getOrdreTri()
   {
      return m_OrdreTri;
   }

   /**
    * @param p_OrdreTri
    *           l'ordre de tri
    */
   public void setOrdreTri(Boolean p_OrdreTri)
   {
      m_OrdreTri = p_OrdreTri;
   }
}
