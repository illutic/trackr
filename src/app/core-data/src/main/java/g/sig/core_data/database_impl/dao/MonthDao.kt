package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.DataResponse
import g.sig.core_data.DataInterface
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthDao : DataInterface.MonthInterface {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun setBudget(amount: Double): Flow<DataResponse>

    @Query("SELECT budget FROM MonthDb")
    override suspend fun getBudget(): Flow<DataResponse>

    @Query("SELECT expenses FROM MonthDb")
    override suspend fun getExpenses(): Flow<DataResponse>

    @Transaction
    @Query("SELECT * FROM MonthDb WHERE monthId = :monthId")
    override suspend fun getCategories(monthId: Int): Flow<DataResponse>
}