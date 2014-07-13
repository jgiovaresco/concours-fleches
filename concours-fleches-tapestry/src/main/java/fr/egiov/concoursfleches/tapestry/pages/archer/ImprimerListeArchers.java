package fr.egiov.concoursfleches.tapestry.pages.archer;

import java.util.List;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.egiov.concoursfleches.domaine.model.Archer;
import fr.egiov.concoursfleches.services.ArcherService;
import fr.egiov.concoursfleches.tapestry.pages.AbstractPage;

/**
 * Page affichant la liste des archers
 * 
 * @author giovarej
 */
@IncludeStylesheet( { "context:css/imprimer_liste_archer.css" })
public class ImprimerListeArchers extends AbstractPage
{
   // ------------------------- Constantes public -------------------------

   // ------------------------- Membres private -------------------------

   /** Service archer */
   @Inject
   private ArcherService m_ArcherService;

   /** liste des archers */
   private List<Archer> m_Archers;

   /** variable utilisée par Tapestry pour construire la liste des archers */
   private Archer m_Archer;

   // ------------------------- Methodes package scoped ------------------------

   /**
    * Méthode appelée pour préparer l'affichage de la page
    */
   void setupRender()
   {
      m_Archers = m_ArcherService.findAllArchers();
   }

   // ------------------------- Accesseurs public -------------------------

   /**
    * @return la liste des archers
    */
   public List<Archer> getArchers()
   {
      return m_Archers;
   }

   /**
    * @return l'archer
    */
   public Archer getArcher()
   {
      return m_Archer;
   }

   /**
    * @param p_archer
    *           l'archer
    */
   public void setArcher(Archer p_archer)
   {
      m_Archer = p_archer;
   }
}
