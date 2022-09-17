package g.sig.core_data.repositories

import android.content.Context
import android.icu.util.Currency
import g.sig.core_data.DataInterface
import g.sig.core_data.Response
import g.sig.core_data.Singletons
import g.sig.core_data.models.user.User
import g.sig.core_data.models.user.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import java.util.*

class UserRepo(context: Context) : DataInterface.UserInterface {
    private val userDao = Singletons.getDatabase(context).userDao()
    override suspend fun setUser(user: User): Flow<Response<Long>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(userDao.setUser(user)))
        }

    override suspend fun deleteUser(userId: Int): Flow<Response<Boolean>> =
        channelFlow {
            send(Response.Loading)
            getUser(userId).collectLatest {
                when (it) {
                    is Response.Loading -> send(Response.Loading)
                    is Response.Error -> send(Response.Error(it.errorMessage))
                    is Response.Success -> {
                        userDao.deleteUser(it.data)
                        send(Response.Success(true))
                    }
                }
            }
        }

    override suspend fun getUser(userId: Int): Flow<Response<User>> =
        channelFlow {
            // if user isn't setup, then create one
            if (userId == -1) {
                setUser().collectLatest {
                    when (it) {
                        is Response.Success -> {
                            val rowId = it.data
                            send(Response.Success(userDao.getUser(rowId.toInt())))
                        }
                        is Response.Loading -> send(Response.Loading)
                        is Response.Error -> send(Response.Error(it.errorMessage))
                    }
                }
            } else {
                send(Response.Loading)
                send(with(userDao.getUser(userId)) {
                    if (this == null) Response.Error(null) else Response.Success(this)
                })
            }

        }

    override suspend fun setUserSettings(userSettings: UserSettings): Flow<Response<Boolean>> =
        channelFlow {
            send(Response.Loading)
            userDao.setUserSettings(userSettings)
            send(Response.Success(true))
        }

    override suspend fun getUserSettings(userId: Int): Flow<Response<UserSettings>> =
        channelFlow {
            send(Response.Loading)
            val userSettings = userDao.getUserSettings(userId)
            if (userSettings == null) {
                setUserSettings(
                    UserSettings(
                        userId = userId,
                        currency = Currency.getInstance(Locale.getDefault())
                    )
                ).collectLatest {
                    when (it) {
                        is Response.Success -> send(Response.Success(userSettings))
                        is Response.Error -> send(Response.Error(it.errorMessage))
                        is Response.Loading -> send(it)
                    }
                }
            } else {
                Response.Success(userSettings)
            }
        }

    suspend fun getCurrencies(): Flow<Response<List<Currency>>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(Currency.getAvailableCurrencies().toList()))
        }
}