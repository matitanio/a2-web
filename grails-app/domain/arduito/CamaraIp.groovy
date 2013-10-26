package arduito

import java.io.Serializable;

import org.codehaus.groovy.grails.validation.routines.InetAddressValidator

class CamaraIp implements Serializable {

	String ip
	Float coordenadaX
	Float coordenadaY
	
	static hasMany		= [notificables:Notificable]
	static belongsTo = [habitacion:Habitacion]

	    static mapping = {
    }
    
		
	static constraints = {
		
		ip validator: {
			true
		}
	}
}
