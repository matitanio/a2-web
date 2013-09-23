package arduito

import java.io.Serializable;

import org.codehaus.groovy.grails.validation.routines.InetAddressValidator

class CamaraIp implements Serializable {

	String ip
	
	static hasMany		= [notificables:DispositivoMovil]
	static belongsTo = Habitacion

	    static mapping = {
    }
    
		
	static constraints = {
		
		ip validator: {
			true
		}
	}
}
