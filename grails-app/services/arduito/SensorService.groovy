package arduito

/**
 * SensorService
 * A service class encapsulates the core business logic of a Grails application
 */
class SensorService {

    static transactional = true

    def buscarTodosLosSensores() {
		
		def sensores = SensorHabitacion.list()
		def sensoresADevolver = [sensores:[]]
		sensores.each{unSensor ->
			sensoresADevolver.sensores << formatearUnSensor(unSensor)
			
		}

		sensoresADevolver
    }
	
	private formatearUnSensor(sensor){
		
		[id:sensor.id,habitacion:sensor.habitacion.sensor,valorActual:sensor.valorActual,
			valorMaximo:sensor.valorMaximo,valorMinimo:sensor.valorMinimo,nombreSensor:sensor.sensor.tipo]
		
	}
	
	def buscarLosUltimosRegistros(sensor,cantidadRegistros){
		
		
		def formateados = [valores:[]]
		
		def lista = RegistroSensor.findAllBySensor(sensor,[max:cantidadRegistros])
		
		lista.each{unRegistro ->
			
			formateados.valores << [valorMedido:unRegistro.valorRegistrado,fecha:unRegistro.fechaCreacion]
		}
		
		formateados
		
	}
	
	
}
