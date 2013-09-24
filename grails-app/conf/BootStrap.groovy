class BootStrap {


	def fixtureLoader
	def grailsApplication
	
	def init = { servletContext ->
		if (grailsApplication.config.usarFixture) {
			fixtureLoader.load("data")
		}
	}
	def destroy = {
	}
}
