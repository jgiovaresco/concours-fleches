package fr.egiov.concoursfleches.tapestry.model;

import java.io.Serializable;

import fr.egiov.concoursfleches.enumerations.CategorieAge;

/**
 * Wrapper Tapestry de l'objet {@link CategorieAge}.
 * @author giovarej
 */
public class CategorieAgeTapestry implements Serializable, Comparable<CategorieAgeTapestry>
{
   // ------------------------- Constantes private -------------------------

   /** serial version UID */
   private static final long serialVersionUID = 1L;

   // ------------------------- Membres private ------------------------

   /** Indique si la catégorie a été sélectionnée par l'utilisateur */
   private boolean m_Selectionnee;

   /** la catégorie wrappée */
   private CategorieAge m_Categorie;

   // ------------------------- Constructeurs ------------------------

   /**
    * Constructeur
    * @param p_Categorie la catégorie à wrapper
    */
   public CategorieAgeTapestry(CategorieAge p_Categorie)
   {
      m_Categorie = p_Categorie;
   }

   // ------------------------- Methodes public ------------------------

   /**
    * {@inheritDoc}
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   @Override
   public int compareTo(CategorieAgeTapestry p_Categorie)
   {
      return m_Categorie.compareTo(p_Categorie.getCategorie());
   }

   // ------------------------- Accesseurs public ------------------------

   /**
    * @return <code>true</code> si la catégorie est sélectionnée
    */
   public boolean isSelectionnee()
   {
      return m_Selectionnee;
   }

   /**
    * @param p_Selectionnee <code>true</code> si la catégorie est sélectionnée
    */
   public void setSelectionnee(boolean p_Selectionnee)
   {
      m_Selectionnee = p_Selectionnee;
   }

   /**
    * @return la catégorie
    */
   public CategorieAge getCategorie()
   {
      return m_Categorie;
   }
}
