package pl.elpassion.demo2

import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Demo2ApplicationTests {

	@Autowired
	private val restTemplate: TestRestTemplate? = null

	@Test
	fun contextLoads() {
		val body = this.restTemplate?.getForObject("/", String::class.java)
		Assertions.assertThat(body).isEqualTo("Hello World")
	}
}
