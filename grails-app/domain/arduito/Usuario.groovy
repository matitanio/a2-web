package arduito

import java.io.Serializable;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

class Usuario extends Notificable implements Serializable{

	transient static springSecurityService
	String username
	String password
	String email
	boolean enabled = true
	boolean accountExpired = false
	boolean accountLocked = false
	boolean passwordExpired = false
	
	static hasMany = [dispositivosMoviles:DispositivoMovil]
	Cuenta cuenta
	static constraints = {
		username blank: false, unique: true
		password blank: false,password:true
		cuenta nullable:true
		email blank:false,email:true,unique:true
		dispositivosMoviles nullable:true
	}

	static mapping = {
		password column: '`password`'
	}
	
	Set<Perfil> getAuthorities() {
		UsuarioPerfil.findAllByUsuario(this).collect { it.perfil } as Set
	}
	
	def tienePerfil(rol){
		authorities.contains(Perfil.findByAuthority(rol))
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
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
			usuarios = findAllByCuenta(user.cuenta,params)
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
			todos = findAllByCuenta(user.cuenta).size()
		}
		
		todos
	}

	def notificar(mesaje) {
		// TODO Auto-generated method stub
		return null;
	}
	
	def getDescripcion(){
			username
	}
	
	
}
