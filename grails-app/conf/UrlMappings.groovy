class UrlMappings {

	static mappings = {
		
		/* 
		 * Pages without controller 
		 */
//		"/"				(view:"/index")
		"/about"		(view:"/siteinfo/about")
		"/blog"			(view:"/siteinfo/blog")
		"/systeminfo"	(view:"/siteinfo/systeminfo")
		"/contact"		(view:"/siteinfo/contact")
		"/terms"		(view:"/siteinfo/terms")
		"/imprint"		(view:"/siteinfo/imprint")
		
		/* 
		 * Pages with controller
		 * WARN: No domain/controller should be named "api" or "mobile" or "web"!
		 */
        "/"	{
			controller	= 'home'
			action		= { 'index' }
            view		= { 'index' }
        }
		"/$controller/$action?/$id?"{
			constraints {
				controller(matches:/^((?!(api|mobile|web)).*)$/)
		  	}
		}

		"/status"{
			controller = 'notificacion'
			action = 'status'
		}


		//URL notificacion de sensores

		"/notificacion/$edificio/$habitacion/$sensor/$numeroSensor/$valores"{
			controller = 'notificacion'
			action = 'index'
		}
		
		//URL de control de acceso
		"/validar/acceso/$habitacion/$tarjeta"{
			controller = 'notificacion'
			action = 'controAcceso'
		}
		
		
		//URLs consumibles desde el celular
		"/api/$pin/accesos/$action?/$id?"(controller: "accesosApi")
		"/api/$pin/edificios/$action?/$id?"(controller: "edificiosApi")
		"/api/$pin/sensores/$action?/$id?"(controller: "sensoresApi")
		
		
		//URLs proceso de registro del dispositivo movil
		"/registro/$pin/$gcmkey?"(controller: "androidDispositivo",action:'registrar')
		"/validar/$pinValidacion/$usuario"(controller: "androidDispositivo",action:'validar')
		
		/* 
		 * System Pages without controller 
		 */
		"403"	(view:'/_errors/403')
		"404"	(view:'/_errors/404')
		"500"	(view:'/_errors/error')
		"503"	(view:'/_errors/503')
	}
}
