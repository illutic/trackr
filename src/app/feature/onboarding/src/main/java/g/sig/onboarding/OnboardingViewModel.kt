package g.sig.onboarding

import android.app.Application
import android.icu.util.Currency
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import g.sig.core_data.Response
import g.sig.core_data.models.user.User
import g.sig.core_data.models.user.UserSettings
import g.sig.core_data.repositories.UserRepo
import g.sig.core_data.shared_prefs.SharedKeys
import g.sig.core_data.shared_prefs.setPreferences
import g.sig.core_data.utils.asStateFlow

()
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class OnboardingViewModel(app: Application) : AndroidViewModel(app) {
    private val userRepo = UserRepo(getApplication())
    private val _onboardingStateFlow =
        MutableStateFlow<OnboardingState>(OnboardingState.OnboardingIdle)
    val onboardingStateFlow = _onboardingStateFlow.asStateFlow()

    fun registerUser() = viewModelScope.launch {
        userRepo.setUser(User()).collectLatest {
            when (it) {
                is Response.Error -> _onboardingStateFlow.value =
                    OnboardingState.OnboardingError(it.errorMessage ?: "")
                Response.Loading -> _onboardingStateFlow.value = OnboardingState.OnboardingLoading
                is Response.Success -> {
                    userRepo.setUserSettings(
                        UserSettings(
                            userId = it.data, currency = Currency.getInstance(Locale.getDefault())
                        )
                    ).collectLatest { settingsResponse ->
                        _onboardingStateFlow.value = when (settingsResponse) {
                            is Response.Error ->
                                OnboardingState.OnboardingError(
                                    settingsResponse.errorMessage ?: ""
                                )
                            Response.Loading ->
                                OnboardingState.OnboardingLoading
                            is Response.Success -> {
                                getApplication<Application>().setPreferences(
                                    SharedKeys.UserId,
                                    it.data
                                )
                                OnboardingState.OnboardingSuccess(userId = it.data)
                            }
                        }
                    }
                }
            }
        }
    }
}