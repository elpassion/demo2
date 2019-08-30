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
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@WebMvcTest(MidiResultController::class)
@ActiveProfiles("test")
class MidiResultRequestsTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var googleAuthorization: GoogleAuthorization
    @MockBean
    private lateinit var midiRepository: MidiRepository
    @MockBean
    private lateinit var midiResultRepository: MidiResultRepository

    private val invalidToken = "invalid-token"
    private val validToken = "valid-token"
    private val userId = "mihau@gmail.com"

    @BeforeEach
    fun setUp() {
        whenever(googleAuthorization.authorize(invalidToken)).doThrow(GoogleAuthorizationError())
        whenever(googleAuthorization.authorize(validToken)).thenReturn(userId)
    }

    @Test
    fun creteShouldRequireAuthorization() {
        mockMvc
                .perform(post("/midis/midi1/results").unauthorized().contentType(MediaType.APPLICATION_JSON).content("{ \"score\": 10.5 }"))
                .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun createShouldValidateBody() {
        mockMvc
                .perform(post("/midis/midi1/results").authorized())
                .andExpect(status().isBadRequest)
    }

    @Test
    fun createShouldCreateMidiResult() {
        val midi = Midi("midi1", userId, ByteArray(0))
        val midiResult = MidiResult(midi = midi, score = 10.5)
        whenever(midiRepository.findByUserIdAndId(userId, "midi1")).thenReturn(midi)
        whenever(midiResultRepository.save(midiResult)).thenReturn(midiResult)
        mockMvc
                .perform(post("/midis/midi1/results").authorized().contentType(MediaType.APPLICATION_JSON).content("{ \"score\": 10.5 }"))
                .andExpect(status().isCreated)
                .andExpect(content().json("{ \"score\": 10.5, \"midiId\": \"midi1\" }"))
    }

    private fun MockHttpServletRequestBuilder.authorized() = apply {
        header("authorization", validToken)
    }

    private fun MockHttpServletRequestBuilder.unauthorized() = apply {
        header("authorization", invalidToken)
    }
}