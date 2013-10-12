package arduito

/**
 * SensorService
 * A service class encapsulates the core business logic of a Grails application
 */
class SensorService {

	static transactional = false
	def notificacionService
	def grailsApplication

	def buscarTodosLosSensores(pin) {

		def criteria = SensorHabitacion.createCriteria()
		def sensores = criteria.list{
			createAlias('notificables','n')
			eq('n.pin',pin)
		}
		formatearSensores(sensores)
	}

	private formatearSensores(sensores){

		def sensoresADevolver = [sensores:[]]
		
		sensores.each{sensor ->
			
			def habitacion = sensor.habitacion
			sensoresADevolver.sensores << [id:sensor.id,habitacion:formatHabitacion(habitacion),valorActual:sensor.valorActual,
				valorMaximo:sensor.valorMaximo,valorMinimo:sensor.valorMinimo,nombreSensor:sensor.sensor.tipo, unidad:sensor.sensor.unidades]
		}
		
		sensoresADevolver
	}
	
	private formatHabitacion(habitacion){
		
		[id:habitacion.id,direccion:habitacion.edificio.direccion,numero:habitacion.numero,piso:habitacion.piso]
	}

	def buscarLosUltimosRegistros(sensor,cantidadRegistros){

		def lista = RegistroSensor.findAllBySensor(sensor,[max:cantidadRegistros])
		formatearRegistro(lista)
	}

	private formatearRegistro(lista){

		def formateados = [valores:[]]

		lista.each{unRegistro ->

			formateados.valores << [valorMedido:unRegistro.valorRegistrado,fecha:unRegistro.fechaCreacion]
		}

		formateados
	}

	def validarMedicion(sensor,valorMedido){

		validarWarning(sensor,valorMedido as Float)
		validarRangoMedicion(sensor,valorMedido as Float)
	}

	private validarWarning(sensor,valorMedido){

		def warning = sensor.warning 
		if(warning && warning.validar(valorMedido)){
			def mensaje = [mensaje:"Se activo el Warning para el sensor: $sensor"]
			notificarSensor(sensor,mensaje)
		}
	}

	private validarRangoMedicion(sensor,valorMedido){

		if(!sensor.validar(valorMedido)){
			def mensaje = [mensaje:"Se activo el sensor: $sensor"]
			notificarSensor(sensor,mensaje)
		}
	}
	
	private notificarSensor(sensor,mensaje){
		
		sensor.notificables.each{unNotificable->
			
			unNotificable.notificar(mensaje)
		}
		
	}
	
	public comprobarEstadoSensores(){
		
		
		def sensores = SensorHabitacion.findAllByActivoAndInstalado(false,true)
		notificarSensoresInactivos(sensores)
		actualizarEstadoSensoresActivos()
	}
	
	private notificarSensoresInactivos(sensores){
		
		
		sensores.each{unSensor ->
			log.info('notificando sensor inactvo com id[' + unSensor.id +']')
			notificarSensor(unSensor,[mensaje:"El sensor de ${unSensor.sensor.tipo} de la habitacion ${unSensor.habitacion.edificio.direccion} no esta enviando valores"])
		}
	}
	
	private actualizarEstadoSensoresActivos(){
		
		log.info('restaurando estado sensores activos')
		SensorHabitacion.withTransaction {
			SensorHabitacion.executeUpdate("update SensorHabitacion s set s.activo=false where activo=true and instalado=true")
		}
	}
	
}
