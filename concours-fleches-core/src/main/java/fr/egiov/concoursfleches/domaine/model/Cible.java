package fr.egiov.concoursfleches.domaine.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Définit une cible
 * 
 * @author giovarej
 */
@Entity
@Table(name = "cibles")
@NamedQueries( { @NamedQuery(name = "Cible.findByConcours", query = "SELECT c FROM Cible c WHERE c.concours = :concours ORDER BY c.nom") })
public class Cible implements IModel
{
   /** serial version uid */
   private static final long serialVersionUID = 1L;

   /** l'id de la cible */
   private Long m_Id;

   /** le nom de la cible */
   private String m_Nom;

   /** le concours associé à la cible */
   private Concours m_Concours;

   /** le participant affecté à la cible */
   private Participant m_Participant;

   /** la serie1 */
   private Integer m_Serie1;

   /** la serie2 */
   private Integer m_Serie2;

   /** le nombre permettant de départager deux archers ayant le meme score */
   private Integer m_Departage;

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
    * @return le nom
    */
   @Column(name = "nom", length = 5)
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
    * @return le concours
    */
   @ManyToOne
   public Concours getConcours()
   {
      return m_Concours;
   }

   /**
    * @param p_Concours
    *           le concours
    */
   public void setConcours(Concours p_Concours)
   {
      m_Concours = p_Concours;
   }

   /**
    * @return le participant
    */
   @OneToOne(cascade = { CascadeType.ALL })
   public Participant getParticipant()
   {
      return m_Participant;
   }

   /**
    * @param p_Participant
    *           le participant
    */
   public void setParticipant(Participant p_Participant)
   {
      m_Participant = p_Participant;
   }

   /**
    * @return la serie1
    */
   public Integer getSerie1()
   {
      return m_Serie1;
   }

   /**
    * @param p_Serie1
    *           la serie1
    */
   public void setSerie1(Integer p_Serie1)
   {
      m_Serie1 = p_Serie1;
   }

   /**
    * @return la serie2
    */
   public Integer getSerie2()
   {
      return m_Serie2;
   }

   /**
    * @param p_Serie2
    *           la serie2
    */
   public void setSerie2(Integer p_Serie2)
   {
      m_Serie2 = p_Serie2;
   }

   /**
    * @return le total
    */
   @Transient
   public Integer getTotal()
   {
      Integer total = 0;

      if (null != m_Serie1)
      {
         total = m_Serie1;
      }
      if (null != m_Serie2)
      {
         total += m_Serie2;
      }
      return total;
   }

   /**
    * @return le nombre permettant de départager deux archers ayant le meme
    *         score
    */
   public Integer getDepartage()
   {
      return m_Departage;
   }

   /**
    * @param p_Departage
    *           le nombre permettant de départager deux archers ayant le meme
    *           score
    */
   public void setDepartage(Integer p_Departage)
   {
      m_Departage = p_Departage;
   }
}
