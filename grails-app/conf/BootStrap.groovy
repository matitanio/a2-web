import arduito.Usuario

class BootStrap {


	def fixtureLoader
	def grailsApplication
	def springSecurityService
	
	def init = { servletContext ->
		if (grailsApplication.config.usarFixture) {
			fixtureLoader.load("data")
		}
	}
	def destroy = {
	}
}
