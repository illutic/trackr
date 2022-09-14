package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.models.transaction.LoggedTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setTransaction(loggedTransaction: LoggedTransaction): Flow<Int>

    @Delete
    suspend fun deleteTransaction(loggedTransaction: LoggedTransaction): Flow<Int>

}