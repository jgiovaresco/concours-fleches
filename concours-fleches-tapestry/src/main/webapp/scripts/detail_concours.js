var DetailConcours = Class.create( {
   /**
    * Constructeur
    */
   initialize : function() {
      /** id de la Zone actualisé lors d'un submit du form */
      this.ID_ZONE = "formZone";

      this.ID_TOTAL = "total";
      this.ID_SERIE1 = "serie1";
      this.ID_SERIE2 = "serie2";

      Event.observe(this.ID_ZONE, Tapestry.ZONE_UPDATED_EVENT,
            this.ciblesEnregistrees.bind(this));
   },

   /**
    * Affiche le message "Cibles Enregistrees" lors du clic sur le bouton
    * "Enregistrer"
    */
   ciblesEnregistrees : function() {
      $T(this.ID_ZONE).fadeDuration = 1;
      $(this.ID_ZONE).show();
      new Effect.Fade($(this.ID_ZONE), {
         duration : $T(this.ID_ZONE).fadeDuration
      });
      
      // Mise a jours des totaux
      this.updateTotaux();
   },

   /**
    * Mise a jour des totaux
    */
   updateTotaux : function() {
      var nbCibles = $("nbCibles").value;
      var idTotal = this.ID_TOTAL;
      var idSerie1 = this.ID_SERIE1;
      var idSerie2 = this.ID_SERIE2;
      
      // mise a jour du total d'identifiant "total"
      this.updateTotal(idTotal, idSerie1, idSerie2);
      
      // mise a jour des totaux d'identifiant "total_0" a "total_nbCibles-1"
      
      for (var i = 0; i < nbCibles - 1; i++) {
         idTotal = this.ID_TOTAL + "_" + i;
         idSerie1 = this.ID_SERIE1 + "_" + i;
         idSerie2 = this.ID_SERIE2 + "_" + i;
         
         this.updateTotal(idTotal, idSerie1, idSerie2);
      }
   },
   
   /**
    * <ul>
    * <li>Recupere les valeurs des series dans les input d'id "p_IdSerie1" et "p_IdSerie2".</li>
    * <li>Calcul le total et l'affecte a l'input d'id "p_IdTotal"</li>
    * </ul>
    */
   updateTotal: function (p_IdTotal, p_IdSerie1, p_IdSerie2)
   {
      var serie1 = parseInt($(p_IdSerie1).value);
      var serie2 = parseInt($(p_IdSerie2).value);
      
      if (true == isNaN(serie1))
      {
         serie1 = 0;
      }
      if (true == isNaN(serie2))
      {
         serie2 = 0;
      }
      
      $(p_IdTotal).value = serie1 + serie2;
   }
});

Event.observe(window, "load", function() {
   new DetailConcours();
});