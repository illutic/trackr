package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.models.transaction.Month
import g.sig.core_data.models.transaction.MonthCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setMonth(month: Month)

    @Query("SELECT budget FROM Month WHERE monthId == :monthId")
    suspend fun getBudget(monthId: Int): Double?

    @Query("SELECT expenses FROM Month WHERE monthId == :monthId")
    suspend fun getExpenses(monthId: Int): Double?

    @Transaction
    @Query("SELECT * FROM Month WHERE monthId == :monthId")
    suspend fun getCategories(monthId: Int): MonthCategories?
}