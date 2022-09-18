package g.sig.core_navigation


const val DeepLinkUri = "https://g.sig.trackr"

sealed interface DeepLink {
    val deepLink: String
}