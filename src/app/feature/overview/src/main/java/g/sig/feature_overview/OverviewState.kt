package g.sig.feature_overview

import g.sig.core_data.models.transaction.MonthCategories

sealed interface OverviewState {
    data class OverviewSuccess(val monthCategories: MonthCategories?) : OverviewState
    data class OverviewError(val errorMessage: String) : OverviewState
    object OverviewLoading : OverviewState
}