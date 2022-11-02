package g.sig.core_data.models.transaction

import androidx.room.*

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    val name: String
)

@Entity(
    primaryKeys = ["categoryId", "transactionId"],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LoggedTransaction::class,
            parentColumns = ["transactionId"],
            childColumns = ["transactionId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class CategoryTransactionsCrossRef(
    @ColumnInfo(index = true)
    val categoryId: Long,
    @ColumnInfo(index = true)
    val transactionId: Long
)

data class CategoryTransactions(
    @Embedded val category: Category,
    @Relation(
        entity = LoggedTransaction::class,
        parentColumn = "categoryId",
        entityColumn = "transactionId",
        associateBy = Junction(CategoryTransactionsCrossRef::class)
    )
    val loggedTransactions: List<LoggedTransaction>
)