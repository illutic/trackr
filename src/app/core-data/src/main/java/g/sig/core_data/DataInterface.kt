package g.sig.core_data

import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.LoggedTransaction
import g.sig.core_data.models.user.UserSettings
import kotlinx.coroutines.flow.Flow

sealed interface DataInterface {
    interface UserInterface : DataInterface {
        suspend fun setUserSettings(userSettings: UserSettings): Flow<DataResponse>
        suspend fun getUserSettings(): Flow<DataResponse>
    }

    interface MonthInterface : DataInterface {
        suspend fun setBudget(amount: Double): Flow<DataResponse>
        suspend fun getBudget(): Flow<DataResponse>
        suspend fun getExpenses(): Flow<DataResponse>
        suspend fun getCategories(monthId: Int): Flow<DataResponse>
    }

    interface TransactionInterface : DataInterface {
        suspend fun setTransaction(loggedTransaction: LoggedTransaction): Flow<DataResponse>
        suspend fun deleteTransaction(loggedTransaction: LoggedTransaction): Flow<DataResponse>
    }

    interface CategoryInterface : DataInterface {
        suspend fun setCategory(category: Category): Flow<DataResponse>
        suspend fun getCategories(): Flow<DataResponse>
        suspend fun getCategoryTransactions(categoryId: Int): Flow<DataResponse>
        suspend fun deleteCategory(category: Category): Flow<DataResponse>
    }
}