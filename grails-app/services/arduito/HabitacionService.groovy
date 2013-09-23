package arduito

/**
 * HabitacionService
 * A service class encapsulates the core business logic of a Grails application
 */
class HabitacionService {

	static transactional = false

	def crear(parametros) {

		def habitacion = new Habitacion()
		habitacion.ipHabitacion = parametros.ip
		habitacion.numero = parametros.numero
		habitacion.piso = parametros.piso
		habitacion.edificio = Edificio.get(parametros.edificio as Long)
		habitacion.urlPlano = parametros.plano
		agregarSensores(habitacion,parametros.sensores)
		agregarCamaras(habitacion,parametros.camaras)
		agregarRfid(habitacion,parametros)
		
		Habitacion.withTransaction {
			
			habitacion.save(flush:true)
			println habitacion.errors
		}
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
			agregarNotificables(sensorHabitacion,unSensor.notificables)
		}
	}

	private agregarNotificables(elNotificable,notificables){

		notificables.each{ unDispositivo ->

			elNotificable.addToNotificables(DispositivoMovil.get(unDispositivo as Long))
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
	}

	private agregarRfid(habitacion,parametros){

		if(parametros.rfid.contiene){

			habitacion.rfeed = new LectorRfeed()
			agregarNotificables(habitacion.rfeed ,parametros.rfid.notificables)
		}
	}
}
