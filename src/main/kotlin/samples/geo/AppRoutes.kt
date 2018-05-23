package samples.geo

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.router
import samples.geo.web.CityHandler

class AppRoutes {
    companion object {
        fun routes(cityHandler: CityHandler): RouterFunction<*> = router {
            (accept(MediaType.APPLICATION_JSON) and "/cities").nest {
                GET("/", cityHandler::getCities)
                POST("/", cityHandler::createCity)
                GET("/{id}", cityHandler::getCity)
            }

            (accept(MediaType.APPLICATION_JSON) and "/cityids").nest {
                GET("/", cityHandler::getCityIds)
            }
        }
    }
}