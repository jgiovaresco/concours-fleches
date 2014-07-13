package fr.egiov.concoursfleches.tapestry.composants;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.PropertyAdapter;
import org.apache.tapestry5.util.AbstractSelectModel;

/**
 * Modele générique pour les SELECT
 * @author giovarej
 * @param <T> le type des objets affichés par le SELECT
 */
public class GenericSelectionModel<T> extends AbstractSelectModel
{
   // ------------------------- Membres private -------------------------
   
   /** service permettant d'accéder à une propriété d'une classe */
   private PropertyAdapter m_LabelFieldAdapter = null;

   /** liste des objets affichés par le SELECT */
   private List<T> m_Liste;

   // ------------------------- Constructeurs -------------------------
   
   /**
    * Constructeur
    * @param p_Liste liste des objets affichés par le SELECT
    * @param p_LabelField champ affiché dans le SELECT
    * @param p_PropertyAccess service permettant d'accéder à une propriété d'une classe
    */
   public GenericSelectionModel(List<T> p_Liste,
                                String p_LabelField,
                                PropertyAccess p_PropertyAccess)
   {
      if (null == p_Liste)
      {
         p_Liste = new ArrayList<T>();
      }
      if (null != p_LabelField && false == p_LabelField.equalsIgnoreCase("null"))
      {
         if (p_Liste.size() > 0)
         {
            this.m_LabelFieldAdapter =
               p_PropertyAccess.getAdapter(p_Liste.get(0).getClass()).getPropertyAdapter(
                  p_LabelField);
         }
      }
      this.m_Liste = p_Liste;
   }

   // ------------------------- Méthodes public -------------------------

   /**
    * {@inheritDoc}
    * @see org.apache.tapestry5.SelectModel#getOptionGroups()
    */
   @Override
   public List<OptionGroupModel> getOptionGroups()
   {
      return null;
   }

   /**
    * {@inheritDoc}
    * @see org.apache.tapestry5.SelectModel#getOptions()
    */
   public List<OptionModel> getOptions()
   {
      List<OptionModel> optionModelList = new ArrayList<OptionModel>();
      
      for (T objet : m_Liste)
      {
         if (m_LabelFieldAdapter == null)
         {
            optionModelList.add(new OptionModelImpl(construitTexteOption(objet) + "", objet));
         }
         else
         {
            optionModelList.add(new OptionModelImpl(construitTexteOption(m_LabelFieldAdapter
               .get(objet)), objet));
         }
      }
      return optionModelList;
   }

   // ------------------------- Méthodes private -------------------------

   /**
    * Construit le texte affiché dans les options du select. Evite le NullPointerException
    * @param p_Objet l'objet
    * @return String
    */
   private String construitTexteOption(Object p_Objet)
   {
      String resultat = "";

      if (null != p_Objet)
      {
         resultat = p_Objet.toString();
      }
      return resultat;
   }
}
