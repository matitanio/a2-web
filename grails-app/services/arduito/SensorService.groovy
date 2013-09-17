package arduito

/**
 * SensorService
 * A service class encapsulates the core business logic of a Grails application
 */
class SensorService {

	static transactional = false

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
			println 'warning'
		}
	}

	private validarRangoMedicion(sensor,valorMedido){

		if(!sensor.validar(valorMedido)){
			println 'fuera de rango'
		}
	}
}
