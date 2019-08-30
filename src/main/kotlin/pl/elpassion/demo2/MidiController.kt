package pl.elpassion.demo2

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MidiController constructor(val googleAuthorization: GoogleAuthorization, val midiRepository: MidiRepository) {
    @GetMapping("/midis")
    fun listMidis(@RequestHeader("authorization") token: String): ResponseEntity<List<Midi>> {
        try {
            val userId = googleAuthorization.authorize(token)
            return ResponseEntity(midiRepository.findByUserId(userId), HttpStatus.OK)
        } catch (e: GoogleAuthorizationError) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    @PostMapping("/midis")
    fun addMidi(@RequestHeader("authorization") token: String, @RequestBody createMidiDto: CreateMidiDto): ResponseEntity<Midi> {
        try {
            val userId = googleAuthorization.authorize(token)
            val midi = midiRepository.save(Midi(id = createMidiDto.id, userId = userId, data = createMidiDto.data))
            return ResponseEntity(midi, HttpStatus.CREATED)
        } catch (e: GoogleAuthorizationError) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    data class CreateMidiDto(val id: String, val data: ByteArray)
}