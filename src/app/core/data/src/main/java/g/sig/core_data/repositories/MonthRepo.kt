package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.DataInterface
import g.sig.core_data.DataResponse
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.MonthCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class MonthRepo(context: Context) : DataInterface.MonthInterface {
    private val monthDao = Singletons.getDatabase(context).monthDao()

    override suspend fun setBudget(amount: Double): Flow<DataResponse<Boolean>> =
        flow {
            emit(DataResponse.Loading)
            monthDao.setBudget(amount)
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(true))
                }
        }

    override suspend fun getBudget(): Flow<DataResponse<Double>> =
        flow {
            emit(DataResponse.Loading)
            monthDao.getBudget()
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(it))
                }
        }

    override suspend fun getExpenses(): Flow<DataResponse<Double>> =
        flow {
            emit(DataResponse.Loading)
            monthDao.getExpenses()
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(it))
                }
        }

    override suspend fun getCategories(monthId: Int): Flow<DataResponse<MonthCategories>> =
        flow {
            emit(DataResponse.Loading)
            monthDao.getCategories(monthId)
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(it))
                }
        }

}