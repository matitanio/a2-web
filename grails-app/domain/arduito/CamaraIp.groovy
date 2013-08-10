package arduito

import org.codehaus.groovy.grails.validation.routines.InetAddressValidator

class CamaraIp {

	String ip
	
	static belongsTo = Habitacion

	    static mapping = {
    }
    
		
	static constraints = {
		
		ip validator: {
			InetAddressValidator.isValid(it)
		}
	}
}
