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
	def usuarioUser1 = new Usuario(username:'user1',password:'1234',email:'mgon.uat@gmail.com').save(flush:true)
	
	UsuarioPerfil.create(usuarioUser1, perfilUser,true)
	
	
	def usuarioUser2 = new Usuario(username:'user2',password:'1234',email:'ptamburro@gmail.com').save(flush:true)
	UsuarioPerfil.create(usuarioUser2, perfilUser,true)
	
	def usuarioUser3 = new Usuario(username:'user3',password:'1234',email:'u3@a.com').save(flush:true)
	UsuarioPerfil.create(usuarioUser3, perfilUser,true)
	
	def cuenta = new Cuenta(nombre:'cuenta1',owner:usuarioAdmin).save(flush:true)
	
	usuarioAdmin.cuenta = cuenta
	usuarioUser1.cuenta = cuenta
	usuarioUser2.cuenta = cuenta
	usuarioUser3.cuenta = cuenta
	
	usuarioUser1.save(flush:true)
	usuarioUser2.save(flush:true)
	usuarioUser3.save(flush:true)
	usuarioAdmin.save(flush:true)
	
	
	def usuarioAdmin2 = new Usuario(username:'admin2',password:'1234',email:'d@a.com').save(flush:true)
	UsuarioPerfil.create(usuarioAdmin2, perfilAdmin,true)
	
	def cuenta2 = new Cuenta(nombre:'cuenta2',owner:usuarioAdmin2).save(flush:true)
	
	usuarioAdmin2.cuenta = cuenta2
	
}

fixture{
	
	def cuenta = Cuenta.findByNombre('cuenta1')
	def usuario1 = Usuario.findByUsername('user1')
	def usuario2 = Usuario.findByUsername('user2')
	def usuario3 = Usuario.findByUsername('user3')
	edificio(Edificio,owner:cuenta,direccion:'Reconquista 464',nombre:'Infraestructura Tec.')
	dispositivoMovil1(DispositivoMovil,owner:usuario1)
	dispositivoMovil2(DispositivoMovil,owner:usuario2)
	dispositivoMovil3(DispositivoMovil,owner:usuario3)
	
	tarjetaAcceso1(TarjetaAcceso,acceso:'56CFC1B4EC')
	tarjetaAcceso2(TarjetaAcceso,acceso:'2222')
	rfeed(LectorRfeed,tarjetasConAcceso:[tarjetaAcceso1,tarjetaAcceso2],notificables:[dispositivoMovil1,dispositivoMovil2])
	
	habitacion(Habitacion,piso:'1',numero:'1',urlPlano:'aa',ipHabitacion:'192.168.1.103',edificio:edificio,
				rfeed:rfeed)
	habitacion2(Habitacion,piso:'1',numero:'2',urlPlano:'aa',ipHabitacion:'192.168.0.2',edificio:edificio)
	
	sensorTemperatura(Sensor,tipo:'temperatura',unidades:'grados',descripcion:'aa',valorMaximo:'40',valorMinimo:'10')
	sensorHumedad(Sensor,tipo:'humedad',unidades:'%',descripcion:'aa',valorMaximo:'80',valorMinimo:'40')
	sensorGas(Sensor,tipo:'gas',unidades:'ppm',descripcion:'aa',valorMaximo:'300',valorMinimo:'0')
	
	sensorHumedad1(Sensor,tipo:'humedad',unidades:'%',descripcion:'sensor que mide humedad en ctro computos',valorMaximo:'70',valorMinimo:'50')
	sensorGas1(Sensor,tipo:'gas',unidades:'ppm',descripcion:'sensor para saber si hay gas en lugar trabajo',valorMaximo:'300',valorMinimo:'0')
	
	sensorHabitacion(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',valorActual:20,valorMinimo:5,valorMaximo:38,coordenadaX:120,coordenadaY:130,habitacion:habitacion,
						warning:new Warning(valorWarning:15,comparador:ComparadoresWarning.GT),notificables:[dispositivoMovil1,dispositivoMovil2])
	
	
	//sensores habitacion1
	sensorHabitacion1(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',valorActual:60,valorMinimo:40,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion,
						notificables:[dispositivoMovil1,dispositivoMovil2,dispositivoMovil3],instalado:true)
	sensorHabitacion2(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',valorActual:150,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion,instalado:true)
	
	//sensores habitacion
	sensorHabitacion3(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',valorActual:15,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion2)
	sensorHabitacion4(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',valorActual:60,valorMinimo:10,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion2)
	sensorHabitacion5(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',valorActual:200,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion2)
	
}