package pl.elpassion.demo2

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(MidiController::class)
@ActiveProfiles("test")
class MidiRequestsTest {

    @Autowired private lateinit var mockMvc: MockMvc
    @MockBean private lateinit var googleAuthorization: GoogleAuthorization

    @Test
    fun shouldThrowWhenListingByUnauthorizedUsers() {
        whenever(googleAuthorization.authorize("invalid-token")).doThrow(GoogleAuthorizationError())
        mockMvc.perform(
                get("/midis").header("authorization", "invalid-token")
        ).andExpect(status().isForbidden)
    }

    @Test
    fun shouldReturnEmptyListOfMidisWhenAuthorized() {
        whenever(googleAuthorization.authorize("valid-token")).thenReturn("mihau@gmail.com")
        mockMvc.perform(
                get("/midis").header("authorization", "valid-token")
        ).andExpect(status().isOk)
    }
}