package samples.geo

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import samples.geo.domain.City
import samples.geo.repo.CityRepo

@SpringBootApplication
class CitiesApplication

@Component
class DataPopulator(val cityRepo: CityRepo): ApplicationRunner {
    
    override fun run(args: ApplicationArguments) {
        cityRepo.save(City(name="Portland", country = "USA", pop = 16000000L))
        cityRepo.save(City(name="Seattle", country = "USA", pop = 32000000L))
        cityRepo.save(City(name="SFO", country = "USA", pop = 64000000L))
        cityRepo.save(City(name="LA", country = "USA", pop = 128000000L))
        cityRepo.save(City(name="Denver", country = "USA", pop = 30000000L))
        cityRepo.save(City(name="Chicago", country = "USA", pop = 256000000L))
        cityRepo.save(City(name="NY", country = "USA", pop = 256000000L))
    }

}

fun main(args: Array<String>) {
    runApplication<CitiesApplication>(*args)
}


