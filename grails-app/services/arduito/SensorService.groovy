package arduito

/**
 * SensorService
 * A service class encapsulates the core business logic of a Grails application
 */
class SensorService {

	static transactional = true

	def buscarTodosLosSensores() {

		def sensores = SensorHabitacion.list()
		formatearUnSensor(sensores)
	}

	private formatearUnSensor(sensores){

		def sensoresADevolver = [sensores:[]]
		sensores.each{sensor ->
			sensoresADevolver.sensores << [id:sensor.id,habitacion:sensor.habitacion.sensor,valorActual:sensor.valorActual,
				valorMaximo:sensor.valorMaximo,valorMinimo:sensor.valorMinimo,nombreSensor:sensor.sensor.tipo]
		}
		sensoresADevolver
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
