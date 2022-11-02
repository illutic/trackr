package g.sig.core_data.database_impl.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.CategoryTransactions
import g.sig.core_data.models.transaction.MonthCategoriesCrossRef

@Dao
interface CategoryDao {
    @Insert(onConflict = REPLACE)
    suspend fun setCategory(category: Category)

    @Insert(onConflict = REPLACE)
    suspend fun setCategoryMonthRelation(crossRef: MonthCategoriesCrossRef)

    @Query("SELECT * FROM Category")
    suspend fun getCategories(): List<Category>?

    @Query("SELECT * FROM Category WHERE categoryId == :categoryId")
    suspend fun getCategory(categoryId: Long): Category?

    @Delete
    suspend fun deleteCategory(category: Category?): Int?

    @Transaction
    @Query("SELECT * FROM Category WHERE categoryId == :categoryId")
    suspend fun getCategoryTransactions(categoryId: Long): CategoryTransactions?

}