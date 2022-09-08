package g.sig.core_data.database_impl.models.transaction

import androidx.room.*
import g.sig.core_data.models.transaction.Month

@Entity
data class MonthDb(
    @PrimaryKey override val monthId: Int,
    override val budget: Double = 0.0,
    override val expenses: Double = 0.0
) : Month

@Entity(primaryKeys = ["monthId", "categoryId"])
data class MonthCategoriesCrossRef(
    val monthId: Int,
    val categoryId: Int
)

data class MonthCategories(
    @Embedded val month: Month,
    @Relation(
        parentColumn = "monthId",
        entityColumn = "categoryId",
        associateBy = Junction(MonthCategoriesCrossRef::class)
    )
    val categories: List<CategoryDb>
)