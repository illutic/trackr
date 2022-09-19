package g.sig.feature_overview

import android.app.Application
import android.icu.util.Currency
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import g.sig.core_data.Response
import g.sig.core_data.repositories.MonthRepo
import g.sig.core_data.shared_prefs.savedCurrencyCode
import g.sig.core_data.utils.stateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime

class OverviewViewModel(app: Application) : AndroidViewModel(app) {
    private val applicationContext: Application get() = getApplication()

    private val monthRepo = MonthRepo(applicationContext)
    private val _overviewStateFlow = MutableStateFlow<OverviewState>(OverviewState.OverviewLoading)
    val overviewStateFlow: StateFlow<OverviewState> = _overviewStateFlow

    private val _savedCurrency =
        MutableStateFlow(Currency.getInstance(applicationContext.savedCurrencyCode))
    val savedCurrency = _savedCurrency.stateFlow

    init {
        changeMonth()
    }

    fun changeMonth() {
        _overviewStateFlow.value = OverviewState.OverviewLoading
        viewModelScope.launch {
            monthRepo.getMonth(LocalDateTime.now()).collectLatest {
                _overviewStateFlow.value = when (it) {
                    is Response.Error -> OverviewState.OverviewError(it.errorMessage ?: "")
                    Response.Loading -> OverviewState.OverviewLoading
                    is Response.Success -> OverviewState.OverviewSuccess(it.data)
                }
            }
        }
    }
}