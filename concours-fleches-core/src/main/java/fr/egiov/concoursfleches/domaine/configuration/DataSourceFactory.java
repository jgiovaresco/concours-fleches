package fr.egiov.concoursfleches.domaine.configuration;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Factory permettant d'utiliser le bon DataSource
 * 
 * @author giovarej
 */
public class DataSourceFactory
{
   // ------------------------- Membres private -------------------------

   /** le logger */
   private final Logger m_Logger = LoggerFactory
         .getLogger(DataSourceFactory.class);

   /** datasource pour une base de donnée HSQL */
   private DataSource m_HsqldbDataSource;

   /** datasource pour une base de donnée HSQL in-memory */
   private DataSource m_InMemoryHsqldbDataSource;

   /** datasource utilisé */
   private DataSource m_CurrentDataSource = null;

   /** permet d'utiliser Hibernate avec JPA */
   private HibernateJpaVendorAdapter m_HibernateJpaVendorAdapter;

   // ------------------------- Méthodes public -------------------------

   /**
    * Méthode d'initialisation de la factory
    */
   public void init()
   {
      m_Logger.warn("Initializing database connection pool.");
      m_HibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
      connecterHsqldbDataSource();
   }

   /**
    * Se connecte à une base HSQL.
    */
   private void connecterHsqldbDataSource()
   {
      this.m_HibernateJpaVendorAdapter.setDatabase(Database.HSQL);
      this.m_HibernateJpaVendorAdapter.setGenerateDdl(true);
      this.m_HibernateJpaVendorAdapter.setShowSql(true);
      try
      {
         Connection conn = m_HsqldbDataSource.getConnection();
         conn.close();
         this.m_CurrentDataSource = m_HsqldbDataSource;
         m_Logger.warn("HSQLDB database connected.");
      }
      catch (SQLException sqle)
      {
         m_Logger.warn("HSQLDB database could not be connected : "
               + sqle.getMessage());

         launchInMemoryHsqldbDataSource();
      }
   }

   /**
    * Lance une base de donnée HSQL in-memory et créer un datasource
    */
   private void launchInMemoryHsqldbDataSource()
   {
      this.m_HibernateJpaVendorAdapter.setDatabase(Database.HSQL);
      this.m_HibernateJpaVendorAdapter.setGenerateDdl(true);
      this.m_HibernateJpaVendorAdapter.setShowSql(true);
      try
      {
         Connection conn = m_InMemoryHsqldbDataSource.getConnection();
         conn.close();
         this.m_CurrentDataSource = m_InMemoryHsqldbDataSource;
         m_Logger.warn("HSQLDB database started.");
      }
      catch (SQLException sqle)
      {
         m_Logger.warn("HSQLDB database could not be started : "
               + sqle.getMessage());
         m_Logger.error(
               "No database could be used, the application cannot run!", sqle);
      }
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * @param p_HsqldbDataSource
    *           la datasource HSQL
    */
   public void setHsqldbDataSource(DataSource p_HsqldbDataSource)
   {
      this.m_HsqldbDataSource = p_HsqldbDataSource;
   }

   /**
    * @param p_InMemoryHsqldbDataSource
    *           la datasource HSQL in-memory
    */
   public void setInMemoryHsqldbDataSource(DataSource p_InMemoryHsqldbDataSource)
   {
      this.m_InMemoryHsqldbDataSource = p_InMemoryHsqldbDataSource;
   }

   /**
    * @return le datasource utilisé
    */
   public DataSource getDataSource()
   {
      return this.m_CurrentDataSource;
   }

   /**
    * @return le HibernateJpaVendorAdapter
    */
   public HibernateJpaVendorAdapter getHibernateJpaVendorAdapter()
   {
      return this.m_HibernateJpaVendorAdapter;
   }
}
