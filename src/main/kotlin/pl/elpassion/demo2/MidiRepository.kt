package pl.elpassion.demo2

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MidiRepository : JpaRepository<Midi, Long> {
    fun findByUserId(userId: String): List<Midi>
}
