package arduito.arduino

import arduito.Habitacion
import arduito.RegistroAcceso
import arduito.RegistroSensor
import arduito.SensorHabitacion;
import arduito.TarjetaAcceso;

/**
 * RegistroService
 * A service class encapsulates the core business logic of a Grails application
 */
class RegistroService {

    static transactional = true
	static String formatoFecha = "dd/MM/yyyy hh:mm:ss"

    def registrarMedicion(sensorId,valorMedido,fecha) {

		def sensor = SensorHabitacion.get(sensorId as Long)
		def registro = new RegistroSensor(fechaCreacion:Date.parse(formatoFecha, fecha),sensor:sensor,valorRegistrado:valorMedido as Float)
		registro.save(flush:true)
    }
	
	def registrarAcceso(habitacionId,tarjetaId,resultado,fecha){
		
		def habitacion = Habitacion.get(habitacionId)
		def tarjeta = TarjetaAcceso.get(tarjetaId)
		def registro = new RegistroAcceso(habitacion:habitacion,tarjeta:tarjeta,resultado:resultado,fecha:Date.parse(formatoFecha, fecha))
		registro.save(flush:true)
	}
}
