package fr.egiov.concoursfleches;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import fr.egiov.concoursfleches.domaine.model.Archer;
import fr.egiov.concoursfleches.domaine.model.Club;
import fr.egiov.concoursfleches.enumerations.Genre;
import fr.egiov.concoursfleches.enumerations.TypeArc;
import fr.egiov.concoursfleches.services.ArcherService;

public class ConcoursCoreApp
{

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      ApplicationContext ctx = new FileSystemXmlApplicationContext(
            "classpath:/applicationContext-jpa.xml");
      // PlatformTransactionManager txManager =
      // (PlatformTransactionManager)ctx.getBean("transactionManager");

      ArcherService archerService = (ArcherService) ctx
            .getBean("archerService");

      // ConcoursService concoursService = (ConcoursService) ctx
      // .getBean("concoursService");
      try
      {
         // TransactionStatus status = txManager.getTransaction(null);
         Club club = new Club();
         club.setNom("FPM");
         club.setVille("La Motte d'Aveillans");
         club.setDepartement("38");

         Archer archer = new Archer();
         archer.setGenre(Genre.HOMME);
         archer.setNom("Dupont");
         archer.setPrenom("robert");
         archer.setAge(25);
         archer.setClub(club);
         archer.setTypeArc(TypeArc.ARC_A_POULIE_AVEC_VISEUR);
         archer.setNumeroLicence("294394839");

         // archerService.createArcher(archer);

         // List<Archer> archers = archerService.findAllArchers();
         // System.out.println(archers);

         Archer a = archerService.findArcher(new Long(6));
         System.out.println("Archer = " + a);

         // Concours concours = concoursService.findConcours(new Long(1));
         //
         // if (null == concours)
         // {
         // concours = new Concours();
         // concours.setDate(new Date());
         // concoursService.creerConcours(concours);
         // concours = concoursService.findConcours(new Long(1));
         // }
         //
         // Cible cible = new Cible();
         // cible.setArcher(a);
         // cible.setConcours(concours);
         // cible.setNom("0A");
         // concoursService.ajouterCible(concours.getConcoursId(), cible);

         // Concours concoursByDate = concoursService
         // .findConcoursByDate(new GregorianCalendar(2009, 0, 2).getTime());

         // System.out.println("concoursByDate = " + concoursByDate);

         // txManager.commit(status);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

}
