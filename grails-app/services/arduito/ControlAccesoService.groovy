package arduito

/**
 * ControlAccesoService
 * A service class encapsulates the core business logic of a Grails application
 */
class ControlAccesoService {

    static transactional = false

    def validarAcceso(habitacionId,nroTarjeta) {
		
		
		def habitacion = Habitacion.get(habitacionId as Long)
		def acceso = TarjetaAcceso.findByAcceso(nroTarjeta) 
	
		habitacion.tarjetasConAcceso.contains(acceso)?'si':'no'	
		
    }
}
