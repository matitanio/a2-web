package arduito.arduino

/**
 * ArduinoMessagingService
 * A service class encapsulates the core business logic of a Grails application
 */
class ArduinoMessagingService {

    boolean transactional = false
	def sensorService
	
	static exposes = ['jms']
	static destination = "arduino.queue"
	static container = 'arduino'

	void onMessage(msg){
		
		sensorService.sensorActivado(msg.sensor)
		
	}
}
