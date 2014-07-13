package fr.egiov.concoursfleches.tapestry.pages;

import java.text.DateFormat;
import java.util.Locale;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

/**
 * Classe abstraite regroupant des méthodes communes aux Pages de l'application
 * 
 * @author giovarej
 */
public abstract class AbstractPage
{
   // ------------------------- Constantes protected -------------------------

   /** code pour un formulaire de création */
   protected static final String CODE_CREER = "new";

   // ------------------------- Membres private -------------------------

   @Inject
   private Locale m_LocaleCourante;

   /** Service permettant d'accéder aux catalogues de messages */
   @Inject
   private Messages m_Messages;

   /** Service permettant de créer un model à partir d'un bean */
   @Inject
   private BeanModelSource m_BeanModelSource;

   /** l'id du bouton cliqué */
   private String m_IdBoutonClique;

   /** l'image du lien modifier */
   @Inject
   @Path("context:images/modifier.png")
   private Asset m_ImageModifier;

   /** l'image du lien supprimer */
   @Inject
   @Path("context:images/supprimer.png")
   private Asset m_ImageSupprimer;

   // ------------------------- Accesseurs public -------------------------

   /**
    * @return la locale courante
    */
   public Locale getLocaleCourante()
   {
      return m_LocaleCourante;
   }

   /**
    * @param p_LocaleCourante
    *           la locale courante
    */
   public void setLocaleCourante(Locale p_LocaleCourante)
   {
      m_LocaleCourante = p_LocaleCourante;
   }

   /**
    * @return le format de la date. ex : jeudi 1er novembre 2008
    */
   public DateFormat getFullDateFormat()
   {
      return DateFormat.getDateInstance(DateFormat.FULL, getLocaleCourante());
   }

   /**
    * @return le format de la date. ex : 01/11/2008
    */
   public DateFormat getShortDateFormat()
   {
      return DateFormat.getDateInstance(DateFormat.SHORT, getLocaleCourante());
   }

   /**
    * @return le service Messages
    */
   public Messages getMessages()
   {
      return m_Messages;
   }

   /**
    * @return le service permettant de créer un model à partir d'un bean
    */
   public BeanModelSource getBeanModelSource()
   {
      return m_BeanModelSource;
   }

   /**
    * @return l'id du bouton cliqué
    */
   public String getIdBoutonClique()
   {
      return m_IdBoutonClique;
   }

   /**
    * @param p_IdBoutonClique
    *           l'id du bouton cliqué
    */
   public void setIdBoutonClique(String p_IdBoutonClique)
   {
      m_IdBoutonClique = p_IdBoutonClique;
   }

   /**
    * @return l'image du lien modifier
    */
   public Asset getImageModifier()
   {
      return m_ImageModifier;
   }

   /**
    * @return l'image du lien supprimer
    */
   public Asset getImageSupprimer()
   {
      return m_ImageSupprimer;
   }
}
