package arduito.arduino

/**
 * NotificacionController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class NotificacionController {

	def index = {
		
		
		sendJMSMessage("arduino.queue", params)
		render 'ok'
	}
}
