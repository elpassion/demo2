package pl.elpassion.demo2

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MidiRepository : JpaRepository<Midi, MidiId> {
    fun findByUserId(userId: String): List<Midi>
    fun findByUserIdAndId(userId: String, id: String): Midi
}
