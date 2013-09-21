class BootStrap {


	def fixtureLoader
	def grailsApplication
	
	def init = { servletContext ->
		println System.env
		println System.env.EMAIL_PASSWORD
		if (grailsApplication.config.usarFixture) {
			fixtureLoader.load("data")
		}
	}
	def destroy = {
	}
}
