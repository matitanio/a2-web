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
	
	
	def todos(){
		
//		JSON.use('deep')
		def sensores = SensorHabitacion.list()
		
		def sensoresADevolver = [sensores:[]]
		sensores.each{
			
			sensoresADevolver.sensores << [id:it.id,habitacion:it.habitacion.id,valorActual:it.valorActual,
				valorMaximo:it.valorMaximo,valorMinimo:it.valorMinimo,nombreSensor:it.sensor.tipo]
			
		}
		
		render sensoresADevolver as JSON
		
	}
}
