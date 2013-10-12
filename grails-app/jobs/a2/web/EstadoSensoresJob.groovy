package a2.web



class EstadoSensoresJob {
	
	def sensorService
    static triggers = {
      simple repeatInterval: 60000l // execute job once in 5 seconds
    }

    def execute() {
		
		log.info('chqueando estado sensores')
		sensorService.comprobarEstadoSensores()
    }
}
