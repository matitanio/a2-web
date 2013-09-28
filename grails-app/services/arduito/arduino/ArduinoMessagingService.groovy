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
		
		
		def sensor = buscarSensor(msg)
		def valorMedido = msg.valores
		sensor.registrarMedicion(valorMedido)
		sensorService.validarMedicion(sensor,valorMedido)
		sendJMSMessage("registro.queue", [tipo:'registro-medicion',sensorId:sensor.id,valorMedido:sensor.valorActual,fecha:new Date().format(RegistroService.formatoFecha)])
	}
	
	private buscarSensor(msg){
		
		def habitacion = Habitacion.get(msg.habitacion.toLong())
		def sensor = Sensor.get(msg.sensor.toLong())
		
		SensorHabitacion.findByHabitacionAndSensorAndNumeroSensor(habitacion,sensor,msg.numeroSensor)
		
	}
}
