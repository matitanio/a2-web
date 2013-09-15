package arduito.arduino

class ComparadoresWarning {

	
	static String EQ = "="
	static String GT = ">"
	static String GE = ">="
	static String LT = "<"
	static String LE = "<="
	
	def static comparadores =[
		(this.EQ):{valorMedido,valorWarning ->
			valorMedido == valorWarning
		},
		(this.GT):{valorMedido,valorWarning ->
			valorMedido > valorWarning
		},
		(this.GE):{valorMedido,valorWarning ->
			valorMedido >= valorWarning
		},
		(this.LT):{valorMedido,valorWarning ->
			valorMedido < valorWarning
		},
		(this.LE):{valorMedido,valorWarning ->
			valorMedido <= valorWarning
		},

	]	

}
