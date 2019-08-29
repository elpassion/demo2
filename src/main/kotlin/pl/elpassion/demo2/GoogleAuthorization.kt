package pl.elpassion.demo2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GoogleAuthorization(val client: RestTemplate) {
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

@Configuration
class GoogleConfiguration {
    @Bean
    fun googleClient() = RestTemplate()
}

class GoogleAuthorizationError : RuntimeException()

data class TokenResponse(val email: String)