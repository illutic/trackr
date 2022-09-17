package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.models.transaction.LoggedTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM loggedtransaction WHERE transactionId == :transactionId")
    suspend fun getTransaction(transactionId: Int): LoggedTransaction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setTransaction(loggedTransaction: LoggedTransaction)

    @Delete
    suspend fun deleteTransaction(loggedTransaction: LoggedTransaction): Int?

}