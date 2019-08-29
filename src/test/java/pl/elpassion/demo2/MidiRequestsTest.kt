package pl.elpassion.demo2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class MidiRequestsTest(@Autowired
                       private val restTemplate: TestRestTemplate) {

    @Test
    fun shouldThrowWhenListingByUnauthorizedUsers() {
        val entity = restTemplate.getForEntity<Any>("/midis")
        assertEquals(HttpStatus.FORBIDDEN, entity.statusCode)
    }
}