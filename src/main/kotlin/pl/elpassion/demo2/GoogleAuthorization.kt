package pl.elpassion.demo2

import org.springframework.web.client.RestOperations

class GoogleAuthorization(val client: RestOperations) {
    fun authorize(token: String): String {
        try {
            val url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=${token}"
            return client
                    .getForObject(url, TokenResponse::class.java)!!
                    .email
        } catch (e: Throwable) {
            throw GoogleAuthorizationError()
        }
    }
}

class GoogleAuthorizationError : RuntimeException()

data class TokenResponse(val email: String)