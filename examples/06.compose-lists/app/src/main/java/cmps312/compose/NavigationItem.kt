package cmps312.compose

sealed class NavigationItem(var route: String, var title: String, var icon: Int) {
    object SurahList : NavigationItem("surahs", "Surahs", R.drawable.ic_book)
    //object Divider: NavigationItem("Divider", "Divider")
}