package qa.edu.cmps312.compose

sealed class NavigationItem(var route: String, var title: String, var icon: Int? = null) {
    object SurahColumn : NavigationItem("surah-column", "Surah (Display List using Column)", R.drawable.ic_quran)
    object SurahLazyColumn : NavigationItem("surah-lazy-column", "Surah (Display List using LazyColumn)", R.drawable.ic_quran)
    object Divider: NavigationItem("Divider", "Divider")
}