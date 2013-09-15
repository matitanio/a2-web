package arduito.rest.api

import grails.converters.JSON
import arduito.SensorHabitacion
import arduito.annotations.TienePermisos

class SensoresApiController {

	def sensorService
	
	@TienePermisos(componente=ComponentesValidables.SENSOR)
	def sensor(Long id){
		
		def sensor = SensorHabitacion.get(id)
		
		def formateados = sensorService.buscarLosUltimosRegistros(sensor,10)
		render formateados as JSON
	}
	
	
	def todos(){
		
		def sensoresADevolver = sensorService.buscarTodosLosSensores(params.pin)
		render sensoresADevolver as JSON
		
	}
}
