package g.sig.core_data.database_impl.dao

import androidx.room.*
import g.sig.core_data.DataResponse
import g.sig.core_data.DataInterface
import g.sig.core_data.database_impl.models.transaction.LoggedTransactionDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao : DataInterface.TransactionInterface {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun setTransaction(loggedTransaction: LoggedTransactionDb): Flow<DataResponse>

    @Delete
    override suspend fun deleteTransaction(loggedTransaction: LoggedTransactionDb): Flow<DataResponse>

}