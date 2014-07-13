package fr.egiov.concoursfleches.helpers;

import java.util.Arrays;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.egiov.collections.SortedList;
import fr.egiov.concoursfleches.domaine.model.Resultat;
import fr.egiov.concoursfleches.domaine.model.Score;
import fr.egiov.concoursfleches.enumerations.CategorieAge;
import fr.egiov.concoursfleches.enumerations.CategorieArcher;
import fr.egiov.concoursfleches.services.ConcoursService;

/**
 * Assistant permettant de générer un fichier Excel contenant les résultats d'un
 * concours
 * 
 * @author giovarej
 */
@Component("excelHelper")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExcelHelper
{
   private Integer m_NumeroLigne = 0;

   // ------------------------- Constantes private -------------------------

   /** Index de la 1ere colonne */
   private static final int PREMIERE_COLONNE = 0;

   /** Index de la cellule contenant la position de l'archer */
   private static final int NUMERO_CELLULE_POSITION = 0;

   /** Index de la cellule contenant le nom de l'archer */
   private static final int NUMERO_CELLULE_ARCHER = 1;

   /** Index de la cellule contenant le club de l'archer */
   private static final int NUMERO_CELLULE_CLUB = 2;

   /** Index de la cellule contenant le score de la 1ere série de l'archer */
   private static final int NUMERO_CELLULE_SERIE1 = 3;

   /** Index de la cellule contenant le score de la 2e série de l'archer */
   private static final int NUMERO_CELLULE_SERIE2 = 4;

   /** Index de la cellule contenant le score total de l'archer */
   private static final int NUMERO_CELLULE_SCORE = 5;

   // ------------------------- Membres private -------------------------

   /** Service concours */
   @Autowired
   @Qualifier("concoursService")
   private ConcoursService m_ConcoursService;

   // ------------------------- Méthodes public -------------------------

   /**
    * Génère le document excel du résultat d'un concours
    * 
    * @param p_ConcoursId
    *           l'identifiant du concours
    * @return le document Excel
    * @throws Exception
    *            en cas d'erreur
    */
   public HSSFWorkbook genererExcel(Long p_ConcoursId) throws Exception
   {
      Resultat resultat = m_ConcoursService.calculerResultats(p_ConcoursId);

      // create a new workbook
      HSSFWorkbook wb = new HSSFWorkbook();
      // create a new sheet
      HSSFSheet sheet = wb.createSheet();

      Map<CategorieArcher, SortedList<Score>> classementCategorieAge = null;
      for (CategorieAge categorieAge : Arrays.asList(CategorieAge.values()))
      {
         classementCategorieAge = resultat.getClassement().get(categorieAge);

         if (null != classementCategorieAge)
         {
            genererClassementCategorieAge(wb, sheet, categorieAge,
                  classementCategorieAge);
         }
      }

      return wb;
   }

   // ------------------------- Méthodes private -------------------------

   /**
    * Génère le classement d'une catégorie d'age
    * 
    * @param p_HSSFWorkbook
    *           le document excel
    * @param p_Sheet
    *           la feuille de calcul
    * @param p_CategorieAge
    *           la catégorie d'age dont on veut générer le classement
    * @param p_ClassementCategorieAge
    *           le classement du concours
    */
   private void genererClassementCategorieAge(HSSFWorkbook p_HSSFWorkbook,
         HSSFSheet p_Sheet, CategorieAge p_CategorieAge,
         Map<CategorieArcher, SortedList<Score>> p_ClassementCategorieAge)
   {
      // Ligne contenant la categorie d'age
      HSSFRow row = creerLigneCategorieAge(p_Sheet);

      // Creation de la cellule contenant la catégorie d'age
      HSSFCell cell = row.createCell(PREMIERE_COLONNE);
      cell.setCellStyle(getStyleCategorieAge(p_HSSFWorkbook));
      cell.setCellValue(new HSSFRichTextString(p_CategorieAge.getLabel()));

      SortedList<Score> scores = null;
      for (CategorieArcher categorieArcher : Arrays.asList(CategorieArcher
            .values()))
      {
         scores = p_ClassementCategorieAge.get(categorieArcher);
         if (null != scores)
         {
            genererClassementCategorieArcher(p_HSSFWorkbook, p_Sheet,
                  categorieArcher, scores);
         }
      }
   }

   /**
    * Génère le classement d'une catégorie d'archer
    * 
    * @param p_HSSFWorkbook
    *           le document excel
    * @param p_Sheet
    *           la feuille de calcul
    * @param p_CategorieArcher
    *           la catégorie d'archer dont on veut générer le classement
    * @param p_Scores
    *           les scores du concours
    */
   private void genererClassementCategorieArcher(HSSFWorkbook p_HSSFWorkbook,
         HSSFSheet p_Sheet, CategorieArcher p_CategorieArcher,
         SortedList<Score> p_Scores)
   {
      // Ligne contenant la categorie archer
      HSSFRow row = creerLigneCategorieArcher(p_Sheet);

      // Creation de la cellule contenant la catégorie archer
      HSSFCell cell = row.createCell(PREMIERE_COLONNE);
      cell.setCellStyle(getStyleCategorieArcher(p_HSSFWorkbook));
      cell.setCellValue(new HSSFRichTextString(p_CategorieArcher.getLabel()));

      // Creation des entetes du classement
      // Ligne contenant l'entete du classement
      row = creerLigneEnteteClassement(p_Sheet);

      // Cellule Position
      cell = row.createCell(NUMERO_CELLULE_POSITION);
      cell.setCellStyle(getStyleEnteteClassment(p_HSSFWorkbook));
      cell.setCellValue(new HSSFRichTextString("Position"));
      // Cellule Archer
      cell = row.createCell(NUMERO_CELLULE_ARCHER);
      cell.setCellStyle(getStyleEnteteClassment(p_HSSFWorkbook));
      cell.setCellValue(new HSSFRichTextString("Archer"));
      // Cellule Club
      cell = row.createCell(NUMERO_CELLULE_CLUB);
      cell.setCellStyle(getStyleEnteteClassment(p_HSSFWorkbook));
      cell.setCellValue(new HSSFRichTextString("Club"));
      // Cellule Serie1
      cell = row.createCell(NUMERO_CELLULE_SERIE1);
      cell.setCellStyle(getStyleEnteteClassment(p_HSSFWorkbook));
      cell.setCellValue(new HSSFRichTextString("Serie 1"));
      // Cellule Serie2
      cell = row.createCell(NUMERO_CELLULE_SERIE2);
      cell.setCellStyle(getStyleEnteteClassment(p_HSSFWorkbook));
      cell.setCellValue(new HSSFRichTextString("Serie 2"));
      // Cellule Score
      cell = row.createCell(NUMERO_CELLULE_SCORE);
      cell.setCellStyle(getStyleEnteteClassment(p_HSSFWorkbook));
      cell.setCellValue(new HSSFRichTextString("Score"));

      int position = 1;
      for (Score score : p_Scores)
      {
         genererLigneScore(p_Sheet, position, score);
         position++;
      }
   }

   /**
    * Génère la ligne représentant un archer
    * 
    * @param p_Sheet
    *           la feuille de calcul
    * @param p_Position
    *           la position de l'archer
    * @param p_Score
    *           l'objet contenant les informations à afficher
    */
   private void genererLigneScore(HSSFSheet p_Sheet, Integer p_Position,
         Score p_Score)
   {
      // Ligne contenant le score
      HSSFRow row = creerLigne(p_Sheet);

      // Cellule Position
      row.createCell(NUMERO_CELLULE_POSITION).setCellValue(p_Position);
      // Cellule Archer
      row.createCell(NUMERO_CELLULE_ARCHER).setCellValue(
            new HSSFRichTextString(p_Score.getCible().getParticipant()
                  .getArcher().getNomComplet()));
      // Cellule Club
      row.createCell(NUMERO_CELLULE_CLUB).setCellValue(
            new HSSFRichTextString(p_Score.getCible().getParticipant()
                  .getArcher().getClub().getNom()));
      // Cellule Serie1
      if (null != p_Score.getCible().getSerie1())
      {
         row.createCell(NUMERO_CELLULE_SERIE1).setCellValue(
               p_Score.getCible().getSerie1());
      }
      else
      {
         row.createCell(NUMERO_CELLULE_SERIE1).setCellValue(0);
      }

      // Cellule Serie2
      if (null != p_Score.getCible().getSerie2())
      {
         row.createCell(NUMERO_CELLULE_SERIE2).setCellValue(
               p_Score.getCible().getSerie2());
      }
      else
      {
         row.createCell(NUMERO_CELLULE_SERIE2).setCellValue(0);
      }
      // Cellule Score
      row.createCell(NUMERO_CELLULE_SCORE).setCellValue(p_Score.getTotal());
   }

   /**
    * Créer une ligne contenant la catégorie d'age dans la feuille de calcul
    * 
    * @param p_Sheet
    *           la feuille de calcul
    * @return la ligne créée
    */
   private HSSFRow creerLigneCategorieAge(HSSFSheet p_Sheet)
   {
      m_NumeroLigne += 3;
      return p_Sheet.createRow(m_NumeroLigne);
   }

   /**
    * Créer une ligne contenant la catégorie d'archer dans la feuille de calcul
    * 
    * @param p_Sheet
    *           la feuille de calcul
    * @return la ligne créée
    */
   private HSSFRow creerLigneCategorieArcher(HSSFSheet p_Sheet)
   {
      m_NumeroLigne += 2;
      return p_Sheet.createRow(m_NumeroLigne);
   }

   /**
    * Créer une ligne contenant les entetes du classement dans la feuille de
    * calcul
    * 
    * @param p_Sheet
    *           la feuille de calcul
    * @return la ligne créée
    */
   private HSSFRow creerLigneEnteteClassement(HSSFSheet p_Sheet)
   {
      m_NumeroLigne += 2;
      return p_Sheet.createRow(m_NumeroLigne);
   }

   /**
    * Créer une ligne "simple" dans la feuille de calcul
    * 
    * @param p_Sheet
    *           la feuille de calcul
    * @return la ligne créée
    */
   private HSSFRow creerLigne(HSSFSheet p_Sheet)
   {
      m_NumeroLigne++;
      return p_Sheet.createRow(m_NumeroLigne);
   }

   /**
    * Génère le style utilisé pour les cellules contenant la catégorie d'age
    * 
    * @param p_HSSFWorkbook
    *           le document excel
    * @return le style généré
    */
   private HSSFCellStyle getStyleCategorieAge(HSSFWorkbook p_HSSFWorkbook)
   {
      HSSFCellStyle cellStyle = p_HSSFWorkbook.createCellStyle();

      // create 2 fonts objects
      HSSFFont font = p_HSSFWorkbook.createFont();

      // set font 1 to 12 point type
      font.setFontHeightInPoints((short) 14);
      // make it bold
      // arial is the default font
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

      cellStyle.setFont(font);
      return cellStyle;
   }

   /**
    * Génère le style utilisé pour les cellules contenant la catégorie d'archer
    * 
    * @param p_HSSFWorkbook
    *           le document excel
    * @return le style généré
    */
   private HSSFCellStyle getStyleCategorieArcher(HSSFWorkbook p_HSSFWorkbook)
   {
      HSSFCellStyle cellStyle = p_HSSFWorkbook.createCellStyle();

      // create 2 fonts objects
      HSSFFont font = p_HSSFWorkbook.createFont();

      // set font 1 to 12 point type
      font.setFontHeightInPoints((short) 12);
      // make it bold
      // arial is the default font
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

      cellStyle.setFont(font);
      return cellStyle;
   }

   /**
    * Génère le style utilisé pour les cellules contenant les entetes du
    * classement
    * 
    * @param p_HSSFWorkbook
    *           le document excel
    * @return le style généré
    */
   private HSSFCellStyle getStyleEnteteClassment(HSSFWorkbook p_HSSFWorkbook)
   {
      HSSFCellStyle cellStyle = p_HSSFWorkbook.createCellStyle();

      // create 2 fonts objects
      HSSFFont font = p_HSSFWorkbook.createFont();

      // set font 1 to 12 point type
      font.setFontHeightInPoints((short) 10);
      // make it bold
      // arial is the default font
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

      cellStyle.setFont(font);
      cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

      return cellStyle;
   }
}
