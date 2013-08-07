import arduito.Cuenta
import arduito.Perfil
import arduito.Usuario
import arduito.UsuarioPerfil


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
	
}