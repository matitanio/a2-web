package arduito.rest.api

/**
 * ValidadorPermisosService
 * A service class encapsulates the core business logic of a Grails application
 */
class ValidadorPermisosService {

    static transactional = false

    def validar(componente,id,pin) {
		
		pin == '1234'
    }
}
