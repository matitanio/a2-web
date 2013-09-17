package arduito

import arduito.Habitacion
import arduito.RegistroAcceso

/**
 * RfeedService
 * A service class encapsulates the core business logic of a Grails application
 */
class RfeedService {

	static transactional = false

	def buscarUltimosAccesos(id,cantidad) {

		def habitacion = Habitacion.get(id as Long)
		def ultimosAccesos = formatearUltimosAccesos(RegistroAcceso.findAllByHabitacion(habitacion,[max:cantidad]))
		ultimosAccesos
	}

	private formatearUltimosAccesos(ultimosAccesos){

		def accesosFormateados = []
		ultimosAccesos.each{unAcceso ->

			accesosFormateados << [habitacion:formatHabitacion(unAcceso.habitacion),fecha:unAcceso.fecha,
				tarjeta:unAcceso?.tarjeta?.acceso,resultado:unAcceso.resultado]
		}

		accesosFormateados
	}

	private formatHabitacion(habitacion){

		[id:habitacion.id,direccion:habitacion.edificio.direccion,numero:habitacion.numero,piso:habitacion.piso]
	}

	def buscarTodosAccesos(pin) {

		def criteria = Habitacion.createCriteria()
		def habitacionesConAcceso = criteria.list{
			createAlias('rfeed','rf')
			createAlias('rf.notificables','n')
			eq('n.pin',pin)
		}
		formatearAccesos(habitacionesConAcceso)
	}
	
	private formatearAccesos(habitacionesConAcceso){
		
		def accesosFormateados = []
		habitacionesConAcceso.each{unHabitacion ->
			accesosFormateados << [habitacion:formatHabitacion(unHabitacion)] 
			
		}
		
		accesosFormateados
	}
}
