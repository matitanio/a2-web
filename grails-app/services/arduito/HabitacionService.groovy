package arduito

import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * HabitacionService
 * A service class encapsulates the core business logic of a Grails application
 */
class HabitacionService {

	static transactional = false

	
	def crear(parametros) {

		def habitacion = new Habitacion()
		guardar(habitacion,parametros)
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private guardar(habitacion,parametros){
		
		habitacion.ipHabitacion = parametros.ip
		habitacion.numero = parametros.numero
		habitacion.piso = parametros.piso
		habitacion.edificio = Edificio.get(parametros.edificio as Long)
		habitacion.urlPlano = parametros.plano
		agregarSensores(habitacion,parametros.sensores)
		agregarCamaras(habitacion,parametros.camaras)
		agregarRfid(habitacion,parametros)
		
		habitacion.save(flush:true)
		
		habitacion
	}

	private agregarSensores(habitacion,sensores){

		sensores.each{unSensor ->
			def sensorHabitacion = new SensorHabitacion()
			sensorHabitacion.coordenadaX = unSensor.ubicacion.split(':')[1] as Float
			sensorHabitacion.coordenadaY = unSensor.ubicacion.split(':')[0] as Float
			sensorHabitacion.numeroSensor = 0
			sensorHabitacion.sensor = Sensor.get(unSensor.tipo as Long)
			sensorHabitacion.valorMaximo = unSensor.max as Float
			sensorHabitacion.valorMinimo = unSensor.min as Float
			agregarWarning(sensorHabitacion,unSensor)
			agregarNotificables(sensorHabitacion,unSensor.notificables)
			habitacion.addToSensores(sensorHabitacion)
		}
	}

	private agregarNotificables(elNotificable,notificables){

		notificables.each{ unNotificable ->
			elNotificable.addToNotificables(Notificable.get(unNotificable as Long))
		}
	}
	
	private agregarWarning(sensorHabitacion,sensor){
		
		if(sensor.warning.comparador != '-1'){
			
			def warning = new Warning(comparador: sensor.warning.comparador,valorWarning: sensor.warning.valorAlerta)
			sensorHabitacion.warning = warning
		}
		
	}

	private agregarCamaras(habitacion,camaras){

		camaras.each{unaCamara ->
			agregarCamara(habitacion,unaCamara)
		}
	}

	private agregarCamara(habitacion,unaCamara){

		def camara = new CamaraIp(ip:unaCamara.ip)
		agregarNotificables(camara,unaCamara.notificables)
		habitacion.addToCamaras(camara)
	}

	private agregarRfid(habitacion,parametros){

		if(parametros.rfid.contiene){

			habitacion.rfeed = new LectorRfeed()
			agregarNotificables(habitacion.rfeed ,parametros.rfid.notificables)
		}
	}
	
	def editar(parametros){
		
		def habitacion = Habitacion.get(parametros.id as Long)
		
		eliminarSensores(habitacion,parametros.sensoresEliminados)
		parametros.sensores = removerSesoresNoModificados(parametros.sensores)
		
		eliminarSensores(habitacion,parametros.camarasEliminados)
		parametros.camaras = removerCamarasNoModificados(parametros.camaras)
		parametros.rfid.contiene = false
		
		
		guardar(habitacion,parametros)
		
	}
	
	private eliminarSensores(habitacion,sensoresEliminados){
		
		sensoresEliminados.each{unSensorId ->
			
			habitacion.removeFromSensores(SensorHabitacion.get(unSensorId as Long))
		}
	}
	
	private removerSesoresNoModificados(todosLosSensores){
		
		removerComponentesNoEliminadosHabitacion(todosLosSensores)
	}
	
	
	private eliminarCamaras(habitacion,todosLasCamras){
		
		todosLasCamras.each{unaCamaraId ->
			
			habitacion.removeFromCamaras(CamaraIp.get(unaCamaraId as Long))
		}
	}
	
	private removerCamarasNoModificados(todosLasCamras){
		
		removerComponentesNoEliminadosHabitacion(todosLasCamras)
	}
	
	
	private removerComponentesNoEliminadosHabitacion(componentes){
		
		def componentesNuevos = componentes.findAll{unComponente ->
			!unComponente.id
		}
		
		componentesNuevos
	}
}
