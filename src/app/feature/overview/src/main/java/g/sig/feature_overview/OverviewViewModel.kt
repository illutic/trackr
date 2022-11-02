package g.sig.feature_overview

import android.app.Application
import android.icu.util.Currency
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import g.sig.core_data.Response
import g.sig.core_data.models.transaction.Month
import g.sig.core_data.models.transaction.MonthCategories
import g.sig.core_data.repositories.MonthRepo
import g.sig.core_data.shared_prefs.savedCurrencyCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class OverviewViewModel(app: Application) : AndroidViewModel(app) {
    private val applicationContext: Application get() = getApplication()

    private val monthRepo = MonthRepo(applicationContext)
    private val _overviewStateFlow = MutableStateFlow<OverviewState>(OverviewState.OverviewLoading)
    val overviewStateFlow: StateFlow<OverviewState> = _overviewStateFlow

    private val _savedCurrency =
        MutableStateFlow(Currency.getInstance(applicationContext.savedCurrencyCode))
    val savedCurrency = _savedCurrency.asStateFlow()

    fun changeMonth(date: LocalDate) {
        _overviewStateFlow.value = OverviewState.OverviewLoading
        viewModelScope.launch {
            monthRepo.getMonth(date).collectLatest { monthCategoriesResponse ->
                when (monthCategoriesResponse) {
                    is Response.Error -> _overviewStateFlow.value =
                        OverviewState.OverviewError(monthCategoriesResponse.errorMessage ?: "")
                    Response.Loading -> _overviewStateFlow.value = OverviewState.OverviewLoading
                    is Response.Success -> if (monthCategoriesResponse.data == null) {
                        val month = Month(month = date.monthValue, year = date.year)
                        _overviewStateFlow.value = OverviewState.OverviewLoading

                        // A month doesn't exist, go create a new one and send it back if it's successful
                        monthRepo.setMonth(month).collectLatest {
                            _overviewStateFlow.value = when (it) {
                                is Response.Error -> OverviewState.OverviewError(
                                    it.errorMessage ?: ""
                                )
                                Response.Loading -> OverviewState.OverviewLoading
                                is Response.Success -> OverviewState.OverviewSuccess(
                                    MonthCategories(
                                        month,
                                        categories = listOf()
                                    )
                                )
                            }
                        }
                    } else {
                        _overviewStateFlow.value =
                            OverviewState.OverviewSuccess(monthCategoriesResponse.data)
                    }
                }
            }
        }
    }
}