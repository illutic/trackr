package g.sig.core_data

import g.sig.core_data.database_impl.models.transaction.CategoryDb
import g.sig.core_data.database_impl.models.transaction.LoggedTransactionDb
import g.sig.core_data.database_impl.models.user.UserSettingsDb
import kotlinx.coroutines.flow.Flow

sealed interface DataInterface {
    interface UserInterface : DataInterface {
        suspend fun setUserSettings(userSettings: UserSettingsDb): Flow<DataResponse>
        suspend fun getUserSettings(): Flow<DataResponse>
    }

    interface MonthInterface : DataInterface {
        suspend fun setBudget(amount: Double): Flow<DataResponse>
        suspend fun getBudget(): Flow<DataResponse>
        suspend fun getExpenses(): Flow<DataResponse>
        suspend fun getCategories(monthId: Int): Flow<DataResponse>
    }

    interface TransactionInterface : DataInterface {
        suspend fun setTransaction(loggedTransaction: LoggedTransactionDb): Flow<DataResponse>
        suspend fun deleteTransaction(loggedTransaction: LoggedTransactionDb): Flow<DataResponse>
    }

    interface CategoryInterface : DataInterface {
        suspend fun setCategory(category: CategoryDb): Flow<DataResponse>
        suspend fun getCategories(): Flow<DataResponse>
        suspend fun getCategoryTransactions(categoryId: Int): Flow<DataResponse>
        suspend fun deleteCategory(category: CategoryDb): Flow<DataResponse>
    }
}