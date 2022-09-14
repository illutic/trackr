package g.sig.core_data.models.transaction

import androidx.room.*
import java.time.Instant

@Entity
data class Month(
    @PrimaryKey(autoGenerate = true) val monthId: Int,
    val budget: Double = 0.0,
    val expenses: Double = 0.0,
    val date: Instant
)

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
    val categories: List<Category>
)