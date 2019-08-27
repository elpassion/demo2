package pl.elpassion.demo2

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(@Autowired private val repository: CustomerRepository) {

    @GetMapping("/")
    fun index(): List<Customer> {
        repository.save(Customer().apply {
            firstName = "Kewin"
            lastName = "Czupryński"
        })
        return repository.findAll()
    }
}
