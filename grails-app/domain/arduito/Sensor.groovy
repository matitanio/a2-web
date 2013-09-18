package arduito

import java.io.Serializable;

class Sensor implements Serializable {

	
	String tipo
	String unidades
	String descripcion
	String valorMaximo
	String valorMinimo
	
	static constraints = {
		
		tipo blank:false,nullable:false
		descripcion blank:false,nullable:false
		valorMaximo blank:false,nullable:true
		valorMinimo blank:false,nullable:true
	}
	
	
	String toString(){
		
		tipo
	}
	
	
}
