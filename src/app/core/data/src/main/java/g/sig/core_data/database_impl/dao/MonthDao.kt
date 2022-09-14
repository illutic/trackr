package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.models.transaction.MonthCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthDao {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setBudget(amount: Double): Flow<Int>

    @Query("SELECT budget FROM Month")
    suspend fun getBudget(): Flow<Double>

    @Query("SELECT expenses FROM Month")
    suspend fun getExpenses(): Flow<Double>

    @Transaction
    @Query("SELECT * FROM Month WHERE monthId = :monthId")
    suspend fun getCategories(monthId: Int): Flow<MonthCategories>
}