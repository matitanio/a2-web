import arduito.Cuenta
import arduito.DispositivoMovil
import arduito.Edificio
import arduito.Habitacion
import arduito.LectorRfeed;
import arduito.Perfil
import arduito.Sensor
import arduito.SensorHabitacion
import arduito.TarjetaAcceso
import arduito.Usuario
import arduito.UsuarioPerfil
import arduito.Warning
import arduito.arduino.ComparadoresWarning


pre{
	
	def perfilAdmin = new Perfil(authority:'ROLE_ADMIN').save(flush:true)
	def usuarioAdmin = new Usuario(username:'admin',password:'1234',email:'a@a.com').save(flush:true)
	
	UsuarioPerfil.create(usuarioAdmin, perfilAdmin,true)
	
	def perfilArduito = new Perfil(authority:'ROLE_ARDUITO').save(flush:true)
	def usuarioArduito= new Usuario(username:'arduito',password:'1234',email:'b@a.com').save(flush:true)
	
	UsuarioPerfil.create(usuarioArduito, perfilArduito,true)
	
	
	def perfilUser = new Perfil(authority:'ROLE_USER').save(flush:true)
	def usuarioUser = new Usuario(username:'user',password:'1234',email:'c@a.com').save(flush:true)
	
	UsuarioPerfil.create(usuarioUser, perfilUser,true)
	
	def cuenta = new Cuenta(nombre:'cuenta1',owner:usuarioUser).save(flush:true)
	
	usuarioAdmin.cuenta = cuenta
	usuarioUser.cuenta = cuenta
	
	usuarioUser.save(flush:true)
	usuarioAdmin.save(flush:true)
	
	
	def usuarioAdmin2 = new Usuario(username:'admin2',password:'1234',email:'d@a.com').save(flush:true)
	UsuarioPerfil.create(usuarioAdmin2, perfilAdmin,true)
	
	def cuenta2 = new Cuenta(nombre:'cuenta2',owner:usuarioUser).save(flush:true)
	
	usuarioAdmin2.cuenta = cuenta2
	
}

fixture{
	
	def cuenta = Cuenta.findByNombre('cuenta2')
	
	edificio(Edificio,owner:cuenta,direccion:'aaa',nombre:'aaaa')
	dispositivoMovil1(DispositivoMovil,numero:'5411138314736',pin:'1234')
	dispositivoMovil2(DispositivoMovil,numero:'5411138314736',pin:'2345')
	dispositivoMovil3(DispositivoMovil,numero:'5411138314736',pin:'4567')
	
	tarjetaAcceso1(TarjetaAcceso,acceso:'56CFC1B4EC')
	tarjetaAcceso2(TarjetaAcceso,acceso:'2222')
	rfeed(LectorRfeed,notificables:[dispositivoMovil1],tarjetasConAcceso:[tarjetaAcceso1,tarjetaAcceso2])
	
	habitacion(Habitacion,piso:'1',numero:'1',urlPlano:'aa',ipHabitacion:'192.168.0.1',edificio:edificio,
				rfeed:rfeed)
	habitacion2(Habitacion,piso:'1',numero:'2',urlPlano:'aa',ipHabitacion:'192.168.0.2',edificio:edificio)
	
	sensorTemperatura(Sensor,tipo:'temperatura',unidades:'grados',descripcion:'aa',valorMaximo:'40',valorMinimo:'10')
	sensorHumedad(Sensor,tipo:'humedad',unidades:'%',descripcion:'aa',valorMaximo:'40',valorMinimo:'10')
	sensorGas(Sensor,tipo:'gas',unidades:'ppm',descripcion:'aa',valorMaximo:'40',valorMinimo:'10')
	sensorHabitacion(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',valorActual:20,valorMinimo:5,valorMaximo:38,coordenadaX:120,coordenadaY:130,habitacion:habitacion,
						warning:new Warning(valorWarning:15,comparador:ComparadoresWarning.GT),notificables:[dispositivoMovil1,dispositivoMovil2])
	sensorHabitacion1(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',valorActual:60,valorMinimo:40,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion,
					notificables:[dispositivoMovil1,dispositivoMovil3])
	sensorHabitacion2(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',valorActual:150,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion)
	
	
	sensorHabitacion3(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',valorActual:15,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion2)
	sensorHabitacion4(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',valorActual:60,valorMinimo:10,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion2)
	sensorHabitacion5(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',valorActual:200,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion2)
	
	
	
}