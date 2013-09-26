package android

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional

import arduito.DispositivoMovil
import arduito.Usuario

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
	
	
	def buscarUsuarios(cuenta){
		
		def lista = Usuario.findAllByCuenta(cuenta).collect{
			
			[id:it.id,descripcion:it.descripcion]
		}
		
		collectData(lista)
	}

	def buscarDispositivos(cuenta){
		
		
		def lista = DispositivoMovil.createCriteria().list{
			owner{
				eq('cuenta',cuenta)
			}
		}
		
		collectData(lista)
	}
	
	private collectData(collection){
		
		collection.collect{
			
			[id:it.id,descripcion:it.descripcion]
		}
	}
	
}
