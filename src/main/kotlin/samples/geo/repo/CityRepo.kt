package samples.geo.repo

import org.springframework.data.jpa.repository.JpaRepository
import samples.geo.domain.City

interface CityRepo: JpaRepository<City, Long>
