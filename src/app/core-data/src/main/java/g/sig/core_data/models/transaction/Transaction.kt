package g.sig.core_data.models.transaction

import java.time.Instant

interface Transaction {
    val transactionId: Int
    val amount: Double
    val createdAt: Instant
    val description: String?
    val categoryId: Int?
}
