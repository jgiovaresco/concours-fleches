package fr.egiov.concoursfleches.tapestry.datasource;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.egiov.concoursfleches.domaine.dao.IDao;
import fr.egiov.concoursfleches.domaine.dao.util.CritereTri;

/**
 * Implémentation de l'interface GridDataSource fonctionnant avec {@link IDao}
 * 
 * @author giovarej
 * 
 * @param <T>
 *           le type de l'entité
 */
public class GenericDaoSource<T> implements GridDataSource
{
   /** le logger */
   private final Logger m_Logger = LoggerFactory
         .getLogger(GenericDaoSource.class);

   /** Dao */
   private IDao<T> m_Dao;

   /** Entité sélectionnés */
   private List<T> m_Selection;

   /** Index du début de l'intervalle de la sélection */
   private Integer m_IndexDebut;

   /** le nombre de résultat */
   private Integer m_NombreResultats;

   /**
    * Constructeur
    * 
    * @param p_Dao
    *           la dao
    */
   public GenericDaoSource(IDao<T> p_Dao)
   {
      m_Dao = p_Dao;
   }

   /**
    * {@inheritDoc}
    * 
    * @see org.apache.tapestry5.grid.GridDataSource#getAvailableRows()
    */
   @Override
   public int getAvailableRows()
   {
      if (null == m_NombreResultats)
      {
         m_NombreResultats = m_Dao.count();
      }
      return m_NombreResultats;
   }

   /**
    * {@inheritDoc}
    * 
    * @see org.apache.tapestry5.grid.GridDataSource#getRowType()
    */
   @Override
   public Class<T> getRowType()
   {
      return m_Dao.getClasseEntite();
   }

   /**
    * {@inheritDoc}
    * 
    * @see org.apache.tapestry5.grid.GridDataSource#getRowValue(int)
    */
   @Override
   public Object getRowValue(int p_NumLigne)
   {
      return m_Selection.get(p_NumLigne - m_IndexDebut);
   }

   /**
    * {@inheritDoc}
    * 
    * @see org.apache.tapestry5.grid.GridDataSource#prepare(int, int,
    *      java.util.List)
    */
   @Override
   public void prepare(int p_IndexDebut, int p_IndexFin,
         List<SortConstraint> p_SortConstraints)
   {
      List<CritereTri> criteres = new ArrayList<CritereTri>();

      for (SortConstraint sortConstraint : p_SortConstraints)
      {
         String nomColonne = sortConstraint.getPropertyModel()
               .getPropertyName();
         Boolean ordre = ColumnSort.ASCENDING.equals(sortConstraint
               .getColumnSort());

         m_Logger.debug("colonne a trier = {}", nomColonne);

         criteres.add(new CritereTri(nomColonne, ordre));
      }

      m_Selection = m_Dao.findAndOrderByCriteria(p_IndexDebut, p_IndexFin,
            criteres);
      m_IndexDebut = p_IndexDebut;
   }
}
