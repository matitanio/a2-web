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
			mergeSensorhabitacion(sensorHabitacion,unSensor)
			habitacion.addToSensores(sensorHabitacion)
		}
	}
	
	private mergeSensorhabitacion(sensorHabitacion,unSensor){
		
		sensorHabitacion.coordenadaX = unSensor.ubicacion?unSensor.ubicacion.split(':')[1] as Float:0
		sensorHabitacion.coordenadaY = unSensor.ubicacion?unSensor.ubicacion.split(':')[0] as Float:0
		sensorHabitacion.numeroSensor = 0
		sensorHabitacion.sensor = Sensor.get(unSensor.tipo as Long)
		sensorHabitacion.valorMaximo = unSensor.max as Float
		sensorHabitacion.valorMinimo = unSensor.min as Float
		sensorHabitacion.instalado = unSensor.estado
		agregarWarning(sensorHabitacion,unSensor)
		agregarNotificables(sensorHabitacion,unSensor.notificables)
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

		def x = unaCamara.ubicacion?unaCamara.ubicacion.split(':')[1] as Float:0
		def y = unaCamara.ubicacion?unaCamara.ubicacion.split(':')[0] as Float:0
		def camara = new CamaraIp(ip:unaCamara.ip,coordenadaX:x,coordenadaY:y)
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
		actualizarSensoresModificados(habitacion,parametros.sensores)
		parametros.sensores = removerSesoresNoModificados(parametros.sensores)
		eliminarCamaras(habitacion)
		editarRfid(habitacion,parametros)
		parametros.rfid.contiene = false
		guardar(habitacion,parametros)
		
	}
	
	private editarRfid(habitacion,parametros){
		
		def rfid = parametros.rfid
		
		//la habitacion no tenia rfid y se lo estamos agregando
		if(rfid.contiene && !habitacion.rfeed){
			agregarRfid(habitacion,parametros)
		}else if(!rfid.contiene && habitacion.rfeed){//la habitacion tenia rgid y se lo estamos sacando
			
			def lectorRfid = habitacion.rfeed
			habitacion.rfeed = null
			LectorRfeed.withTransaction {
				lectorRfid.delete(flush:true)
			}
		}else if(rfid.contiene && habitacion.rfeed){//la habitacion tenia rfid y se esta modificando
			
			def lectorRfid = habitacion.rfeed
			lectorRfid.notificables.removeAll(lectorRfid.notificables)
			agregarNotificables(habitacion.rfeed ,parametros.rfid.notificables)
		}
		
		Habitacion.withTransaction {
			habitacion.save(flush:true)
		}
	}
	private actualizarSensoresModificados(habitacion,sensores){
		
		def sensoresAEditar = sensores.findAll{unSensor ->
			unSensor.id
		}
		
		def sensoresHabitacion = habitacion.sensores
		
		sensoresHabitacion.each{ sensorViejo ->
			 
			def unNuevoSensor = sensoresAEditar.find{ sensorNuevo ->
				sensorNuevo.id == sensorViejo.id
			}
			
			if(unNuevoSensor){
				removerNotificables(sensorViejo)
				mergeSensorhabitacion(sensorViejo,unNuevoSensor)
			}
		}
		
	}
	
	private removerNotificables(sensorViejo){
	
		sensorViejo.notificables.removeAll(sensorViejo.notificables)	
	}
	
	private eliminarSensores(habitacion,sensoresEliminados){
		
		sensoresEliminados.each{unSensorId ->
			
			def unSensor = SensorHabitacion.get(unSensorId as Long)
			habitacion.removeFromSensores(unSensor)
			unSensor.delete()
		}
	}
	
	private removerSesoresNoModificados(todosLosSensores){
		
		removerComponentesNoEliminadosHabitacion(todosLosSensores)
	}
	
	
	private eliminarCamaras(habitacion){
		
		def camaras = habitacion.camaras.collect {it.id}
		habitacion.camaras.removeAll(habitacion.camaras)
		
		camaras.each {id ->
			CamaraIp.withTransaction {
				CamaraIp.get(id as Long).delete(flush:true)
			}
		}
	}
	
	
	private removerComponentesNoEliminadosHabitacion(componentes){
		
		def componentesNuevos = componentes.findAll{unComponente ->
			!unComponente.id
		}
		
		componentesNuevos
	}
}
