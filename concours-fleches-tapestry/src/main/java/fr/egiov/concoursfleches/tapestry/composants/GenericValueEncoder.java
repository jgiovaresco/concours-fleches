package fr.egiov.concoursfleches.tapestry.composants;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.PropertyAdapter;

/**
 * Convertie les objets du SELECT en String pour affichage côté client.
 * @author giovarej
 * @param <T> le type de l'objet à convertir
 */
public class GenericValueEncoder<T> implements ValueEncoder<T>
{
   // ------------------------- Membres private -------------------------

   /** service permettant d'accéder à une propriété d'une classe */
   private PropertyAdapter m_IdFieldAdapter = null;

   /** les objets du SELECT */
   private List<T> m_Liste;

   // ------------------------- Constructeur -------------------------

   /**
    * Constructeur
    * @param p_Liste les objets du SELECT
    * @param p_IdField le champ affiché par le SELECT
    * @param p_PropertyAccess service permettant d'accéder à une propriété d'une classe
    */
   public GenericValueEncoder(List<T> p_Liste, String p_IdField, PropertyAccess p_PropertyAccess)
   {
      if (null == p_Liste)
      {
         p_Liste = new ArrayList<T>();
      }
      if (null != p_IdField && false == p_IdField.equalsIgnoreCase("null"))
      {
         if (p_Liste.size() > 0)
         {
            this.m_IdFieldAdapter =
               p_PropertyAccess.getAdapter(p_Liste.get(0).getClass()).getPropertyAdapter(p_IdField);
         }
      }
      this.m_Liste = p_Liste;
   }

   // ------------------------- Méthodes public -------------------------

   /**
    * {@inheritDoc}
    * @see org.apache.tapestry5.ValueEncoder#toClient(java.lang.Object)
    */
   @Override
   public String toClient(T obj)
   {
      if (m_IdFieldAdapter == null)
      {
         return construitTexteOption(obj);
      }
      else
      {
         return construitTexteOption(m_IdFieldAdapter.get(obj));
      }
   }

   /**
    * {@inheritDoc}
    * @see org.apache.tapestry5.ValueEncoder#toValue(java.lang.String)
    */
   @Override
   public T toValue(String p_Chaine)
   {
      T resultat = null;
      
      if (null == m_IdFieldAdapter)
      {
         for (T objet : m_Liste)
         {
            if (true == construitTexteOption(objet).equals(p_Chaine))
            {
               resultat = objet;
            }
         }
      }
      else
      {
         for (T objet : m_Liste)
         {
            if (true == construitTexteOption(m_IdFieldAdapter.get(objet)).equals(p_Chaine))
            {
               resultat = objet;
            }
         }
      }
      return resultat;
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
