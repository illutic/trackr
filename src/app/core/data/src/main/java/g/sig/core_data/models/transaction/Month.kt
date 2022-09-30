package g.sig.core_data.models.transaction

import androidx.room.*

@Entity
data class Month(
    @PrimaryKey(autoGenerate = true) val monthId: Long = 0,
    val budget: Double = 0.0,
    val expenses: Double = 0.0,
    val month: Int,
    val year: Int
)

@Entity(
    primaryKeys = ["monthId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = Month::class,
            parentColumns = ["monthId"],
            childColumns = ["monthId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class MonthCategoriesCrossRef(
    @ColumnInfo(index = true)
    val monthId: Long,
    @ColumnInfo(index = true)
    val categoryId: Long
)

data class MonthCategories(
    @Embedded val month: Month,
    @Relation(
        entity = Category::class,
        parentColumn = "monthId",
        entityColumn = "categoryId",
        associateBy = Junction(MonthCategoriesCrossRef::class)
    )
    val categories: List<CategoryTransactions>
)