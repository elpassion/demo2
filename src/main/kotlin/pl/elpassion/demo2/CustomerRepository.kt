package pl.elpassion.demo2

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.io.Serializable
import javax.persistence.*

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    override fun findAll(): List<Customer>
}
