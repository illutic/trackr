package g.sig.constants

sealed interface AppButtonType {
    object Primary : AppButtonType
    object Secondary : AppButtonType
    object Tertiary : AppButtonType
}