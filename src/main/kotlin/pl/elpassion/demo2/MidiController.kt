package pl.elpassion.demo2

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class MidiController constructor(val googleAuthorization: GoogleAuthorization) {
    @GetMapping("/midis")
    fun listMidis(@RequestHeader("authorization") token: String): ResponseEntity<Any> {
        try {
            googleAuthorization.authorize(token)
        } catch (e: Throwable) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
        return ResponseEntity(HttpStatus.OK)
    }
}