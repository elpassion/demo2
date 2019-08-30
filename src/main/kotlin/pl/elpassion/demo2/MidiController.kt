package pl.elpassion.demo2

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class MidiController constructor(val googleAuthorization: GoogleAuthorization, val midiRepository: MidiRepository) {
    @CrossOrigin
    @GetMapping("/midis")
    fun listMidis(@RequestHeader("authorization") token: String): ResponseEntity<List<Midi>> {
        try {
            val userId = googleAuthorization.authorize(token)
            val midis = midiRepository.findByUserId(userId).sortedBy { it.results.lastOrNull()?.score }
            return ResponseEntity(midis, HttpStatus.OK)
        } catch (e: GoogleAuthorizationError) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    @CrossOrigin
    @PostMapping("/midis")
    fun addMidi(@RequestHeader("authorization") token: String, @RequestParam("file") file: MultipartFile, @RequestParam("id") id: String): ResponseEntity<Midi> {
        try {
            val userId = googleAuthorization.authorize(token)
            val midi = midiRepository.save(Midi(id = id, userId = userId, data = file.bytes))
            return ResponseEntity(midi, HttpStatus.CREATED)
        } catch (e: GoogleAuthorizationError) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }
}
