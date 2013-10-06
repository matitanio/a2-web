package arduito

class Perfil {

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
	
	static obtenerPerfilesVisibles(String user){
		
		def role = Usuario.findByUsername(user).perfil
		
		def perfiles = Perfil.list()
		if(role.authority == 'ROLE_ADMIN'){
			perfiles -= Perfil.findByAuthority('ROLE_ARDUITO')
		}
		
		perfiles
	}
	
	String toString(){
		
		authority.replaceAll('_',' ')
	}
}
