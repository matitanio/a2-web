package arduito

class Sensor {

	
	String tipo
	String descripcion
	
	static constraints = {
		
		tipo blank:false,nullable:false
		descripcion blank:false,nullable:false
	}
	
	
}
