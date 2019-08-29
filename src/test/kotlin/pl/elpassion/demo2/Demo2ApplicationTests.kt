package pl.elpassion.demo2

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class Demo2ApplicationTests {

    @Autowired
    private val mockMvc: MockMvc? = null

    val expected = "[{\"id\":1,\"firstName\":\"Kewin\",\"lastName\":\"Czupryński\"}]"

    @Test
    fun contextLoads() {
        mockMvc!!.perform(get("/")).andReturn().also {
            Assert.assertEquals(it.response.contentAsString, expected)
        }
    }

    @Test
    fun contextLoads2() {
        mockMvc!!.perform(get("/")).andReturn().also {
            Assert.assertEquals(it.response.contentAsString, expected)
        }
    }
}


