package android

/**
 * NotificacionService
 * A service class encapsulates the core business logic of a Grails application
 */
class NotificacionService {

    static transactional = false
	def grailsApplication
	def androidGcmService
	
    def notificar(mensaje,key) {
		
		def apiKey = grailsApplication.config.android.gcm.api.key
		androidGcmService.sendMessage(messages, [dispositivo.key],
			"", apiKey)
    }
}
