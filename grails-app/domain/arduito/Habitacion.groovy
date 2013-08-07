package arduito

class Habitacion {
	
	
	static belongsTo = [edificio:Edificio]
	static hasMany = [sensores:Sensor]
	String piso
	String numero
	
	

}
