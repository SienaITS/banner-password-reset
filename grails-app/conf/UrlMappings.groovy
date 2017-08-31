class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        //"/"(view:"/index") //old default index
        "/"(controller:"password", action:"index")
        "500"(view:'/error')
	}
}
