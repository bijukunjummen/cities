package samples.geo.repo

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import samples.geo.domain.City
import samples.geo.repo.CityRepo

@RunWith(SpringRunner::class)
@DataJpaTest
class CityRepoTest {
    
    @Autowired
    lateinit var cityRepo: CityRepo
    
    @Test
    fun createACity() {
        val city = City(name = "Test1", country = "USA", pop = 3000000000)
        cityRepo.save(city)
        assertThat(cityRepo.findById(city.id!!)).hasValue(city)
        
        val cityToBeUpdated = city.copy(name = "Test 2")
        val updated = cityRepo.save(cityToBeUpdated)
        assertThat(cityRepo.findById(city.id!!)).hasValue(updated)
        
        assertThat(cityRepo.findAll()).containsExactly(updated)
        
        cityRepo.delete(updated)

        assertThat(cityRepo.findAll()).hasSize(0)
    }
}