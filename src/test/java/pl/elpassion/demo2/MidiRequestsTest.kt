package pl.elpassion.demo2

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(MidiController::class)
@ActiveProfiles("test")
class MidiRequestsTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var googleAuthorization: GoogleAuthorization
    @MockBean
    private lateinit var midiRepository: MidiRepository

    private val invalidToken = "invalid-token"
    private val validToken = "valid-token"
    private val userId = "mihau@gmail.com"
    private val exampleMidiFile = MockMultipartFile("data", "example.mid", "text/plain", "ABC".toByteArray())

    @BeforeEach
    fun setUp() {
        whenever(googleAuthorization.authorize(invalidToken)).doThrow(GoogleAuthorizationError())
        whenever(googleAuthorization.authorize(validToken)).thenReturn(userId)
    }

    @Test
    fun listShouldRequireAuthorization() {
        mockMvc
                .perform(get("/midis").unauthorized())
                .andExpect(status().isForbidden)
    }

    @Test
    fun listShouldReturnMidisListForUser() {
        val userMidis = listOf(Midi(id = "midi1", data = "ABC".toByteArray(), userId = userId))
        whenever(midiRepository.findByUserId(userId)).thenReturn(userMidis)
        val expectedResponse = "[{\"id\": \"midi1\", \"data\": \"QUJD\", \"userId\": \"mihau@gmail.com\"}]"
        mockMvc
                .perform(get("/midis").authorized())
                .andExpect(status().isOk)
                .andExpect(content().json(expectedResponse))
    }

    @Test
    fun createShouldRequireAuthorization() {
        mockMvc
                .perform(multipart("/midis").file("file", exampleMidiFile.bytes).param("id", "midi1").unauthorized().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden)
    }

    @Test
    fun createShouldValidateBody() {
        mockMvc
                .perform(multipart("/midis").file("file", exampleMidiFile.bytes).authorized().contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun createShouldCreateMidi() {
        val midi = Midi(userId = userId, data = "ABC".toByteArray(), id = "midi1")
        whenever(midiRepository.save(midi)).thenReturn(midi)
        mockMvc
                .perform(multipart("/midis").file("file", exampleMidiFile.bytes).param("id", "midi1").authorized())
                .andExpect(status().isCreated)
                .andExpect(content().json("{\"id\": \"midi1\", \"data\": \"QUJD\", \"userId\": \"mihau@gmail.com\"}"))
    }

    private fun MockHttpServletRequestBuilder.authorized(): MockHttpServletRequestBuilder {
        header("authorization", validToken)
        return this
    }

    private fun MockHttpServletRequestBuilder.unauthorized(): MockHttpServletRequestBuilder {
        header("authorization", invalidToken)
        return this
    }
}