package arduito.arduino

/**
 * RegistroMessagingService
 * A service class encapsulates the core business logic of a Grails application
 */
class RegistroMessagingService {

    boolean transactional = false
	def registroService 
	static exposes = ['jms']
	static destination = "registro.queue"
	static container = 'registro'

	void onMessage(msg){
		
		switch(msg.tipo) {
                case "registro-medicion":
					registroService.registrarMedicion(msg.sensorId, msg.valorMedido,msg.resultado,msg.fecha)
					break
				case "registro-acceso":
					registroService.registrarAcceso(msg.habitacion,msg.tarjeta,msg.resultado,msg.fecha)
					break
				default:
					log.error "Tipo de mensaje no reconocido"
				
		}

    }
}
