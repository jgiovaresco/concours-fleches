package fr.egiov.concoursfleches.domaine.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import fr.egiov.concoursfleches.enumerations.CategorieAge;
import fr.egiov.concoursfleches.enumerations.Genre;
import fr.egiov.concoursfleches.enumerations.TypeArc;
import fr.egiov.concoursfleches.helpers.CategorieAgeHelper;

/**
 * Définit un archer
 * 
 * @author giovarej
 */
@Entity
@Table(name = "archers")
@NamedQueries( {
      @NamedQuery(name = "Archer.count", query = "SELECT count(a.id) FROM Archer a"),
      @NamedQuery(name = "Archer.findAll", query = "SELECT a FROM Archer a ORDER BY a.nom, a.prenom") })
public class Archer implements IModel, Comparable<Archer>
{
   /** serial version uid */
   private static final long serialVersionUID = 1L;

   /** l'id de l'archer */
   private Long m_Id;

   /** le genre de l'archer */
   private Genre m_Genre;

   /** Nom de l'archer */
   private String m_Nom;

   /** Prénom de l'archer */
   private String m_Prenom;

   /** Date de naissance de l'archer */
   private Date m_DateNaissance;

   /** Age de l'archer */
   private Integer m_Age;

   /** Numéro de licence de l'archer */
   private String m_NumeroLicence;

   /** Type de l'arc de l'archer */
   private TypeArc m_TypeArc;

   /** Club du archer */
   private Club m_Club;

   /** La catégorie d'age de l'archer */
   private CategorieAge m_CategorieAge;

   /** Adresse de l'archer */
   private String m_Adresse;

   /** Code postal de l'archer */
   private String m_CodePostal;

   /** Ville de l'archer */
   private String m_Ville;

   /** Flag permettant de déterminer si l'archer est handicapé */
   private Boolean m_Handisport;

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
    * @return le genre.
    */
   @Column(name = "genre")
   public Genre getGenre()
   {
      return m_Genre;
   }

   /**
    * @param p_Genre
    *           le genre
    */
   public void setGenre(Genre p_Genre)
   {
      m_Genre = p_Genre;
   }

   /**
    * @return le Nom.
    */
   @Column(name = "nom", length = 50)
   public String getNom()
   {
      return m_Nom;
   }

   /**
    * @param p_Nom
    *           le Nom
    */
   public void setNom(String p_Nom)
   {
      m_Nom = p_Nom;
   }

   /**
    * @return le Prénom.
    */
   @Column(name = "prenom", length = 50)
   public String getPrenom()
   {
      return m_Prenom;
   }

   /**
    * @param p_Prenom
    *           le Prenom
    */
   public void setPrenom(String p_Prenom)
   {
      this.m_Prenom = p_Prenom;
   }

   /**
    * @return le nom complet
    */
   @Transient
   public String getNomComplet()
   {
      return m_Nom + " " + m_Prenom;
   }

   /**
    * @return l'Age.
    */
   @Transient
   public Integer getAge()
   {
      return m_Age;
   }

   /**
    * @param p_Age
    *           l'Age
    */
   public void setAge(Integer p_Age)
   {
      this.m_Age = p_Age;
      m_CategorieAge = CategorieAgeHelper.getCategorie(p_Age);
   }

   /**
    * @return la Catégorie d'age.
    */
   @Transient
   public CategorieAge getCategorieAge()
   {
      return m_CategorieAge;
   }

   /**
    * @return la date de naissance
    */
   public Date getDateNaissance()
   {
      return m_DateNaissance;
   }

   /**
    * @param p_DateNaissance
    *           la date de naissance
    */
   public void setDateNaissance(Date p_DateNaissance)
   {
      m_DateNaissance = p_DateNaissance;

      GregorianCalendar dateNaissance = new GregorianCalendar();
      dateNaissance.setTime(m_DateNaissance);
      GregorianCalendar dateJour = new GregorianCalendar();

      Integer age = dateJour.get(Calendar.YEAR)
            - dateNaissance.get(Calendar.YEAR);
      // L'age se calcul au mois de septembre de la saison
      if (dateJour.get(Calendar.MONTH) < Calendar.SEPTEMBER)
      {
         age -= 1;
      }

      setAge(age);
   }

   /**
    * @return le Type d'arc.
    */
   @Column(name = "type_arc")
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
   @Column(name = "licence", length = 50)
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
    * @return le Club.
    */
   @OneToOne
   public Club getClub()
   {
      return m_Club;
   }

   /**
    * @param p_Club
    *           le Club
    */
   public void setClub(Club p_Club)
   {
      this.m_Club = p_Club;
   }

   /**
    * @return le nom du Club.
    */
   @Transient
   public String getNomClub()
   {
      return m_Club.getNom();
   }

   /**
    * @return l'adresse
    */
   public String getAdresse()
   {
      return m_Adresse;
   }

   /**
    * @param p_Adresse
    *           l'adresse
    */
   public void setAdresse(String p_Adresse)
   {
      m_Adresse = p_Adresse;
   }

   /**
    * @return le code postal
    */
   public String getCodePostal()
   {
      return m_CodePostal;
   }

   /**
    * @param p_CodePostal
    *           le code postal
    */
   public void setCodePostal(String p_CodePostal)
   {
      m_CodePostal = p_CodePostal;
   }

   /**
    * @return la ville
    */
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
    * @return le flag Handisport
    */
   public Boolean getHandisport()
   {
      return m_Handisport;
   }

   /**
    * @param p_handisport
    *           le flag Handisport
    */
   public void setHandisport(Boolean p_handisport)
   {
      m_Handisport = p_handisport;
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
      if (true == (p_Obj instanceof Archer))
      {
         Archer u = (Archer) p_Obj;
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
      hash = 31 * hash + (null == m_Nom ? 0 : m_Nom.hashCode());
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
      return m_Nom + " " + m_Prenom;
   }

   /**
    * Retourne une String contenant la valeur des attributs de la classe.
    * 
    * @return String
    */
   public String toDebugString()
   {
      StringBuffer sb = new StringBuffer().append("[\n").append("user_id = '")
            .append(m_Id).append("', \nname = '").append(m_Nom).append("'\n]");
      return sb.toString();
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   @Override
   public int compareTo(Archer p_Archer)
   {
      return m_Nom.compareTo(p_Archer.getNom());
   }
}
