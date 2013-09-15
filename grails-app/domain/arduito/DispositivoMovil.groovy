package arduito

class DispositivoMovil {

	
	String numero
	String pin
	Usuario owner
	
	Estado estado = Estado.SIN_VERIFICAR
	static constraints = {
		pin unique:true
		owner nullable:true
		numero blank:false,nullable:false
	}
}

enum Estado {
	SIN_VERIFICAR,VERIFICANDO,VERIFICADO
}
