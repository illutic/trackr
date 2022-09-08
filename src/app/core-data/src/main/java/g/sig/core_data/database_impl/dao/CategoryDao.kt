package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.DataResponse
import g.sig.core_data.DataInterface
import g.sig.core_data.database_impl.models.transaction.CategoryDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : DataInterface.CategoryInterface {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun setCategory(category: CategoryDb): Flow<DataResponse>

    @Query("SELECT * FROM CategoryDb")
    override suspend fun getCategories(): Flow<DataResponse>

    @Delete
    override suspend fun deleteCategory(category: CategoryDb): Flow<DataResponse>

    @Transaction
    @Query("SELECT * FROM CategoryDb")
    override suspend fun getCategoryTransactions(categoryId: Int): Flow<DataResponse>

}