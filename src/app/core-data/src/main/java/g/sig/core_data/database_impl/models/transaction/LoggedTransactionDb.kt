package g.sig.core_data.database_impl.models.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import g.sig.core_data.models.transaction.Transaction
import java.time.Instant

@Entity
data class LoggedTransactionDb(
    @PrimaryKey override val transactionId: Int,
    override val amount: Double = 0.0,
    override val createdAt: Instant,
    override val description: String? = null,
    override val categoryId: Int? = null
) : Transaction
