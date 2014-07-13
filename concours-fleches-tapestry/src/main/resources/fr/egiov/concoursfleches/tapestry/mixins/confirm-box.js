/**
 * Attache une demande de confirmation sur l'evenement onclic d'un element HTML
 */
var Confirm = Class.create();
Confirm.prototype = {
	/**
	 * Constructeur
	 * 
	 * @param p_Element
	 *            L'element HTML dont on veut attacher la demande de
	 *            confirmation
	 * @param p_Message
	 *            Le message de la demande de confirmation
	 */
	initialize : function(p_Element, p_Message) {
		this.m_Message = p_Message;
		Event.observe($(p_Element), 'click', this.doConfirm
				.bindAsEventListener(this));
	},

	/**
	 * Callback appelee lors d'un clic sur l'element HTML
	 * @param p_Event L'evenement
	 */
	doConfirm : function(p_Event) {
		if (false == confirm(this.m_Message)) {
			p_Event.stop();
		}
	}
}