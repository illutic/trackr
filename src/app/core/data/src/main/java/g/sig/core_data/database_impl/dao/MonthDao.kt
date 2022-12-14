package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.models.transaction.Month
import g.sig.core_data.models.transaction.MonthCategories

@Dao
interface MonthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setMonth(month: Month)

    @Transaction
    @Query("SELECT * FROM Month WHERE monthId == :monthId")
    suspend fun getMonth(monthId: Long): MonthCategories?

    @Transaction
    @Query("SELECT * FROM Month WHERE month == :month AND year == :year")
    suspend fun getMonthByDate(month: Int, year: Int): MonthCategories?

    @Query("SELECT * FROM Month WHERE month == :month AND year == :year")
    suspend fun getSimpleMonthByDate(month: Int, year: Int): Month?

    @Query("SELECT budget FROM Month WHERE monthId == :monthId")
    suspend fun getBudget(monthId: Long): Double?

    @Query("SELECT expenses FROM Month WHERE monthId == :monthId")
    suspend fun getExpenses(monthId: Long): Double?
}