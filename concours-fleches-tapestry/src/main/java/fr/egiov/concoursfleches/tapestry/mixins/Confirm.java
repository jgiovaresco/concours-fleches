package fr.egiov.concoursfleches.tapestry.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Attache une demande de confirmation sur l'événement onclick pour tout
 * composants implémentant {@link ClientElement}
 */
@IncludeJavaScriptLibrary("confirm-box.js")
public class Confirm
{
   /** Le message à afficher */
   @Parameter(value = "Etes-vous sur ?", defaultPrefix = BindingConstants.LITERAL)
   private String m_Message;

   /** Le service permettant d'ajouter du code HTML à la page */
   @Inject
   private RenderSupport m_RenderSupport;

   /** Référence à l'élément dont on veut ajouter une demande de confirmation */
   @InjectContainer
   private ClientElement m_Element;

   /**
    * Ajout le code Javascript créant la boite de confirmation.
    */
   @AfterRender
   public void afterRender()
   {
      m_RenderSupport.addScript(String.format("new Confirm('%s', '%s');",
            m_Element.getClientId(), m_Message));
   }
}
