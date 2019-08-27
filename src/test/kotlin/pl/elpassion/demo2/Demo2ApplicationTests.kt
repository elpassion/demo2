package pl.elpassion.demo2

import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class Demo2ApplicationTests {

    @Autowired
    private val restTemplate: TestRestTemplate? = null

    @Test
    fun contextLoads() {
        val body = this.restTemplate?.getForObject("/", Array<Customer>::class.java)
        body as Array<Customer>
        Assertions
                .assertThat(body)
                .isEqualTo(arrayOf(Customer().apply {
                    id = 1
                    firstName = "Kewin"
                    lastName = "Czupryński"
                }))
    }

    @Test
    fun contextLoads2() {
        val body = this.restTemplate?.getForObject("/", Array<Customer>::class.java)
        body as Array<Customer>
        Assertions
                .assertThat(body)
                .isEqualTo(arrayOf(Customer().apply {
                    id = 1
                    firstName = "Kewin"
                    lastName = "Czupryński"
                }))
    }
}
