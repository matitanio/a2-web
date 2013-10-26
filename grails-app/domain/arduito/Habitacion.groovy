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
	Boolean activa = false
	
	
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
	
	def activar(){
		
		this.activa = true
		cambiarEstadoSensores(true)
		this.save()
	}
	
	
	def desActivar(){
		
		this.activa = false
		cambiarEstadoSensores(false)
		this.save()
	}
	
	private cambiarEstadoSensores(estado){
		
		SensorHabitacion.executeUpdate("update SensorHabitacion sh set sh.instalado=:instalado where sh.habitacion=:habitacion",
										[instalado:estado,habitacion:this])
	}

}
