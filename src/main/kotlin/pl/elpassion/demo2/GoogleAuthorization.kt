package pl.elpassion.demo2

import org.springframework.web.client.RestOperations
import java.lang.RuntimeException

class GoogleAuthorization(mockedClient: RestOperations) {
    fun authorize(token: String) {
        throw GoogleAuthorizationError()
    }
}

class GoogleAuthorizationError : RuntimeException()