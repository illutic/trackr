package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.CategoryTransactions
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCategory(category: Category): Flow<Int>

    @Query("SELECT * FROM Category")
    suspend fun getCategories(): Flow<List<Category>>

    @Delete
    suspend fun deleteCategory(category: Category): Flow<Int>

    @Transaction
    @Query("SELECT * FROM Category")
    suspend fun getCategoryTransactions(categoryId: Int): Flow<CategoryTransactions>

}