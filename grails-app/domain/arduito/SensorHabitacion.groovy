package arduito

class SensorHabitacion {

	
	Sensor sensor
	Integer numeroSensor
	Float valorMinimo
	Float valorMaximo
	Float valorActual
	
	Float coordenadaX
	Float coordenadaY
	
	/* Automatic timestamping of GORM */
//	Date	dateCreated
//	Date	lastUpdated
	
	static belongsTo = [habitacion:Habitacion]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
//	static hasOne		= []	// tells GORM to associate another domain object as an owner in a 1-1 mapping
//	static hasMany		= []	// tells GORM to associate other domain objects for a 1-n or n-m mapping
//	static mappedBy		= []	// specifies which property should be used in a mapping 
	
	static constraints = {
		valorActual nullable:true
		sensor lazy: false
		habitacion lazy:false
    }
	
	
}
