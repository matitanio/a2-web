package arduito.arduino

import arduito.RegistroSensor

/**
 * RegistroService
 * A service class encapsulates the core business logic of a Grails application
 */
class RegistroService {

    static transactional = true

    def registrarMedicion(sensor,fecha,valorMedido) {

		sensor.valorActual = valorMedido
		sensor.save(flush:true)
		def registro = new RegistroSensor(fechaCreacion:fecha,sensor:sensor,valorRegistrado:valorMedido)
		registro.save(flush:true)
    }
}
