class BootStrap {


	def fixtureLoader

	def init = { servletContext ->

		fixtureLoader.load("data")
	}
	def destroy = {
	}
}
