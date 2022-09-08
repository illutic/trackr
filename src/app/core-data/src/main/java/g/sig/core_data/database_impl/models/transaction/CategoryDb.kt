package g.sig.core_data.database_impl.models.transaction

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import g.sig.core_data.models.transaction.Category

@Entity
data class CategoryDb(
    @PrimaryKey override val categoryId: Int,
    override val name: String
) : Category

data class CategoryTransactions(
    @Embedded val category: CategoryDb,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId",
    )
    val loggedTransactions: List<LoggedTransactionDb>
)