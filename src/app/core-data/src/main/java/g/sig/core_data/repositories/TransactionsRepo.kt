package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.DataInterface
import g.sig.core_data.DataResponse
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.LoggedTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class TransactionsRepo(context: Context) : DataInterface.TransactionInterface {
    private val transactionsDao = Singletons.getDatabase(context).transactionsDao()

    override suspend fun setTransaction(loggedTransaction: LoggedTransaction): Flow<DataResponse<Boolean>> =
        flow {
            emit(DataResponse.Loading)
            transactionsDao.setTransaction(loggedTransaction)
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(true))
                }
        }

    override suspend fun deleteTransaction(loggedTransaction: LoggedTransaction): Flow<DataResponse<Boolean>> =
        flow {
            emit(DataResponse.Loading)
            transactionsDao.deleteTransaction(loggedTransaction)
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(true))
                }
        }

}