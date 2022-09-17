package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.CategoryTransactions

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCategory(category: Category)

    @Query("SELECT * FROM Category")
    suspend fun getCategories(): List<Category>?

    @Query("SELECT * FROM Category WHERE categoryId == :categoryId")
    suspend fun getCategory(categoryId: Int): Category?

    @Delete
    suspend fun deleteCategory(category: Category): Int?

    @Transaction
    @Query("SELECT * FROM Category WHERE categoryId == :categoryId")
    suspend fun getCategoryTransactions(categoryId: Int): CategoryTransactions?

}