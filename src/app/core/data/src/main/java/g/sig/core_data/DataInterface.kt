package g.sig.core_data

import g.sig.core_data.models.transaction.*
import g.sig.core_data.models.user.User
import g.sig.core_data.models.user.UserSettings
import kotlinx.coroutines.flow.Flow

sealed interface DataInterface {
    interface UserInterface : DataInterface {
        suspend fun setUser(user: User = User()): Flow<Response<Long>>
        suspend fun deleteUser(userId: Int): Flow<Response<Boolean>>
        suspend fun getUser(userId: Int): Flow<Response<User>>
        suspend fun setUserSettings(userSettings: UserSettings): Flow<Response<Boolean>>
        suspend fun getUserSettings(userId: Int): Flow<Response<UserSettings>>
    }

    interface MonthInterface : DataInterface {
        suspend fun setMonth(month: Month): Flow<Response<Boolean>>
        suspend fun getBudget(monthId: Int): Flow<Response<Double>>
        suspend fun getExpenses(monthId: Int): Flow<Response<Double>>
        suspend fun getCategories(monthId: Int): Flow<Response<MonthCategories>>
    }

    interface TransactionInterface : DataInterface {
        suspend fun setTransaction(loggedTransaction: LoggedTransaction): Flow<Response<Boolean>>
        suspend fun getTransaction(loggedTransactionId: Int): Flow<Response<LoggedTransaction>>
        suspend fun deleteTransaction(loggedTransactionId: Int): Flow<Response<Boolean>>
    }

    interface CategoryInterface : DataInterface {
        suspend fun setCategory(category: Category): Flow<Response<Boolean>>
        suspend fun getCategories(): Flow<Response<List<Category>>>
        suspend fun getCategoryTransactions(categoryId: Int): Flow<Response<CategoryTransactions>>
        suspend fun deleteCategory(categoryId: Int): Flow<Response<Boolean>>
        suspend fun getCategory(categoryId: Int): Flow<Response<Category>>
    }
}