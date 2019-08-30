package pl.elpassion.demo2

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "midis")
@IdClass(MidiId::class)
data class Midi(
        @Id
        var id: String,
        @Id
        @Column(name = "user_id")
        var userId: String,
        @Column(name = "data", columnDefinition = "bytea")
        var data: ByteArray
) : Serializable {

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Midi

                if (id != other.id) return false
                if (userId != other.userId) return false
                if (!data.contentEquals(other.data)) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id.hashCode()
                result = 31 * result + userId.hashCode()
                result = 31 * result + data.contentHashCode()
                return result
        }
}
class MidiId : Serializable {
        private val id: String? = null
        private val userId: String? = null
}