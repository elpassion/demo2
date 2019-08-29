package pl.elpassion.demo2

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.anyString
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.nio.charset.Charset

class GoogleAuthorizationTest {
    @Test
    fun shouldThrowErrorWhenTokenIsIncorrect() {
        val token = "incorrect-token"
        val mockedClient = mock<RestTemplate> {
            on { getForObject<TokenResponse>(anyString(), any()) }.doThrow(HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "", HttpHeaders.EMPTY, ByteArray(0), Charset.defaultCharset()))
        }
        assertThrows<GoogleAuthorizationError> {
            GoogleAuthorization(mockedClient).authorize(token)
        }
    }

    @Test
    fun shouldReturnEmailWhenTokenIsCorrect() {
        val token = "correct-token"
        val email = "michal@michal.pl"
        val mockedClient = mock<RestTemplate> {
            on { getForObject<TokenResponse>(anyString(), any()) }.doReturn(TokenResponse(email = email))
        }
        assertEquals(email, GoogleAuthorization(mockedClient).authorize(token))
    }
}


