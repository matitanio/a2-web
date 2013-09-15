package arduito.rest.api

import grails.converters.JSON
import arduito.RegistroSensor
import arduito.SensorHabitacion

class SensoresApiController {

	def sensorService
	
	def sensor(Long id){
		
		def sensor = SensorHabitacion.get(id)
		
		def sensoresADformateadosevolver = sensorService.buscarLosUltimosRegistros(sensor,10)
		render formateados as JSON
	}
	
	
	def todos(){
		
		def sensoresADevolver = sensorService.buscarTodosLosSensores()
		render sensoresADevolver as JSON
		
	}
}
