package pl.elpassion.demo2

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MidiResultController(
        val googleAuthorization: GoogleAuthorization,
        val midiRepository: MidiRepository,
        val midiResultRepository: MidiResultRepository
) {
    @PostMapping("/midis/{midiId}/results")
    fun createResult(@RequestHeader("authorization") token: String, @PathVariable midiId: String, @RequestBody createResultDto: CreateResultDto): ResponseEntity<MidiResult> {
        try {
            val userId = googleAuthorization.authorize(token)
            val midi = midiRepository.findByUserIdAndId(userId, midiId)
            val midiResult = MidiResult(score = createResultDto.score, midi = midi)
            val createdMidiResult = midiResultRepository.save(midiResult)
            return ResponseEntity(createdMidiResult, HttpStatus.CREATED)
        } catch (e: GoogleAuthorizationError) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    data class CreateResultDto(val score: Double)
}
