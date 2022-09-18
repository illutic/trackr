package g.sig.onboarding

sealed interface OnboardingState {
    data class OnboardingSuccess(val userId: Long) : OnboardingState
    data class OnboardingError(val errorMessage: String) : OnboardingState
    object OnboardingLoading : OnboardingState
    object OnboardingIdle : OnboardingState
}