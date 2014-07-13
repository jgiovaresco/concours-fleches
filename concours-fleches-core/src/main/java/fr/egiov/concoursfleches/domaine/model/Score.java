package fr.egiov.concoursfleches.domaine.model;

import java.io.Serializable;

/**
 * Définit un score
 * 
 * @author giovarej
 */
public class Score implements Serializable, Comparable<Score>
{
   /** serial version UID */
   private static final long serialVersionUID = 1L;

   // ------------------------- Membres private -------------------------

   /** la cible de l'archer */
   private Cible m_Cible;

   // ------------------------- Méthodes public -------------------------

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   @Override
   public int compareTo(Score p_Score)
   {
      int compare = m_Cible.getTotal().compareTo(p_Score.getTotal());

      if (0 == compare)
      {
         compare = m_Cible.getDepartage().compareTo(p_Score.getDepartage());
      }

      // l'opposé pour classer par ordre décroissant
      return -(compare);
   }

   /**
    *{@inheritDoc}
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object p_Objet)
   {
      boolean egal = false;
      if (p_Objet instanceof Score)
      {
         Score score = (Score) p_Objet;
         if ((true == m_Cible.getTotal().equals(score.getTotal()))
               && m_Cible.getDepartage().equals(score.getDepartage()))
         {
            egal = true;
         }
      }
      return egal;
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * @return l'archer
    */
   public Participant getParticipant()
   {
      return m_Cible.getParticipant();
   }

   /**
    * @return la 1ere série de l'archer
    */
   public Cible getCible()
   {
      return m_Cible;
   }

   /**
    * @param p_Cible
    *           la cible de l'archer
    */
   public void setCible(Cible p_Cible)
   {
      m_Cible = p_Cible;
   }

   /**
    * @return le total des points
    */
   public Integer getTotal()
   {
      return m_Cible.getTotal();
   }

   /**
    * @return le nombre permettant de départager deux archers ayant le meme
    *         score
    */
   public Integer getDepartage()
   {
      return m_Cible.getDepartage();
   }
}
