package arduito

class DispositivoMovil {

	
	Integer numero
	Usuario owner
	
	static constraints = {
		
		owner nullable:false
		numero blank:false,nullable:false
	}
}
