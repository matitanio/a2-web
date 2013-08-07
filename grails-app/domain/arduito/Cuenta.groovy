package arduito

class Cuenta {
	
	Usuario owner
	String nombre
	
	static constraints = {
		
		owner nullable:false
		nombre blank:false,nullable:false
	}
	
	String toString(){
		nombre
	}

}
