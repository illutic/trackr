package g.sig.feature_overview

import g.sig.core_data.models.transaction.MonthCategories
import g.sig.core_data.models.user.Currency

sealed interface OverviewState {
    data class OverviewSuccess(val monthCategories: MonthCategories, val currency: Currency) :
        OverviewState

    data class OverviewError(val errorMessage: String) : OverviewState
    object OverviewLoading : OverviewState
}