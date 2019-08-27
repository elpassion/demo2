package pl.elpassion.demo2

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.io.Serializable
import javax.persistence.*


@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByFirstName(FirstName: String): List<Customer>
    override fun findAll(): List<Customer>
}

@Entity
@Table(name = "customers")
data class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,
        @Column(name = "firstname")
        var firstName: String? = null,
        @Column(name = "lastname")
        var lastName: String? = null
) : Serializable {

    companion object {
        private const val serialVersionUID = -2343243243242432341L
    }
}
