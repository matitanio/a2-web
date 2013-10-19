package arduito

import org.codehaus.groovy.grails.validation.routines.InetAddressValidator

class Habitacion implements Serializable {
	
	
	static belongsTo = [edificio:Edificio]
	static hasMany = [sensores:SensorHabitacion,camaras:CamaraIp]
	static hasOne = [rfeed:LectorRfeed]
	String piso
	String numero
	String urlPlano
	String ipHabitacion
	
	
	static constraints = {
//		
//		ipHabitacion validator: {
//			InetAddressValidator.isValid(it)
//		}
		urlPlano nullable:true
		sensores nullable:true
		camaras nullable:true
		rfeed nullable:true
		
	}
	

}
