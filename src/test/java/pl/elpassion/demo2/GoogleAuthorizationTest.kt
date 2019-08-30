package pl.elpassion.demo2

import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.nio.charset.Charset

@DisplayName("GoogleAuthorization #authorize")
class GoogleAuthorizationTest {
    private val mockedClient = mock<RestTemplate>()
    private val incorrectToken = "incorrect-token"
    private val correctToken = "correct-token"
    private val stubbedEmail = "michal@michal.pl"
    private val googleAuthorization = GoogleAuthorization(mockedClient)
    private val badRequestError = HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "", HttpHeaders.EMPTY, ByteArray(0), Charset.defaultCharset())

    @BeforeEach
    fun stubApi() {
        whenever(
            mockedClient.getForObject<TokenResponse>(
                eq("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=$correctToken"),
                any()
            )
        ).thenReturn(TokenResponse(email = stubbedEmail))

        whenever(
            mockedClient.getForObject<TokenResponse>(
                eq("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=$incorrectToken"),
                any()
            )
        ).thenThrow(badRequestError)
    }

    @Nested
    @DisplayName("With incorrect token")
    inner class WithIncorrectToken {
        @Test
        @DisplayName("throws error")
        fun shouldThrowError() {
            assertThrows<GoogleAuthorizationError> {
                googleAuthorization.authorize(incorrectToken)
            }
        }
    }

    @Nested
    @DisplayName("With correct token")
    inner class WithCorrectToken {
        @Test
        @DisplayName("returns email")
        fun shouldReturnEmail() {
            assertEquals(googleAuthorization.authorize(correctToken), stubbedEmail)
        }
    }
}


