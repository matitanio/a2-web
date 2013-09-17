package arduito

class EdificioService {

	def springSecurityService
	
     def buscarTodos(params){
		def user = Usuario.findByUsername(springSecurityService.principal.username)
		def rolAdmin = Perfil.findByAuthority('ROLE_ADMIN')
		def rolArduito = Perfil.findByAuthority('ROLE_ARDUITO')
		
		def usuarios
		if(user.getAuthorities().contains(rolArduito)){
			usuarios = Edificio.list(params)
		}
		
		if(user.authorities.contains(rolAdmin)){
			usuarios = Edificio.findAllByOwner(user.cuenta,params)
		}
		
		usuarios
	}
	
	 def contarTodos(){
		def user = Usuario.findByUsername(springSecurityService.principal.username)
		def rolAdmin = Perfil.findByAuthority('ROLE_ADMIN')
		def rolArduito = Perfil.findByAuthority('ROLE_ARDUITO')
		
		def todos
		if(user.getAuthorities().contains(rolArduito)){
			todos = Edificio.count()
		}
		
		if(user.authorities.contains(rolAdmin)){
			todos = Edificio.findAllByOwner(user.cuenta).size()
		}
		
		todos
	}
}
