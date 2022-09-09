package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.DataInterface
import g.sig.core_data.DataResponse
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class CategoryRepo(context: Context) : DataInterface.CategoryInterface {
    private val categoryDao = Singletons.getDatabase(context).categoryDao()

    override suspend fun setCategory(category: Category): Flow<DataResponse> =
        flow {
            emit(DataResponse.Loading)
            categoryDao.setCategory(category)
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(true))
                }
        }

    override suspend fun getCategories(): Flow<DataResponse> =
        flow {
            emit(DataResponse.Loading)
            categoryDao.getCategories()
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(it))
                }
        }

    override suspend fun getCategoryTransactions(categoryId: Int): Flow<DataResponse> =
        flow {
            emit(DataResponse.Loading)
            categoryDao.getCategoryTransactions(categoryId)
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(it))
                }
        }

    override suspend fun deleteCategory(category: Category): Flow<DataResponse> =
        flow {
            emit(DataResponse.Loading)
            categoryDao.deleteCategory(category)
            emit(DataResponse.Success(true))
        }
}