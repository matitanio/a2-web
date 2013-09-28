package arduito

/**
 * DispositivoMovilService
 * A service class encapsulates the core business logic of a Grails application
 */
class DispositivoMovilService {

    static transactional = false
	def springSecurityService 

	def buscarTodos(params){
		
		def c = DispositivoMovil.createCriteria()
		c.list(params,crearCriterioBusqueda())
	}
	
	private crearCriterioBusqueda(){
		
		def cuenta = springSecurityService.currentUser.cuenta
		def criterio = {
			if(cuenta){
				createAlias('owner', 'o')
				eq('o.cuenta',cuenta)
			}
		}
		
		criterio
	}
	
	
	def registrar(pin,key){
		
		def dispositivo = DispositivoMovil.findByPin(pin)
		
		dispositivo.estado = Estado.VERIFICADO
		dispositivo.key = key
		dispositivo.save(flush:true)
	}
	
	def validar(pinRegistracion,nombreUsuario){
		
		def usuario = Usuario.findByUsername(nombreUsuario)
		def dispositivo = DispositivoMovil.findByCodigoRegistroAndOwner(pinRegistracion,usuario)
		def respuesta = [resultado:'no']
		
		if(dispositivo){
			dispositivo.pin = new Date().time
			dispositivo.estado = Estado.VERIFICADO
			respuesta.resultado = 'si'
			respuesta.pin =dispositivo.pin
			dispositivo.save(flush:true)
		}
		
		respuesta 
	}
	
	def enviarCodigoRegistro(idDispositivo){
		
		def dispositivo = DispositivoMovil.get(idDispositivo)
		dispositivo.estado = Estado.VERIFICANDO
		def codigoRegistro = new Date().time
		sendMail {
			to dispositivo.owner.email
			subject "Codigo de registro para arduito movil"
			body "Hola tu codigo de registro es ${codigoRegistro}"
		}
		dispositivo.pin = null
		dispositivo.codigoRegistro = codigoRegistro
		dispositivo.save()
	}
	
}
