package pl.elpassion.demo2

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class MidiController constructor(val googleAuthorization: GoogleAuthorization, val midiRepository: MidiRepository) {
    @GetMapping("/midis")
    fun listMidis(@RequestHeader("authorization") token: String): ResponseEntity<List<Midi>> {
        try {
            val userId = googleAuthorization.authorize(token)
            return ResponseEntity(midiRepository.findByUserId(userId), HttpStatus.OK)
        } catch (e: Throwable) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    @PostMapping("/midis")
    fun addMidi(@RequestHeader("authorization") token: String): ResponseEntity<Any> {
        return ResponseEntity(HttpStatus.CREATED)
    }
}