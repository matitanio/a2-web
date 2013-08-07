package arduito

import grails.plugins.springsecurity.Secured

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils


class HomeController {

	def springSecurityService
	
	def index(){
		
		def config = SpringSecurityUtils.securityConfig
		String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
		render(view:"index",model: [postUrl: postUrl,
								   rememberMeParameter: config.rememberMe.parameter])
	}
	
	@Secured(["IS_AUTHENTICATED_FULLY"])
	def home(){
		
		session.user = springSecurityService.currentUser
		render(view:"home",model: [user:springSecurityService.currentUser])
	}
}
