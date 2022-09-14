package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.DataInterface
import g.sig.core_data.DataResponse
import g.sig.core_data.Singletons
import g.sig.core_data.models.user.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class UserRepo(context: Context) : DataInterface.UserInterface {
    private val userDao = Singletons.getDatabase(context).userDao()

    override suspend fun setUserSettings(userSettings: UserSettings): Flow<DataResponse<Boolean>> =
        flow {
            emit(DataResponse.Loading)
            userDao.setUserSettings(userSettings)
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(true))
                }
        }

    override suspend fun getUserSettings(): Flow<DataResponse<UserSettings>> =
        flow {
            emit(DataResponse.Loading)
            userDao.getUserSettings()
                .catch {
                    emit(DataResponse.Error(it.message))
                }
                .collectLatest {
                    emit(DataResponse.Success(it))
                }
        }

}