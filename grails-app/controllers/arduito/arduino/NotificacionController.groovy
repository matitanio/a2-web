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
	
	def index = {
		
		
		//sendJMSMessage("arduino.queue", params)
		onMessage(params)
		render 'ok'
	}
	
	void onMessage(msg){
		
		//sensorService.sensorActivado(msg.sensor)
		
		//identificar el sensor
		def sensor = buscarSensor(msg)
		registroService.registrarMedicion(sensor, new Date(), msg.valores)
		//validar
		
	}
	
	private buscarSensor(msg){
		
		def habitacion = Habitacion.get(msg.habitacion.toLong())
		def sensor = Sensor.get(msg.sensor.toLong())
		
		SensorHabitacion.findByHabitacionAndSensorAndNumeroSensor(habitacion,sensor,msg.numeroSensor)
		
	}
}
