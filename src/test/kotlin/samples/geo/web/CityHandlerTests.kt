package samples.geo.web

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import org.springframework.web.reactive.function.BodyInserters.fromObject
import samples.geo.AppRoutes
import samples.geo.domain.City
import samples.geo.repo.CityRepo


class CityHandlerTests {

    private lateinit var webTestClient: WebTestClient
    private val cityRepo: CityRepo = mock()

    @Before
    fun setup() {
        whenever(cityRepo.findAll())
                .thenReturn(
                        listOf(
                                City(id = 1L, name = "test1", country = "country1", pop = 1L),
                                City(id = 2L, name = "test2", country = "country2", pop = 2L)
                        )
                )
        val cityHandler = CityHandler(cityRepo)
        this.webTestClient = WebTestClient.bindToRouterFunction(AppRoutes.routes(cityHandler)).build()
    }

    @Test
    fun getAllCities() {
        webTestClient.get()
                .uri("/cities")
                .exchange()
                .expectStatus().isOk
                .expectBodyList<City>()
                .hasSize(2)
                .contains(
                        City(id = 1L, name = "test1", country = "country1", pop = 1L),
                        City(id = 2L, name = "test2", country = "country2", pop = 2L)
                )
    }
    
    @Test
    fun getAllCityIds() {
        webTestClient.get()
                .uri("/cityids")
                .exchange()
                .expectStatus().isOk
                .expectBodyList<Long>()
                .contains(1L, 2L)
    }

    @Test
    fun saveCity() {
        whenever(cityRepo.save(any<City>())).thenAnswer { invocation ->
            invocation.arguments[0]
        }

        webTestClient.post()
                .uri("/cities")
                .body(fromObject(City(name = "test1", country = "country1", pop = 1L)))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
    }

    @Test
    fun getCity() {
        whenever(cityRepo.getOne(1L)).thenReturn(City(id = 1L, name = "test1", country = "country1", pop = 1L))
        whenever(cityRepo.getOne(2L)).thenReturn(City(id = 2L, name = "test2", country = "country2", pop = 2L))

        webTestClient.get()
                .uri("/cities/1")
                .exchange()
                .expectBody()
                .json(""" 
                    | {
                    |   "id": 1,
                    |   "name": "test1",
                    |   "country":"country1",
                    |   "pop": 1
                    | }
                """.trimMargin())
    }
}