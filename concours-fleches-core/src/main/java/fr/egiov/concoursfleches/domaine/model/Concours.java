package fr.egiov.concoursfleches.domaine.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Définit un concours
 * 
 * @author giovarej
 */
@Entity
@Table(name = "concours")
@NamedQueries( {
      @NamedQuery(name = "Concours.count", query = "SELECT count(c.id) FROM Concours c"),
      @NamedQuery(name = "Concours.findAll", query = "SELECT c FROM Concours c"),
      @NamedQuery(name = "Concours.findConcoursByDate", query = "SELECT c FROM Concours c WHERE c.date = :dateConcours") })
public class Concours implements IModel
{
   /** serial version uid */
   private static final long serialVersionUID = 1L;

   /** Id du concours */
   private Long m_Id;

   /** Club organisateur du concours */
   private Club m_ClubOrganisateur;

   /** le type de concours */
   private String m_TypeConcours;

   /** Date du concours */
   private Date m_Date;

   /** Cibles du concours */
   private List<Cible> m_Cibles = new ArrayList<Cible>();

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
   public void setId(Long p_Id)
   {
      m_Id = p_Id;
   }

   /**
    * @return le type de concours
    */
   @Column(name = "type_concours")
   public String getTypeConcours()
   {
      return m_TypeConcours;
   }

   /**
    * @param p_TypeConcours
    *           le type de concours
    */
   public void setTypeConcours(String p_TypeConcours)
   {
      m_TypeConcours = p_TypeConcours;
   }

   /**
    * @return la date
    */
   @Column(name = "date", unique = true)
   public Date getDate()
   {
      return m_Date;
   }

   /**
    * @param p_Date
    *           la date
    */
   public void setDate(Date p_Date)
   {
      m_Date = p_Date;
   }

   /**
    * @return les cibles
    */
   @OneToMany(mappedBy = "concours", cascade = { CascadeType.ALL })
   public List<Cible> getCibles()
   {
      return m_Cibles;
   }

   /**
    * @param p_Cibles
    *           les cibles
    */
   public void setCibles(List<Cible> p_Cibles)
   {
      m_Cibles = p_Cibles;
   }

   /**
    * @return le club organisateur
    */
   @OneToOne
   public Club getClubOrganisateur()
   {
      return m_ClubOrganisateur;
   }

   /**
    * @param p_ClubOrganisateur
    *           le club organisateur
    */
   public void setClubOrganisateur(Club p_ClubOrganisateur)
   {
      m_ClubOrganisateur = p_ClubOrganisateur;
   }
}
