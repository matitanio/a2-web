package arduito

import org.codehaus.groovy.grails.validation.routines.InetAddressValidator

class Habitacion {
	
	
	static belongsTo = [edificio:Edificio]
	static hasMany = [sensores:SensorHabitacion,tarjetasConAcceso:TarjetaAcceso,camaras:CamaraIp]
	
	String piso
	String numero
	String urlPlano
	String ipHabitacion
	LectorRfeed rfeed
	
	static constraints = {
//		
//		ipHabitacion validator: {
//			InetAddressValidator.isValid(it)
//		}
		sensores nullable:true
		tarjetasConAcceso nullable:true
		camaras nullable:true
		rfeed nullable:true
		
	}
	

}
