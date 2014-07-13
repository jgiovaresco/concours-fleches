package fr.egiov.concoursfleches.tapestry.model;

import java.io.Serializable;

import fr.egiov.concoursfleches.domaine.model.Cible;
import fr.egiov.concoursfleches.domaine.model.Participant;

/**
 * Wrapper Tapestry de l'objet Cible.
 * 
 * @author giovarej
 */
public class CibleTapestry implements Serializable
{
   // ------------------------- Constantes private -------------------------

   /** serial version UID */
   private static final long serialVersionUID = 1L;

   // ------------------------- Membres private ------------------------

   /** Cible sélectionnée par l'utilisateur */
   private boolean m_Selectionnee;

   /** la cible wrappée */
   private Cible m_Cible;

   // ------------------------- Constructeurs ------------------------

   /**
    * Constructeur
    * 
    * @param p_Cible
    *           la cible à wrapper
    */
   public CibleTapestry(Cible p_Cible)
   {
      m_Cible = p_Cible;
   }

   // ------------------------- Accesseurs public ------------------------

   /**
    * @return <code>true</code> si la cible est sélectionnée
    */
   public boolean isSelectionnee()
   {
      return m_Selectionnee;
   }

   /**
    * @param p_Selectionnee
    *           <code>true</code> si la cible est sélectionnée
    */
   public void setSelectionnee(boolean p_Selectionnee)
   {
      m_Selectionnee = p_Selectionnee;
   }

   /**
    * @return le nom de la cible
    */
   public String getNom()
   {
      return m_Cible.getNom();
   }

   /**
    * @return le participant
    */
   public Participant getParticipant()
   {
      return m_Cible.getParticipant();
   }
}
