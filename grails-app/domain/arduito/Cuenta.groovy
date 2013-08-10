package arduito

class Cuenta implements Serializable {
	
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
