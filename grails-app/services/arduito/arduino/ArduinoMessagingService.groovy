package arduito.arduino

import arduito.Edificio
import arduito.Habitacion
import arduito.ResultadoMedicion
import arduito.Sensor;
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
		if(sensor){
			def valorMedido = msg.valores
			sensor.registrarMedicion(valorMedido)
			ResultadoMedicion resultado = sensorService.validarMedicion(sensor,valorMedido)
			sendJMSMessage("registro.queue", [tipo:'registro-medicion',
												sensorId:sensor.id,
												resultado:resultado.toString(),
												valorMedido:sensor.valorActual,
												fecha:new Date().format(RegistroService.formatoFecha)])
		}else{
		
			log.error("No se encuentra el sensor con id [${msg.idSensor}] para la habitacion con id[${msg.habitacion}]")
		}
	}
	
	private buscarSensor(msg){
		
		def habitacion = Habitacion.get(msg.habitacion.toLong())
		def edificio = Edificio.get(msg.edificio.toLong())
		def sensorHabitacion = SensorHabitacion.get(msg.idSensor.toLong())
		
		if(sensorHabitacion?.habitacion != habitacion || sensorHabitacion?.habitacion?.edificio != edificio){
			//en este caso la notificacion esta mal o se esta intentando falsificar la misma
			sensorHabitacion = null
		}
		
		sensorHabitacion
	}
}
