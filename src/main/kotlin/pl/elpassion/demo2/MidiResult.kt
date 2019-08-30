package pl.elpassion.demo2

import org.springframework.data.annotation.CreatedDate
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "midi_results")
data class MidiResult(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @ManyToOne
        @JoinColumns(
                JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                JoinColumn(name = "midi_id", referencedColumnName = "id")
        )
        private var midi: Midi,

        @Column
        var score: Double
): Serializable {
    @CreatedDate
    @Column(name = "created_at")
    var createdAt: Date? = null
    val midiId get() = midi.id
}
