package arduito.arduino

import arduito.Habitacion
import arduito.Sensor
import arduito.SensorHabitacion

/**
 * ArduinoMessagingService
 * A service class encapsulates the core business logic of a Grails application
 */
class ArduinoMessagingService {

    boolean transactional = false
	def sensorService
	def registroService 
	static exposes = ['jms']
	static destination = "arduino.queue"
	static container = 'arduino'

	void onMessage(msg){
		
		//sensorService.sensorActivado(msg.sensor)
		
		//identificar el sensor
		def sensor = buscarSensor(msg)
		registroService.registrarMedicion(sensor, new Date(), msg.valores)
		
	}
	
	private buscarSensor(msg){
		
		def habitacion = Habitacion.get(msg.habitacion.toLong())
		def sensor = Sensor.get(msg.sensor.toLong())
		
		SensorHabitacion.findByHabitacionAndSensorAndNumeroSensor(habitacion,sensor,msg.numeroSensor)
		
	}
}
