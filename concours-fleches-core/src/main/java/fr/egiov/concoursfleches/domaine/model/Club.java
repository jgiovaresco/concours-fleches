package fr.egiov.concoursfleches.domaine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Définit un club
 * 
 * @author giovarej
 */
@Entity
@Table(name = "club")
@NamedQueries( {
      @NamedQuery(name = "Club.count", query = "SELECT count(c.id) FROM Club c"),
      @NamedQuery(name = "Club.findAll", query = "SELECT c FROM Club c ORDER BY c.nom") })
public class Club implements IModel, Comparable<Club>
{
   /** serial version uid */
   private static final long serialVersionUID = 1L;

   /** L'id du club */
   private Long m_Id;

   /** Le nom du club */
   private String m_Nom;

   /** La ville du club */
   private String m_Ville;

   /** le département du club */
   private String m_Departement;

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.model.IModel#getId()
    */
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Override
   public Long getId()
   {
      return m_Id;
   }

   /**
    * {@inheritDoc}
    * 
    * @see fr.egiov.concoursfleches.domaine.model.IModel#setId(java.lang.Long)
    */
   @Override
   public void setId(Long p_clubId)
   {
      m_Id = p_clubId;
   }

   /**
    * @return le nom
    */
   @Column(name = "nom", length = 50)
   public String getNom()
   {
      return m_Nom;
   }

   /**
    * @param p_Nom
    *           le nom
    */
   public void setNom(String p_Nom)
   {
      m_Nom = p_Nom;
   }

   /**
    * @return la ville
    */
   @Column(name = "ville", length = 50)
   public String getVille()
   {
      return m_Ville;
   }

   /**
    * @param p_Ville
    *           la ville
    */
   public void setVille(String p_Ville)
   {
      m_Ville = p_Ville;
   }

   /**
    * @return le département
    */
   @Column(name = "departement")
   public String getDepartement()
   {
      return m_Departement;
   }

   /**
    * @param p_Departement
    *           le département
    */
   public void setDepartement(String p_Departement)
   {
      m_Departement = p_Departement;
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return m_Nom;
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object p_Obj)
   {
      Boolean sameObject = false;
      if (true == (p_Obj instanceof Club))
      {
         Club c = (Club) p_Obj;
         if (this.m_Id.equals(c.getId()))
         {
            sameObject = true;
         }
      }
      return sameObject;
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode()
   {
      int hash = 7;
      hash = 31 * hash + m_Id.hashCode();
      hash = 31 * hash + (null == m_Nom ? 0 : m_Nom.hashCode());
      hash = 31 * hash + (null == m_Departement ? 0 : m_Departement.hashCode());
      return hash;
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   @Override
   public int compareTo(Club p_Club)
   {
      return m_Nom.compareTo(p_Club.getNom());
   }
}
