package arduito.arduino

import arduito.Habitacion
import arduito.Sensor
import arduito.SensorHabitacion

/**
 * NotificacionController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class NotificacionController {

	def registroService
	def controlAccesoService
	
	def index = {
		
		sendJMSMessage("arduino.queue", params)
		render 'ok'
	}
	
	
	def controAcceso(){
		
		def habitacion = params.habitacion
		def tarjeta = params.tarjeta
		
		render controlAccesoService.validarAcceso(habitacion, tarjeta)
		
	}
}
