package g.sig.core_data.models.transaction

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Long,
    val name: String
)

data class CategoryTransactions(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId",
    )
    val loggedTransactions: List<LoggedTransaction>
)