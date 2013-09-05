package arduito.rest.api

import grails.converters.JSON
import arduito.RegistroSensor
import arduito.SensorHabitacion

class SensoresApiController {

	
	
	def sensor(Long id){
		
		def sensor = SensorHabitacion.get(id)
		def lista = RegistroSensor.findAllBySensor(sensor)
		
		render lista as JSON
	}
}
