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
			createAlias('owner', 'o')
			eq('o.cuenta',cuenta)
		}
		
		criterio
		
	}
}
