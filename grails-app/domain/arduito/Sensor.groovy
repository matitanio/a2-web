package arduito

import java.io.Serializable;

class Sensor implements Serializable {

	
	String tipo
	String descripcion
	
	static constraints = {
		
		tipo blank:false,nullable:false
		descripcion blank:false,nullable:false
	}
	
	
}
