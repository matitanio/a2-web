package arduito

class DispositivoMovil {

	
	Integer numero
	Usuario owner
	
	Estado estado = Estado.SIN_VERIFICAR
	static constraints = {
		
		owner nullable:false
		numero blank:false,nullable:false
	}
}

enum Estado {
	SIN_VERIFICAR,VERIFICANDO,VERIFICADO
}
