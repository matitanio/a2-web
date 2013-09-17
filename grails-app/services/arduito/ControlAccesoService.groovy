package arduito

import arduito.arduino.RegistroService

/**
 * ControlAccesoService
 * A service class encapsulates the core business logic of a Grails application
 */
class ControlAccesoService {

    static transactional = false

    def validarAcceso(habitacionId,nroTarjeta) {
		
		
		def habitacion = Habitacion.get(habitacionId as Long)
		def acceso = TarjetaAcceso.findByAcceso(nroTarjeta) 
		def rfeed = habitacion.rfeed
		def resultado = rfeed.tarjetasConAcceso.contains(acceso)	
		sendJMSMessage("registro.queue", [tipo:'registro-acceso',habitacion:habitacion?.id,tarjeta:acceso?.id,resultado:resultado,fecha:new Date().format(RegistroService.formatoFecha)])
		resultado?'si':'no'
    }
}
