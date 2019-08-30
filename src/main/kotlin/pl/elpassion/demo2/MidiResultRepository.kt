package pl.elpassion.demo2

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MidiResultRepository : JpaRepository<MidiResult, Long> {
}
