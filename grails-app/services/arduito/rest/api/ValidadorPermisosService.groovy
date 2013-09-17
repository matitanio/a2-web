package arduito.rest.api

import arduito.DispositivoMovil
import arduito.Habitacion
import arduito.SensorHabitacion

/**
 * ValidadorPermisosService
 * A service class encapsulates the core business logic of a Grails application
 */
class ValidadorPermisosService {

	static transactional = false

	def validar(componente,id,pin) {

		def validador = validadores[componente]
		validador(id,pin)
	}

	def validadores=[
		(ComponentesValidables.SENSOR):{id,pin ->
			def sensor = SensorHabitacion.get(id as Long)
			def dispositivoMovil = DispositivoMovil.findByPin(pin)
			sensor.notificables.contains(dispositivoMovil)
		},
		(ComponentesValidables.ACCESOS):{idHabitacion,pin ->
			def habitacion = Habitacion.get(idHabitacion as Long)
			def rfeed = habitacion.rfeed
			def dispositivoMovil = DispositivoMovil.findByPin(pin)
			rfeed.notificables.contains(dispositivoMovil)
		}
	]
}

enum ComponentesValidables{

	SENSOR,CAMARA,ACCESOS
}
