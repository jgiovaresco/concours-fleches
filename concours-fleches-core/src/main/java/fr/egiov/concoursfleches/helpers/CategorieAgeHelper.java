package fr.egiov.concoursfleches.helpers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import fr.egiov.concoursfleches.enumerations.CategorieAge;

/**
 * Classe fournissant des méthodes pour gérer les catégories d'age;
 * 
 * @author giovarej
 */
public class CategorieAgeHelper
{
   /**
    * Donne la catégorie à partir d'un age;
    * 
    * @param p_Age
    *           l'age
    * @return {@link CategorieAge}
    */
   public static CategorieAge getCategorie(Integer p_Age)
   {
      CategorieAge categorie = null;

      if (null != p_Age)
      {
         if (p_Age < 11)
         {
            categorie = CategorieAge.C_MOINS_11_ANS;
         }
         else if (p_Age <= 12)
         {
            categorie = CategorieAge.C_11_12_ANS;
         }
         else if (p_Age <= 14)
         {
            categorie = CategorieAge.C_13_14_ANS;
         }
         else if (p_Age <= 16)
         {
            categorie = CategorieAge.C_15_16_ANS;
         }
         else if (p_Age <= 18)
         {
            categorie = CategorieAge.C_17_18_ANS;
         }
         else if (p_Age <= 49)
         {
            categorie = CategorieAge.C_19_49_ANS;
         }
         else
         {
            categorie = CategorieAge.C_50_ANS_ET_PLUS;
         }
      }
      return categorie;
   }

   /**
    * Donne la catégorie à partir d'une date de naissance;
    * 
    * @param p_DateNaissance
    *           la date de naissance
    * @return {@link CategorieAge}
    */
   public static CategorieAge getCategorie(Date p_DateNaissance)
   {
      GregorianCalendar dateNaissance = new GregorianCalendar(2000, 7, 31);
      GregorianCalendar dateJour = new GregorianCalendar();

      Integer age = dateJour.get(Calendar.YEAR)
            - dateNaissance.get(Calendar.YEAR);
      // L'age se calcul au mois de septembre de la saison
      if (dateNaissance.get(Calendar.MONTH) < Calendar.SEPTEMBER)
      {
         age -= 1;
      }

      return CategorieAgeHelper.getCategorie(age);
   }
}
