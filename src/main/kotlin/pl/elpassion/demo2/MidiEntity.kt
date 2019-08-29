package pl.elpassion.demo2

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "midis")
data class Midi(
        @Id
        var id: String,
        @Column(name = "user_id")
        var userId: String,
        @Column(name = "data", columnDefinition = "BLOB")
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
