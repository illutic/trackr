package g.sig.trackr.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import g.sig.core_data.Response
import g.sig.core_data.repositories.UserRepo
import g.sig.core_data.shared_prefs.SharedKeys
import g.sig.core_data.shared_prefs.setPreferences
import g.sig.core_data.shared_prefs.sharedPrefs
import g.sig.core_data.utils.stateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppViewModel(app: Application) : AndroidViewModel(app) {
    private val userRepo = UserRepo(getApplication())
    private val userId =
        getApplication<Application>().sharedPrefs().getInt(SharedKeys.UserId, SharedKeys.NoUserId)
    private val _userState = MutableStateFlow<UserState>(UserState.UserLoading)
    val userState = _userState.stateFlow

    fun getUser() {
        viewModelScope.launch {
            userRepo.getUser(userId).collectLatest {
                _userState.value = when (it) {
                    is Response.Success -> {
                        getApplication<Application>().setPreferences(
                            SharedKeys.UserId,
                            it.data.userId
                        )
                        UserState.UserSuccess(it.data)
                    }
                    is Response.Error -> UserState.UserError(it.errorMessage ?: "")
                    is Response.Loading -> UserState.UserLoading
                }
            }
        }
    }
}