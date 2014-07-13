package fr.egiov.concoursfleches.tapestry.pages;

import org.apache.tapestry5.annotations.Persist;

/**
 * Classe abstraite regroupant des méthodes communes aux Pages de l'application
 * 
 * @author giovarej
 */
public abstract class AbstractFormPage extends AbstractPage
{
   // ------------------------- Constantes protected -------------------------

   /** code pour un formulaire de création */
   protected static final String CODE_CREER = "new";

   // ------------------------- Membres private -------------------------

   /** Type du formulaire */
   @Persist
   private TypeFormulaire m_TypeFormulaire;

   // ------------------------- Accesseurs public -------------------------

   /**
    * @return le type de formulaire
    */
   public TypeFormulaire getTypeFormulaire()
   {
      return m_TypeFormulaire;
   }

   /**
    * @param p_TypeFormulaire
    *           le type de formulaire
    */
   public void setTypeFormulaire(TypeFormulaire p_TypeFormulaire)
   {
      m_TypeFormulaire = p_TypeFormulaire;
   }

   /**
    * @return <code>true</code> si le formulaire est de type création
    */
   public boolean isCreerFormulaire()
   {
      return (true == TypeFormulaire.CREATION.equals(getTypeFormulaire()));
   }
}
