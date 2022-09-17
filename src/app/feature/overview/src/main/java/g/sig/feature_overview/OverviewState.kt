package g.sig.feature_overview

import android.icu.util.Currency
import g.sig.core_data.models.transaction.MonthCategories

sealed interface OverviewState {
    data class OverviewSuccess(val monthCategories: MonthCategories, val currency: Currency) :
        OverviewState

    data class OverviewError(val errorMessage: String) : OverviewState
    object OverviewLoading : OverviewState
}