package qa.edu.cmps312.compose

sealed class NavigationItem(var route: String, var title: String, var icon: Int? = null) {
    object SurahList : NavigationItem("surahs", "Surah List (using Column)", R.drawable.ic_quran)
    object SurahLazyList : NavigationItem("surah-list", "Surah List (using LazyColumn)", R.drawable.ic_quran)
    object Divider: NavigationItem("Divider", "Divider")
}