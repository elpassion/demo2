package pl.elpassion.demo2

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.anyString
import org.springframework.web.client.RestOperations

class GoogleAuthorizationTest {
    @Test
    fun shouldThrowErrorWhenTokenIsIncorrect() {
        val token = "incorrect-token"
        val mockedClient = mock<RestOperations> {
            on { getForObject<TokenResponse>(anyString(), any()) }.doReturn(TokenResponse(errorDescription = "Error"))
        }
        assertThrows<GoogleAuthorizationError> {
            GoogleAuthorization(mockedClient).authorize(token)
        }
    }
}


data class TokenResponse(val email: String? = null, val errorDescription: String?)
