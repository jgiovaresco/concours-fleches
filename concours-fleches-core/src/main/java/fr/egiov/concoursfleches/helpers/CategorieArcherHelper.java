package fr.egiov.concoursfleches.helpers;

import fr.egiov.concoursfleches.domaine.model.Archer;
import fr.egiov.concoursfleches.enumerations.CategorieArcher;
import fr.egiov.concoursfleches.enumerations.Genre;
import fr.egiov.concoursfleches.enumerations.TypeArc;
import fr.egiov.concoursfleches.exceptions.helpers.CategorieArcherException;

/**
 * Classe fournissant des méthodes pour gérer les catégories d'archer;
 * 
 * @author giovarej
 */
public class CategorieArcherHelper
{
   /**
    * Donne la catégorie à partir du type d'arc et du genre de l'archer;
    * 
    * @param p_Archer
    *           L'archer
    * @return {@link CategorieArcher}
    * @throws CategorieArcherException
    *            en cas d'erreur lors de la détermination de la catégorie
    *            d'archer
    */
   public static CategorieArcher getCategorie(TypeArc p_TypeArc, Archer p_Archer)
         throws CategorieArcherException
   {
      CategorieArcher categorie = null;

      if (null != p_Archer)
      {
         if (Genre.HOMME.equals(p_Archer.getGenre()))
         {
            categorie = getCategorieHomme(p_TypeArc, p_Archer
                  .getHandisport());
         }
         else
         {
            categorie = getCategorieFemme(p_TypeArc, p_Archer
                  .getHandisport());
         }
      }
      return categorie;
   }

   /**
    * Retourne la catégorie d'archer du genre Homme
    * 
    * @param p_TypeArc
    *           le type d'arc
    * @param p_Handisport
    *           le flag Handisport
    * @return {@link CategorieArcher}
    * @throws CategorieArcherException
    *            en cas d'erreur
    */
   private static CategorieArcher getCategorieHomme(TypeArc p_TypeArc,
         Boolean p_Handisport) throws CategorieArcherException
   {
      CategorieArcher categorie = null;
      if (null != p_Handisport && true == p_Handisport)
      {
         switch (p_TypeArc)
         {
         case ARC_A_POULIE_AVEC_VISEUR:
            categorie = CategorieArcher.HANDI_SPORT_AMPAV_H;
            break;
         case ARC_A_POULIE_SANS_VISEUR:
            categorie = CategorieArcher.HANDI_SPORT_AMPSV_H;
            break;
         case ARC_CLASSIQUE_AVEC_VISEUR:
            categorie = CategorieArcher.HANDI_SPORT_CLAV_H;
            break;
         case ARC_CLASSIQUE_SANS_VISEUR:
            categorie = CategorieArcher.HANDI_SPORT_CLSV_H;
            break;
         default:
            throw new CategorieArcherException("Type d'arc inconnu");
         }
      }
      else
      {
         switch (p_TypeArc)
         {
         case ARC_A_POULIE_AVEC_VISEUR:
            categorie = CategorieArcher.AMPAV_H;
            break;
         case ARC_A_POULIE_SANS_VISEUR:
            categorie = CategorieArcher.AMPSV_H;
            break;
         case ARC_CLASSIQUE_AVEC_VISEUR:
            categorie = CategorieArcher.CLAV_H;
            break;
         case ARC_CLASSIQUE_SANS_VISEUR:
            categorie = CategorieArcher.CLSV_H;
            break;
         default:
            throw new CategorieArcherException("Type d'arc inconnu");
         }
      }
      return categorie;
   }

   /**
    * Retourne la catégorie d'archer du genre Homme
    * 
    * @param p_TypeArc
    *           le type d'arc
    * @param p_Handisport
    *           le flag Handisport
    * @return {@link CategorieArcher}
    * @throws CategorieArcherException
    *            en cas d'erreur
    */
   private static CategorieArcher getCategorieFemme(TypeArc p_TypeArc,
         Boolean p_Handisport) throws CategorieArcherException
   {
      CategorieArcher categorie = null;

      if (null != p_Handisport && true == p_Handisport)
      {
         switch (p_TypeArc)
         {
         case ARC_A_POULIE_AVEC_VISEUR:
            categorie = CategorieArcher.HANDI_SPORT_AMPAV_F;
            break;
         case ARC_A_POULIE_SANS_VISEUR:
            categorie = CategorieArcher.HANDI_SPORT_AMPSV_F;
            break;
         case ARC_CLASSIQUE_AVEC_VISEUR:
            categorie = CategorieArcher.HANDI_SPORT_CLAV_F;
            break;
         case ARC_CLASSIQUE_SANS_VISEUR:
            categorie = CategorieArcher.HANDI_SPORT_CLSV_F;
            break;
         default:
            throw new CategorieArcherException("Type d'arc inconnu");
         }
      }
      else
      {
         switch (p_TypeArc)
         {
         case ARC_A_POULIE_AVEC_VISEUR:
            categorie = CategorieArcher.AMPAV_F;
            break;
         case ARC_A_POULIE_SANS_VISEUR:
            categorie = CategorieArcher.AMPSV_F;
            break;
         case ARC_CLASSIQUE_AVEC_VISEUR:
            categorie = CategorieArcher.CLAV_F;
            break;
         case ARC_CLASSIQUE_SANS_VISEUR:
            categorie = CategorieArcher.CLSV_F;
            break;
         default:
            throw new CategorieArcherException("Type d'arc inconnu");
         }
      }
      return categorie;
   }
}
