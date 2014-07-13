package fr.egiov.concoursfleches.domaine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.egiov.concoursfleches.enumerations.CategorieAge;
import fr.egiov.concoursfleches.enumerations.TypeArc;

/**
 * Définit un participant
 * 
 * @author giovarej
 */
@Entity
@Table(name = "participants")
public class Participant implements IModel, Comparable<Participant>
{
   // ------------------------- Constantes private -------------------------

   /** serial version uid */
   private static final long serialVersionUID = 1L;

   // ------------------------- Membres private -------------------------

   /** l'id de l'archer correspondant au Participant */
   private Long m_Id;

   /** Numéro de licence du participant */
   private String m_NumeroLicence;

   /** Type de l'arc du participant */
   private TypeArc m_TypeArc;

   /** Nom du club du participant */
   private String m_NomClub;

   /** La catégorie d'age du participant */
   private CategorieAge m_CategorieAge;

   /** L'archer représentant le participant */
   private Archer m_Archer;

   // ------------------------- Constructeur -------------------------

   /**
    * Constructeur
    */
   public Participant()
   {
   }

   /**
    * Constructeur à partir d'un {@link Archer}
    * 
    * @param p_Archer
    *           l'archer
    */
   public Participant(Archer p_Archer)
   {
      m_CategorieAge = p_Archer.getCategorieAge();
      m_NomClub = p_Archer.getClub().getNom();
      m_NumeroLicence = p_Archer.getNumeroLicence();
      m_TypeArc = p_Archer.getTypeArc();
      m_Archer = p_Archer;
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * Retourne l'ArcherId.
    * 
    * @return l'ArcherId.
    */
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Override
   public Long getId()
   {
      return m_Id;
   }

   /**
    * @param p_Id
    *           l'ArcherId
    */
   @Override
   public void setId(Long p_Id)
   {
      m_Id = p_Id;
   }

   /**
    * @return la Catégorie d'age.
    */
   public CategorieAge getCategorieAge()
   {
      return m_CategorieAge;
   }

   /**
    * @param p_CategorieAge
    *           la Catégorie d'age.
    */
   public void setCategorieAge(CategorieAge p_CategorieAge)
   {
      m_CategorieAge = p_CategorieAge;
   }

   /**
    * @return le Type d'arc.
    */
   public TypeArc getTypeArc()
   {
      return m_TypeArc;
   }

   /**
    * @param p_TypeArc
    *           le Type d'arc
    */
   public void setTypeArc(TypeArc p_TypeArc)
   {
      m_TypeArc = p_TypeArc;
   }

   /**
    * @return le Numéro de licence.
    */
   public String getNumeroLicence()
   {
      return m_NumeroLicence;
   }

   /**
    * @param p_NumeroLicence
    *           le Numéro de licence
    */
   public void setNumeroLicence(String p_NumeroLicence)
   {
      this.m_NumeroLicence = p_NumeroLicence;
   }

   /**
    * @return le nom du Club.
    */
   public String getNomClub()
   {
      return m_NomClub;
   }

   /**
    * @param p_NomClub
    *           le nom du Club
    */
   public void setNomClub(String p_NomClub)
   {
      this.m_NomClub = p_NomClub;
   }

   /**
    * @return l'archer
    */
   @ManyToOne
   public Archer getArcher()
   {
      return m_Archer;
   }

   /**
    * @param p_Archer
    *           l'archer
    */
   public void setArcher(Archer p_Archer)
   {
      m_Archer = p_Archer;
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
      if (true == (p_Obj instanceof Participant))
      {
         Participant u = (Participant) p_Obj;
         if (this.m_Id.equals(u.getId()))
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
      hash = 31 * hash
            + (null == m_Archer.getNom() ? 0 : m_Archer.getNom().hashCode());
      hash = 31 * hash + (null == m_TypeArc ? 0 : m_TypeArc.hashCode());
      return hash;
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return m_Archer.getNom() + " " + m_Archer.getPrenom();
   }

   /**
    * Retourne une String contenant la valeur des attributs de la classe.
    * 
    * @return String
    */
   public String toDebugString()
   {
      StringBuffer sb = new StringBuffer().append("[\n").append(
            "participant_id = '").append(m_Id).append("', \nname = '").append(
            m_Archer.getNom()).append("'\n]");
      return sb.toString();
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   @Override
   public int compareTo(Participant p_Participant)
   {
      return m_Archer.getNom().compareTo(p_Participant.getArcher().getNom());
   }
}
