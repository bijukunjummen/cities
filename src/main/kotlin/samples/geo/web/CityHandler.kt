package samples.geo.web

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import samples.geo.domain.City
import samples.geo.repo.CityRepo

@Service
class CityHandler(val cityRepo: CityRepo) {
    fun getCities(request: ServerRequest): Mono<ServerResponse> {
        val cities = cityRepo.findAll()
        return ServerResponse.ok().body(fromObject(cities))
    }

    fun createCity(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono<City>()
                .map { city ->
                    cityRepo.save(city)
                }.flatMap { city ->
                    ServerResponse.status(HttpStatus.CREATED).body(fromObject(city))
                }

    }

    fun getCity(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id").toLong()
        return ServerResponse.ok().body(fromObject(cityRepo.getOne(id)))
    }

    fun getCityIds(request: ServerRequest): Mono<ServerResponse> {
        return Flux.fromIterable(cityRepo.findAll())
                .map { city ->
                    city.id
                }.collectList()
                .flatMap { list ->
                    ServerResponse.ok().body(fromObject(list))
                }
    }

}