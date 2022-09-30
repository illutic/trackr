package g.sig.core_data.models.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class LoggedTransaction(
    @PrimaryKey(autoGenerate = true) val transactionId: Long,
    val amount: Double = 0.0,
    val createdAt: Instant,
    val description: String? = null
)