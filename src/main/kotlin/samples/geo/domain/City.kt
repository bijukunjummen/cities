package samples.geo.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class City(
        @Id @GeneratedValue var id: Long? = null,
        val name: String,
        val country: String,
        val pop: Long
) {
    constructor() : this(id = null, name = "", country = "", pop = 0L)
}