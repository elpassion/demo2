package pl.elpassion.demo2

import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestOperations
import java.lang.RuntimeException

class GoogleAuthorization(val client: RestOperations) {
    fun authorize(token: String) {
        try {
            client.getForObject("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=${token}", TokenResponse::class.java)
        } catch (e: HttpClientErrorException.BadRequest) {

            throw GoogleAuthorizationError()
        }
    }
}

class GoogleAuthorizationError : RuntimeException()

data class TokenResponse(val email: String? = null, val errorDescription: String? = null)

class RestResponseErrorHandler : ResponseErrorHandler {

    override fun hasError(response: ClientHttpResponse): Boolean {
        return !response.statusCode.is2xxSuccessful
    }

    override fun handleError(response: ClientHttpResponse) {
        throw GoogleAuthorizationError()
    }

}