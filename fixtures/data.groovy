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
	
	def usuarioUser1Cuenta2 = new Usuario(username:'user1Cuenta1',password:'1234',email:'u1c2@gmail.com').save(flush:true)
	UsuarioPerfil.create(usuarioUser1Cuenta2, perfilUser,true)
	
	def usuarioUser2Cuenta2 = new Usuario(username:'user2Cuenta2',password:'1234',email:'u2c2@a.com').save(flush:true)
	UsuarioPerfil.create(usuarioUser2Cuenta2, perfilUser,true)
	
	def cuenta2 = new Cuenta(nombre:'cuenta2',owner:usuarioAdmin2).save(flush:true)
	
	usuarioAdmin2.cuenta = cuenta2
	usuarioUser1Cuenta2.cuenta = cuenta2
	usuarioUser2Cuenta2.cuenta = cuenta2
}

fixture{
	
	def cuenta = Cuenta.findByNombre('cuenta1')
	def usuario1 = Usuario.findByUsername('user1')
	def usuario2 = Usuario.findByUsername('user2')
	def usuario3 = Usuario.findByUsername('user3')
	def cuenta2 = Cuenta.findByNombre('cuenta2')
	edificio(Edificio,owner:cuenta,direccion:'Reconquista 464',nombre:'Infraestructura Tec.')
	edificio2(Edificio,owner:cuenta2,direccion:'Corrientes 450',nombre:'Cubika')
	
	dispositivoMovil1(DispositivoMovil,owner:usuario1)
	dispositivoMovil2(DispositivoMovil,owner:usuario2)
	dispositivoMovil3(DispositivoMovil,owner:usuario3)
	
	tarjetaAcceso1(TarjetaAcceso,acceso:'56CFC1B4EC')
	tarjetaAcceso2(TarjetaAcceso,acceso:'2222')
	rfeed(LectorRfeed,tarjetasConAcceso:[tarjetaAcceso1,tarjetaAcceso2],notificables:[dispositivoMovil1,dispositivoMovil2])
	
	habitacion(Habitacion,piso:'1',numero:'1',urlPlano:'aa',ipHabitacion:'192.168.1.103',edificio:edificio,
				rfeed:rfeed)
	
	habitacion2(Habitacion,piso:'1',numero:'2',urlPlano:'aa',ipHabitacion:'192.168.0.2',edificio:edificio)
	habitacion3(Habitacion,piso:'1',numero:'3',urlPlano:'aa',ipHabitacion:'192.168.0.3',edificio:edificio)
	habitacion4(Habitacion,piso:'1',numero:'3',urlPlano:'aa',ipHabitacion:'192.168.0.3',edificio:edificio)
	habitacion5(Habitacion,piso:'1',numero:'4',urlPlano:'aa',ipHabitacion:'192.168.0.4',edificio:edificio)
	
	habitacion6(Habitacion,piso:'1',numero:'5',urlPlano:'aa',ipHabitacion:'192.168.0.5',edificio:edificio2)
	habitacion7(Habitacion,piso:'1',numero:'6',urlPlano:'aa',ipHabitacion:'192.168.0.6',edificio:edificio2)
	habitacion8(Habitacion,piso:'1',numero:'7',urlPlano:'aa',ipHabitacion:'192.168.0.7',edificio:edificio2)
	
	sensorTemperatura(Sensor,tipo:'temperatura',unidades:'grados',descripcion:'sensor que mide temperatura',valorMaximo:'40',valorMinimo:'10')
	sensorHumedad(Sensor,tipo:'humedad',unidades:'%',descripcion:'sensor que mide humedad en ctro computos',valorMaximo:'70',valorMinimo:'50')
	sensorGas(Sensor,tipo:'gas',unidades:'ppm',descripcion:'sensor para saber si hay gas en lugar trabajo',valorMaximo:'100',valorMinimo:'0')
	

	
	
	//sensores habitacion1
	sensorHabitacion1(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',
						valorActual:60,valorMinimo:40,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion,
						notificables:[dispositivoMovil1,dispositivoMovil2,dispositivoMovil3],instalado:true)
	sensorHabitacion2(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',
							valorActual:150,valorMinimo:0,valorMaximo:100,coordenadaX:120,coordenadaY:130,habitacion:habitacion,instalado:true)
	
	sensorHabitacion(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',
		valorActual:20,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion,
		warning:new Warning(valorWarning:15,comparador:ComparadoresWarning.GT),notificables:[dispositivoMovil1,dispositivoMovil2],instalado:true)
	
	//sensores habitacion2
	sensorHabitacion3(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',
						valorActual:15,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion2,instalado:true)
	sensorHabitacion4(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',
						valorActual:60,valorMinimo:10,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion2,instalado:true)
	sensorHabitacion5(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',
						valorActual:200,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion2,instalado:true)
	

	//sensores habitacion3
	sensorTemperaturaHabitacion3(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',
						valorActual:15,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion3,instalado:true)
	sensorHumedadHabitacion3(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',
						valorActual:60,valorMinimo:10,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion3,instalado:true)
	sensorGasHabitacion3(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',
						valorActual:200,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion3,instalado:true)

	//sensores habitacion4
	sensorTemperaturaHabitacion4(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',
						valorActual:15,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion4,instalado:true)
	sensorHumedadHabitacion4(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',
						valorActual:60,valorMinimo:10,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion4,instalado:true)
	sensorGasHabitacion4(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',
						valorActual:200,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion4,instalado:true)

	
	//sensores habitacion5
	sensorTemperaturaHabitacion5(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',
						valorActual:15,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion5,instalado:true)
	sensorHumedadHabitacion5(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',
						valorActual:60,valorMinimo:10,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion5,instalado:true)
	sensorGasHabitacion5(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',
						valorActual:200,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion5,instalado:true)

	
	//sensores habitacion6
	sensorTemperaturaHabitacion6(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',
						valorActual:15,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion6,instalado:true)
	sensorHumedadHabitacion6(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',
						valorActual:60,valorMinimo:10,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion6,instalado:true)
	sensorGasHabitacion6(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',
						valorActual:200,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion6,instalado:true)
	
	//sensores habitacion7
	sensorTemperaturaHabitacion7(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',
						valorActual:15,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion7,instalado:true)
	sensorHumedadHabitacion7(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',
						valorActual:60,valorMinimo:10,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion7,instalado:true)
	sensorGasHabitacion7(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',
						valorActual:200,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion7,instalado:true)

	
	//sensores habitacion8
	sensorTemperaturaHabitacion8(SensorHabitacion,sensor:sensorTemperatura,numeroSensor:'1',
						valorActual:15,valorMinimo:10,valorMaximo:20,coordenadaX:120,coordenadaY:130,habitacion:habitacion8,instalado:true)
	sensorHumedadHabitacion8(SensorHabitacion,sensor:sensorHumedad,numeroSensor:'1',
						valorActual:60,valorMinimo:10,valorMaximo:80,coordenadaX:120,coordenadaY:130,habitacion:habitacion8,instalado:true)
	sensorGasHabitacion8(SensorHabitacion,sensor:sensorGas,numeroSensor:'1',
						valorActual:200,valorMinimo:0,valorMaximo:300,coordenadaX:120,coordenadaY:130,habitacion:habitacion8,instalado:true)


}