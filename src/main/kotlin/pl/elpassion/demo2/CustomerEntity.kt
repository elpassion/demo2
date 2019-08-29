package pl.elpassion.demo2

import java.io.Serializable
import javax.persistence.*

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
) : Serializable {}
