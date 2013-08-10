package arduito

import java.io.Serializable;

class Edificio implements Serializable{
	
	transient static def springSecurityService
	
	Cuenta owner
	String direccion
	String nombre
	
	static constraints = {
		
		owner nullable:false
		direccion blank:false,nullable:false
	}
	
	static def buscarTodos(params){
		def user = Usuario.findByUsername(springSecurityService.principal.username)
		def rolAdmin = Perfil.findByAuthority('ROLE_ADMIN')
		def rolArduito = Perfil.findByAuthority('ROLE_ARDUITO')
		
		def usuarios
		if(user.getAuthorities().contains(rolArduito)){
			usuarios = list(params)
		}
		
		if(user.authorities.contains(rolAdmin)){
			usuarios = findAllByOwner(user.cuenta,params)
		}
		
		usuarios
	}
	
	static def contarTodos(){
		def user = Usuario.findByUsername(springSecurityService.principal.username)
		def rolAdmin = Perfil.findByAuthority('ROLE_ADMIN')
		def rolArduito = Perfil.findByAuthority('ROLE_ARDUITO')
		
		def todos
		if(user.getAuthorities().contains(rolArduito)){
			todos = count()
		}
		
		if(user.authorities.contains(rolAdmin)){
			todos = findAllByOwner(user.cuenta).size()
		}
		
		todos
	}
	
	String toString(){
		
		nombre + ' - ' + direccion
	}
	
}
