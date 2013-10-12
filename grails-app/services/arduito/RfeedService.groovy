package arduito

import grails.gorm.DetachedCriteria


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

			accesosFormateados << [habitacion:formatHabitacion(unAcceso.habitacion),fecha:Date.format("dd/MM/yyyy hh:mm:ss", unAcceso.fecha),
				tarjeta:unAcceso?.tarjeta?.acceso,resultado:unAcceso.resultado]
		}

		[accesos:accesosFormateados]
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
			isNotNull('rfeed')
		}
		formatearAccesos(habitacionesConAcceso)
	}
	
	private formatearAccesos(habitacionesConAcceso){
		
		def accesosFormateados = []
		habitacionesConAcceso.each{unaHabitacion ->
			accesosFormateados << [id_lector:unaHabitacion.rfeed.id,cant_accesos_validos_hoy:buscarAcceosValidosDelDia(unaHabitacion),
					,cant_accesos_no_validos_hoy:buscarAcceosNoValidosDelDia(unaHabitacion),habitacion:formatHabitacion(unaHabitacion)] 
			
		}
		
		[accesos:accesosFormateados]
	}
	
	private buscarAcceosValidosDelDia(habitacion){
		def hoy = new Date()
		hoy.clearTime()
		def criteria = crearCriteraComunAccesos(habitacion,hoy).build{
			eq('resultado',true)
		}
		
		criteria.count()
	}
	
	
	private buscarAcceosNoValidosDelDia(habitacion){
		def hoy = new Date()
		hoy.clearTime()
		
		def criteria = crearCriteraComunAccesos(habitacion,hoy).build{
			or{
				eq('resultado',false)
				isNull('tarjeta')
			}
		}
		criteria.count()
	}
	
	private crearCriteraComunAccesos(habitacion,hoy){
		
		
		new DetachedCriteria(RegistroAcceso).build{
			eq('habitacion',habitacion)
			ge('fecha',hoy)
		}
	}
}
